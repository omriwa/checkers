package CheckerServer;

import Database.DatabaseManager;
import java.rmi.RemoteException;
import java.util.HashMap;
import Model.GameState;
import Model.User;
import Client.IRemoteClient;
import Model.GameInvitation;
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

    private CheckersServer() {
        databaseManager = new DatabaseManager();
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

    public User connect(String username, String password, IRemoteClient b) {
        User user;
        if ((user = databaseManager.getUserFromDB(username, password)) != null) {
            if (!onlineClients.containsKey(user.getUsername())) {//user isnt exists
                user.setBridge(b);
                onlineClients.put(user.getUsername(), b);
            }
        }
        return user;
    }

    private void createDataBase() {
        databaseManager.createTables();
    }

    private void clientDisconnected(String user) {
        try {
            onlineClients.get(user).disconnect();
            onlineClients.remove(user);
        } catch (RemoteException ex) {
            Logger.getLogger(CheckersServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (games.containsKey(user)) {
            processGameStoped(games.get(user).getOtherUser(user));
            games.remove(user);//remove the game from hash of the disconnected user
        }
    }

    /*writing statistic of game to the db and announce the other player*/
    public void processGameStoped(String user) {
        GameState game = games.get(user);
        game.setWinner(user);
        game.setEndTime();//set finish time of the game
        databaseManager.writeGameStatistic(game);//writing the game statistic
        IRemoteClient client = onlineClients.get(user);
        String msg = "The other user disconnected";
        try {
            if(client != null)
                client.diconnect(msg);
        } catch (RemoteException ex) {
            Logger.getLogger(CheckersServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        games.remove(user);
    }

    public User register(User userInfo, String pass, IRemoteClient b) {
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
    public void updateUsersListInGui() {
        for (String client : onlineClients.keySet()) {
            try {
                onlineClients.get(client).updateOnlineUsersList(getUsersName(client));
            } catch (RemoteException ex) {
                Logger.getLogger(CheckersServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void writeStatistics(GameState g){
        databaseManager.writeGameStatistic(g);
    }

    public void changeGameTurn(GameState gameState) {
        gameState.changeTurn();
        sendGameState(gameState);
    }

    public void StartGame(GameState gameState) {
        games.put(gameState.getUserId1(), gameState);
        games.put(gameState.getUserId2(), gameState);
        sendGameState(gameState);
    }

    /*send the game state to the users that are playing together*/
    private void sendGameState(GameState gameState) {
        /*update the users*/
        IRemoteClient client1 = (IRemoteClient) onlineClients.get(gameState.getUserId1());
        IRemoteClient client2 = (IRemoteClient) onlineClients.get(gameState.getUserId2());
        try {//upadate the users gamestate
            gameState.enablePlaying();
            client1.sendGameState(gameState);
            gameState.changeTurn();
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
            if(client != null && client.isAlive())//the opponent exists and online
                invitation = client.receiveGameInvitation(invitation);
                } catch (RemoteException ex) {
            Logger.getLogger(CheckersServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(invitation.isAccept())
            StartGame(new GameState(invitation.getUserId1(), invitation.getUserId2()));
    }

    /*for manging the clients*/
    private class ClientManager extends Thread {

        @Override
        public void run() {
            while (true) {
                checkDisconnectedUsers();
                sendOnlineUserList();
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
            }
        }

        private void checkDisconnectedUsers() {
            for (String client : onlineClients.keySet()) {
                try {
                    if (onlineClients.get(client) != null && !onlineClients.get(client).isAlive()) {//disconected user
                        clientDisconnected(client);
                    }
                } catch (Exception e) {
                    System.out.println("cant disconnect user");
                }
            }

        }
        
        private void sendOnlineUserList(){
            ArrayList <String> onlineUsersList = new ArrayList<>(onlineClients.keySet());
            for(String client : onlineClients.keySet()){
                try {
                    if (onlineClients.get(client) != null && onlineClients.get(client).isAlive()) {//disconected user
                        ArrayList<String> clientUsersList = new ArrayList<>(onlineUsersList);
                        clientUsersList.remove(client);
                        if(!clientUsersList.isEmpty())
                            onlineClients.get(client).updateOnlineUsersList(clientUsersList);
                    }
                } catch (Exception e) {
                    System.out.println("cant send list to user");
                }
            }
        }
    }
}
