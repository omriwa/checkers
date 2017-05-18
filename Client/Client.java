/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Model.GameState;
import Model.User;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import CheckerServer.IRemoteServer;
import Database.UserConfiguration;
import View.MyButton;
import View.OnlineUsersPanel;


import java.io.Serializable;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Client implements Serializable{

    private User user;
    private IRemoteClient remoteClient;
    private GameState gameState;
    private IRemoteServer remoteServer;
    private static Client client = null;
    
    private Client(){
        remoteClient = new RemoteClient();
    }
    
    public static Client getClient(String host , String objName){
        if(client == null){
            client = new Client();
            client.intialize(host, objName);
        }
        return client;
    }
    
    public static Client getClient(){
        return client;
    }
    
    private void intialize(String host , String objName){
        Registry registry;
        try {
                registry = LocateRegistry.getRegistry(host);
                remoteServer = (IRemoteServer) registry.lookup(objName);
                remoteClient = (IRemoteClient) UnicastRemoteObject.exportObject(new RemoteClient(), 0);
            } catch (Exception e) {
                System.out.println("error");//fix
            }
        
        System.out.println("Client intialzed");  
    }
    
    private void initializeUser(){

    }

    public User onRegister(User user , String pass){
        try {
            user =  remoteServer.registerInServer(user , pass , remoteClient);
            if (user!=null) {
                UserConfiguration.loadUserConfig(user);
                initializeUser();
            }
            else
                return  null;
        } catch (RemoteException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  null;
    }
    
    public boolean onLogOn(String username, String pass){
        try {
            User user = remoteServer.connectToServer(username, pass , remoteClient);
            if (user != null){
//                user = remoteServer.getUser(username , pass);
//                user = loadConfiguration(user.getConfigurationPath());
//                //xml loading user conf set to user
                if(gameState != null)
                    System.out.println("game state");
                return true;
            } else {
                //dialog doesn't exist
                return false;
            }
                
                //create user by gui
                //else
                //load user
                // RemoteClient callback = new
                // send the user and the callback to the server
                }
        catch (RemoteException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    public void onDisconnect(){
        try {
            remoteClient.disconnect();
        } catch (RemoteException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public IRemoteServer getremoteServer(){
        return remoteServer;
    }
    
    public void setGameState(GameState gameState){
        this.gameState = gameState;//need to refresh the gui
    }
    
    public GameState getGameState(){
        return gameState;
    }
    /*update the lists of online user in panel*/
    public void updateOnlineUserPanel(ArrayList<String> onlineUsers){
        OnlineUsersPanel.getOnlineUsersPanel().setOnlineUsers(onlineUsers);
    }
    /*send the board game to the server*/
    public void sendGameState(MyButton [][] board){
        gameState.setState(board);
        try {
            remoteServer.sendGameState(gameState);
        } catch (RemoteException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void changeTurn(){
        gameState.changeTurn();
        try {
            remoteServer.changeTurn(gameState);
        } catch (RemoteException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void startGame() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
  
}
