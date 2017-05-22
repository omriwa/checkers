package Client;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Model.GameInvitation;
import Model.GameState;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;


// Callback remote object for invoke methods from server
public interface IRemoteClient extends Remote, Serializable {

    public void sendGameState(GameState gameState) throws RemoteException;
    
    public void updateOnlineUsersList(ArrayList<String> onlineUsers) throws RemoteException;

    public boolean isAlive() throws RemoteException;
    
    public void disconnect() throws RemoteException;
    
    public void diconnect(String m) throws RemoteException;
    
    public GameInvitation receiveGameInvitation(GameInvitation i) throws RemoteException;
}
