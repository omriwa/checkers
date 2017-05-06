package CheckerServer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Client.IRemoteClient;
import Model.GameState;
import Model.User;
import View.GameFrame;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Set;
import javax.swing.JFrame;
/**
 *
 * @author omri
 */

public interface IRemoteServer extends Remote{
    
    public User connectToServer(String username , String password , IRemoteClient b) throws RemoteException;
    
    public Set<String> getOnlineUsers() throws RemoteException;

    public User registerInServer(String username, String password , IRemoteClient b) throws RemoteException;
    
    public User getUser(String username , String password) throws RemoteException;

    public void sendGameState(GameState gameState) throws RemoteException;
    public void changeTurn(GameState gameState) throws RemoteException;
}
