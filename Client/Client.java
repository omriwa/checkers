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
import View.MyButton;
import View.OnlineUsersPanel;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author omri
 */

public class Client {
    private User user;
    private RemoteClient remoteClient;
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
            } catch (Exception e) {
                System.out.println("error");//fix
            }
        
            
    }
    
    public User onRegister(String username , String password){
        try {
            user =  remoteServer.registerInServer(username, password , remoteClient);
            if (user!=null) {
                UserConfiguration.loadUserConfig(user);
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
            if (remoteServer.connectToServer(username, pass , remoteClient)){
//                user = remoteServer.getUser(username , pass);
//                user = loadConfiguration(user.getConfigurationPath());
//                //xml loading user conf set to user
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
        catch (RemoteException ex) {}
        return false;
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
    public void updateOnlineUserPanel(ArrayList<User> onlineUsers){
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
