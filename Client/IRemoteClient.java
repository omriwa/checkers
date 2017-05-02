package Client;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Model.GameState;
import Model.User;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author omri
 */

// Callback remote object for invoke methods from server
public interface IRemoteClient extends Remote, Serializable {

    public boolean isGoodObject() throws RemoteException;

    public void sendGameState(GameState gameState) throws RemoteException;
    
    public void updateOnlineUsersList(ArrayList<User> onlineUsers) throws RemoteException;
}
