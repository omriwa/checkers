package CheckerServer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Model.User;
import View.GameFrame;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import javax.swing.JFrame;
/**
 *
 * @author omri
 */

public interface IRemoteServer extends Remote{
    
    public boolean connectToServer(String username , String password) throws RemoteException;
    
    public ArrayList<User> getOnlineUsers() throws RemoteException;

    public boolean registerInServer(String username, String password) throws RemoteException;
    
    public User getUser(String username , String password) throws RemoteException;

    
}
