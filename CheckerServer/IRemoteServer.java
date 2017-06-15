package CheckerServer;

import Model.User;
import Client.IRemoteClient;
import Model.GameInvitation;
import Model.GameState;
import Model.PlayerWonEvent;
import Model.User;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

public interface IRemoteServer extends Remote, Serializable {

    public User connectToServer(String username, String password, IRemoteClient b) throws RemoteException;

    public Set<String> getOnlineUsers() throws RemoteException;

    public User registerInServer(User u, String pass, IRemoteClient b) throws RemoteException;

    public User getUser(String username, String password) throws RemoteException;

    public void changeGameTurn(GameState gameState) throws RemoteException;

    public void sendInvitation(GameInvitation invitation) throws RemoteException;

    public void writeSatistics(GameState gamestate) throws RemoteException;

    public boolean closeConnection(String userId) throws RemoteException;

    public void gameFinishReg(PlayerWonEvent e , GameState g) throws RemoteException;

    public String[][] getUserHistory(User user) throws RemoteException;

}
