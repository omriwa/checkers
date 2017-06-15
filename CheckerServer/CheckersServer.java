package CheckerServer;

import Database.DatabaseManager;
import java.rmi.RemoteException;
import java.util.HashMap;
import Model.GameState;
import Model.User;
import Client.IRemoteClient;
import Model.GameInvitation;
import Model.PlayerWonEvent;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CheckersServer {

    private static CheckersServer server = null;
    private final DatabaseManager databaseManager;
    private HashMap<String, IRemoteClient> onlineClients;
    private HashMap<String, GameState> games;
    private RemoteServer remoteServer;
    private IRemoteServer remoteServerStub;
    private String looseMsg = "You loose!", winMsg = "You won!";

    private CheckersServer() {
        databaseManager = DatabaseManager.getDatabaseManager();
        this.initialize();
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            remoteServerStub = (IRemoteServer) UnicastRemoteObject.exportObject(this.remoteServer, 1099);
            registry.rebind("gameManager", remoteServer);

            System.out.println("server is up");
        } catch (Exception e) {
            System.out.println("cannot set up the server");
        }
    }

    public static CheckersServer getServer() {
        if (server == null) {
            server = new CheckersServer();
        }
        return server;
    }

    public synchronized User connect(String username, String password, IRemoteClient b) {
        User user;
        if ((user = databaseManager.getUserFromDB(username, password)) != null) {
            if (!onlineClients.containsKey(user.getUsername())) {//user isnt exists
                onlineClients.put(user.getUsername(), b);
            }
        }
        return user;
    }

    private void createDataBase() {
        databaseManager.createTables();
    }

    private synchronized void clientDisconnected(String user) {
        try {
            onlineClients.get(user).disconnect();
            onlineClients.remove(user);
            System.out.println(user + " get disconnected successfully");
        } catch (RemoteException ex) {
            Logger.getLogger(CheckersServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (games.containsKey(user)) {
            processGameStoped(games.get(user).getOtherUser(user));
            games.remove(user);//remove the game from hash of the disconnected user
        }
    }

    /*writing statistic of game to the db and announce the other player*/
    public synchronized void processGameStoped(String user) {
        GameState game = games.get(user);
        game.setWinner(user);
        game.setEndTime();//set finish time of the game
        databaseManager.writeGameStatistic(game);//writing the game statistic
        IRemoteClient client = onlineClients.get(user);
        String msg = "The other user disconnected";
        try {
            if (client != null) {
                client.diconnect(msg);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(CheckersServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        games.remove(user);
    }

    public synchronized User register(User userInfo, String pass, IRemoteClient b) {
        if (databaseManager.registerUser(userInfo, pass)) {
            return userInfo;
        }
        return null;
    }

    private void initialize() {
        remoteServer = new RemoteServer();
        onlineClients = new HashMap<String, IRemoteClient>();
        games = new HashMap<>();
        createDataBase();
        new ClientManager().start();
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    /*return the the user details from database and creating a user*/
    public User getUser(String username, String password) {
        return databaseManager.getUserFromDB(username, password);
    }

    /*get online users*/
    public HashMap<String, IRemoteClient> getOnlineClients() {
        return onlineClients;
    }

    /*update online users lists in users panel*/
    public synchronized void updateUsersListInGui() {
        for (String client : onlineClients.keySet()) {
            try {
                onlineClients.get(client).updateOnlineUsersList(getUsersName(client));
            } catch (RemoteException ex) {
                Logger.getLogger(CheckersServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void writeStatistics(GameState g) {
        databaseManager.writeGameStatistic(g);
    }

    public void changeGameTurn(GameState gameState) {
        sendGameState(gameState, true, true);
    }

    public void StartGame(GameState gameState) {
        games.put(gameState.getUserId1(), gameState);
        games.put(gameState.getUserId2(), gameState);
        sendGameState(gameState, true, false);
    }

    /*send the game state to the users that are playing together when the are begining game
    or for during the game, if enableGame is true and changeTurn is false, its the start of the game
    if enable  is during the game*/
    private void sendGameState(GameState gameState, boolean enableGame, boolean changeTurn) {
        /*update the users*/
        IRemoteClient client1 = (IRemoteClient) onlineClients.get(gameState.getUserId1());
        IRemoteClient client2 = (IRemoteClient) onlineClients.get(gameState.getUserId2());
        try {//upadate the users gamestate
            if (enableGame) {
                gameState.enablePlaying();
            }
            if (changeTurn) {
                gameState.changeTurn();
            }
            client1.sendGameState(gameState);
            if (!changeTurn)//if its the start of the game
            {
                gameState.changeTurn();
            }
            client2.sendGameState(gameState);
        } catch (RemoteException e) {
        }
    }

    /*return array list of string ,of the usersname*/
    private ArrayList<String> getUsersName(String curUser) {
        ArrayList<String> uName = new ArrayList<>();
        for (String name : onlineClients.keySet()) {
            uName.add(name);
        }
        uName.remove(curUser);
        return uName;
    }

    public void sendInvitation(GameInvitation invitation) {
        IRemoteClient client = onlineClients.get(invitation.getUserId2());
        try {
            if (client != null && client.isAlive())//the opponent exists and online
            {
                invitation = client.receiveGameInvitation(invitation);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(CheckersServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (invitation.isAccept()) {
            GameState g = new GameState(invitation.getUserId1(), invitation.getUserId2());
            g.setStartTime();
            StartGame(g);
        }
    }

    /*disconnect the user when he close the program (the frame)*/
    boolean closeConnection(String userId) {
        clientDisconnected(userId);
        return true;
    }

    public synchronized void finishGameHandling(PlayerWonEvent e, GameState g) {
        g.disabledGame();
        this.sendGameState(g, false, false);
        g.setEndTime();
        databaseManager.writeGameStatistic(g);
        sendUserMsg(g);
    }

    /*send the users win and loose msg*/
    public void sendUserMsg(GameState g) {
        System.out.println("u1 " + g.getUserId1() + " u2 " + g.getUserId2());
        IRemoteClient client1 = (IRemoteClient) onlineClients.get(g.getUserId1());
        IRemoteClient client2 = (IRemoteClient) onlineClients.get(g.getUserId2());
        System.out.println(client1);
        System.out.println(client2);
        try {
            if (client1 != null && client2 != null && client1.isAlive() && client2.isAlive()) {
                if (g.getWinner().equalsIgnoreCase(g.getUserId1())) {
                    client1.getUserMsg(winMsg);
                } else {
                    client1.getUserMsg(looseMsg);
                }
                if (g.getWinner().equalsIgnoreCase(g.getUserId2())) {
                    client2.getUserMsg(winMsg);
                } else {
                    client2.getUserMsg(looseMsg);
                }
            }
        } catch (Exception e) {
            System.out.println("error in sending message to users");
        }
    }

    public String[][] getUserHistory(User u) {
        return databaseManager.retrieveGamesHistoryData(u);
    }


    /*for manging the clients*/
    private class ClientManager extends Thread {

        private ArrayList<String> onlineUsersList = new ArrayList<>(onlineClients.keySet());

        @Override
        public void run() {
            while (true) {
                checkDisconnectedUsers();
                sendOnlineUserList();
                try {
                    Thread.sleep(3000);
                } catch (Exception e) {
                }
            }
        }

        private synchronized void checkDisconnectedUsers() {
            for (String client : onlineClients.keySet()) {
                try {
                    if (onlineClients.get(client) != null && onlineClients.get(client).isAlive() == false) {//disconected user
                        clientDisconnected(client);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("cant disconnect user");
                }
            }

        }

        private synchronized void sendOnlineUserList() {

            if (isOnlineUserListChange()) {
                System.out.println("changed");
                for (String client : onlineClients.keySet()) {
                    try {
                        if (onlineClients.get(client) != null && onlineClients.get(client).isAlive()) {//disconected user
                            ArrayList<String> clientUsersList = new ArrayList<>(onlineUsersList);
                            clientUsersList.remove(client);

                            onlineClients.get(client).updateOnlineUsersList(clientUsersList);

                        }
                    } catch (Exception e) {
                        System.out.println("cant send list to user");
                    }
                }
            }
        }

        private boolean isOnlineUserListChange() {
            ArrayList<String> curOnlineUsersList = new ArrayList<>(onlineClients.keySet());
            if (curOnlineUsersList.isEmpty()) {
                onlineUsersList = curOnlineUsersList;
                return false;
            }
            else if(curOnlineUsersList.size() != onlineUsersList.size()){
                onlineUsersList = curOnlineUsersList;
                return true;
            }
            else {
                for (int i = 0; i < curOnlineUsersList.size(); i++) {
                    if (!onlineUsersList.get(i).equals(curOnlineUsersList.get(i))) {
                        onlineUsersList = curOnlineUsersList;
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
