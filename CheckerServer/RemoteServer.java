/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CheckerServer;

import Model.User;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author omri
 */
public class RemoteServer implements IRemoteServer{
    @Override
    public boolean connectToServer(String username , String password) throws RemoteException {  
        return CheckersServer.getServer().connect(username , password);
       
    }

    @Override
    public ArrayList<User> getOnlineUsers() throws RemoteException {
        return null;//fix
    }

    @Override
    public boolean registerInServer(String username, String password) throws RemoteException {
        return CheckersServer.getServer().register(username , password);
    }
 
}
