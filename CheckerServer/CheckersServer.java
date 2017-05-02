package CheckerServer;

import Database.DatabaseManager;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import Model.GameState;
import Model.User;
import View.GameFrame;
import javax.swing.JFrame;
import Client.IRemoteClient;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

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
    private ArrayList<User> onlineUsers;
    private HashMap<String, GameState> games;

    private CheckersServer() {
        databaseManager = new DatabaseManager();
        this.initialize();
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            IRemoteServer remoteServer = (IRemoteServer) UnicastRemoteObject.exportObject(new RemoteServer(), 1099);
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

    public boolean connect(String username , String password , IRemoteClient b) {
        if (databaseManager.checkUserLogin(username, password)) {
            User user = databaseManager.getUserFromDB(username, password);
            if(onlineUsers.indexOf(user) == -1){//user isnt exists
                user.setBridge(b);
                onlineUsers.add(user);
            }
            return true;
        }
        return false;
    }

    private void createDataBase() {
        databaseManager.createTables();
    }

    private void clientDisconnected(String user) {
        onlineUsers.remove(user);
        if (games.containsKey(user)) {
            //games.get(user).getPlayerIngame();

            //processGameEnded(otherUser)
        }
    }

    public boolean register(String username, String password , IRemoteClient b) {
        if(databaseManager.registerUser(username, password)) {
            //add to hash
            return connect(username, password, b);
 
        }
        return false;
    }
    private Thread checkUsersOnline = new Thread(new Runnable() {
        public void run() {
            try {
                for (User user : onlineUsers) {
                    try {
                        if (!user.isAlive()){//disconected user
                            clientDisconnected(user);
                        }
                    } catch (Exception e) {
                        clientDisconnected(user);
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

    private void initialize() {
        RemoteServer manager = new RemoteServer();
        onlineUsers = new ArrayList<>();
        createDataBase();

    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }
    
    private void intializeGameState(String username , IRemoteClient remoteClient){
        GameState gameState = new GameState();
    }
    /*return the the user details from database and creating a user*/
    public User getUser(String username , String password){
        return databaseManager.getUserFromDB(username, password);
    }
}
