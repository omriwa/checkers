package CheckerServer;

import Client.IRemoteClient;
import Model.GameState;
import Model.User;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;


public interface IRemoteServer extends Remote , Serializable{
    
    public User connectToServer(String username , String password , IRemoteClient b) throws RemoteException;
    
    public Set<String> getOnlineUsers() throws RemoteException;

    public User registerInServer(String username, String password , IRemoteClient b) throws RemoteException;
    
    public User getUser(String username , String password) throws RemoteException;

    public void sendGameState(GameState gameState) throws RemoteException;
    public void changeTurn(GameState gameState) throws RemoteException;
}
