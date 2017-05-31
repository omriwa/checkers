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
import Model.GameInvitation;
import View.*;


import java.io.Serializable;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


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
    
    public User getUser(){
        return user;
    }
    
    public void setConnectionAlive(){
        try {
            remoteClient.setAlive();
        } catch (RemoteException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void intialize(String host , String objName){
        Registry registry;
        try {
                registry = LocateRegistry.getRegistry(host);
                remoteServer = (IRemoteServer) registry.lookup(objName);
                remoteClient = (IRemoteClient) UnicastRemoteObject.exportObject(new RemoteClient(), 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        
        System.out.println("Client intialzed");  
    }
    
    private void initializeUser(){

    }

    public boolean onRegister(User user , String pass){
        try {
            user =  remoteServer.registerInServer(user , pass , remoteClient);
            if (user!=null) {
                UserConfiguration.saveUserConfig(user);
                return true;
            }
            else
                return false;
        } catch (RemoteException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean onLogOn(String username, String pass){
        try {
            user = remoteServer.connectToServer(username, pass , remoteClient);
            if (user  != null){
                UserConfiguration.loadUserConfig(user);
                MyMenu.getMenuPanel().setBackground(user.getColor());
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
    
    public boolean onClose(){
        if(user == null)//if the user didnt sign in
            return true;
        try {
            return remoteServer.closeConnection(user.getUsername());
        } catch (RemoteException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public void onDisconnect(){
        try {
            remoteClient.disconnect();
        } catch (RemoteException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public IRemoteServer getRemoteServer(){
        return remoteServer;
    }
    
    public void setGameState(GameState gameState){
        this.gameState = gameState;//need to refresh the gui
        System.out.println("user board " + user.getUsername());
        GamePanel.getGamePlayPanel().setUpNewBoard(gameState.getBoard());
        GamePanel.getGamePlayPanel().getListener().getJudge().setBoard(gameState.getState());
        if(user.getUsername().equalsIgnoreCase(gameState.getUserId1()))
            GamePanel.getGamePlayPanel().getListener().setPlayer1(true);
        else
            GamePanel.getGamePlayPanel().getListener().setPlayer1(false);
        GamePanel.getGamePlayPanel().repaint();

    }
    
    public GameState getGameState(){
        return gameState;
    }
    /*update the lists of online user in panel*/
    public void updateOnlineUserPanel(ArrayList<String> onlineUsers){
        OnlineUsersPanel.getOnlineUsersPanel().setOnlineUsers(onlineUsers);
    }
    /*send the board game to the server and change the game turn*/
    public void changeGameTurn(MyButton [][] board){
        gameState.setState(board);
        try {
            remoteServer.changeGameTurn(gameState);
        } catch (RemoteException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendInvitation(String opponentId){
        GameInvitation invitation = new GameInvitation(user.getUsername(), opponentId);
        try {
            remoteServer.sendInvitation(invitation);
        } catch (RemoteException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public GameInvitation receiveInvitation(GameInvitation invitation){
        int dialogButton = JOptionPane.YES_NO_OPTION;
        JOptionPane.showConfirmDialog (null, "Would you like to play with " + invitation.getUserId1(),"Info",dialogButton);
        if(dialogButton == JOptionPane.YES_OPTION)
            invitation.setAccept(true);
        return invitation;
    }

    public void writeStatistics(GameState gamestate) {
        try {
            remoteServer.writeSatistics(gamestate);
        } catch (RemoteException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  
}
