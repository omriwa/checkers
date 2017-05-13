package CheckerServer;

import Client.Client;
import Database.DatabaseManager;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import Model.GameState;
import Model.User;
import Client.IRemoteClient;
import View.MyButton;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author omri
 */
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

    public User connect(String username , String password , IRemoteClient b) {
        User user;
        if ((user = databaseManager.getUserFromDB(username, password))!=null) {
            if(!onlineClients.containsKey(user.getUsername())){//user isnt exists
                user.setBridge(b);
                onlineClients.put(user.getUsername(), b);
                StartGame(new GameState(username, username));
                updateUsersListInGui();    
            }
        }
        return user;
    }

    private void createDataBase() {
        databaseManager.createTables();
    }

    private void clientDisconnected(String user) {
        onlineClients.remove(user);
        onlineClients.get(user).
        if (games.containsKey(user)) {
            
        }
    }

    public User register(String username, String password , IRemoteClient b) {
        if(databaseManager.registerUser(username, password)) {
            //add to hash
            return connect(username, password, b);
 
        }
        return null;
    }
    private Thread checkUsersOnline = new Thread(new Runnable() {
        public void run() {
            try {
                for (String client : onlineClients.keySet()) {
                    try {
                        if (onlineClients.get(client).isAlive()) {//disconected user
                            clientDisconnected(client);
                        }
                    } catch (Exception e) {
                        clientDisconnected(client);
                    }
                }
            } finally {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
            }
        }
    });

    private void initialize() {
        remoteServer = new RemoteServer();
        onlineClients = new HashMap<String,IRemoteClient>();
        createDataBase();
        Thread checkUsersOnline = new Thread(new Runnable() {
            public void run() {
                try {
                    for (String id : onlineClients.keySet()) {
                        try {
                            if (!onlineClients.get(id).isAlive()){//disconected user
                                clientDisconnected(id);
                            }
                        } catch (Exception e) {
                            clientDisconnected(id);
                        }
                    }
                } finally {
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                    }
                }
            }
        });
//        checkUsersOnline.start();
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    private void intializeGameState(String uId1, String uId2) {
        GameState gameState = new GameState(uId1, uId2);
        games.put(uId1, gameState);
        games.put(uId2, gameState);
        //send to users
        this.sendGameState(gameState);
    }

    /*return the the user details from database and creating a user*/
    public User getUser(String username, String password) {
        return databaseManager.getUserFromDB(username, password);
    }

    /*get online users*/
    public HashMap<String,IRemoteClient> getOnlineClients(){
        return onlineClients;
    }

    /*update online users lists in users panel*/
    public void updateUsersListInGui(){
        for(String client : onlineClients.keySet())
            try {
                ArrayList<String> x = new ArrayList<>();
                x.add("aaa");
                onlineClients.get(client).updateOnlineUsersList(x);
            } catch (RemoteException ex) {
                Logger.getLogger(CheckersServer.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    public void updateGameState(GameState gameState) throws RemoteException {
        /*update data structure*/
        games.put(gameState.getUserId1(), gameState);
        games.put(gameState.getUserId2(), gameState);
        sendGameState(gameState);//send game state to users

    }

    public void changeGameTurn(GameState gameState) {
        gameState.changeTurn();
        try {
            updateGameState(gameState);
        } catch (RemoteException ex) {
            Logger.getLogger(CheckersServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void StartGame(GameState gameState){
        sendGameState(gameState);
    }
    /*send the game state to the users that are playing together*/
    private void sendGameState(GameState gameState){
        /*update the users*/
        IRemoteClient client1 = (IRemoteClient) onlineClients.get(gameState.getUserId1());
        IRemoteClient client2 = (IRemoteClient) onlineClients.get(gameState.getUserId2());
        try {//upadate the users gamestate
            client1.sendGameState(gameState);
            client2.sendGameState(gameState);
        } catch (RemoteException e) {
        }
    }
    /*return array list of string ,of the usersname*/
    private ArrayList<String> getUsersName(String curUser){
        ArrayList<String> uName = new ArrayList<>();
        for(String name : onlineClients.keySet()){
            uName.add(name);
        }
        uName.remove(curUser);
        return uName;
    }

}
