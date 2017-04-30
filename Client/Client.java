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
    
    private Client(){}
    
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
    
    public void onRegister(String username){//to fix
        
    }
    
    public boolean onLogOn(String username, String pass){
        try {
            if (remoteServer.connectToServer(username, pass)){
                user = remoteServer.getUser();
                user = loadConfiguration(user.getConfigurationPath());
                //xml loading user conf set to user
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
  
}
