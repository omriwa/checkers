/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CheckerServer;

import Client.IRemoteClient;
import Model.GameInvitation;
import Model.GameState;
import Model.PlayerWonEvent;
import Model.User;
import java.rmi.RemoteException;
import java.util.Set;

public class RemoteServer implements IRemoteServer {

    @Override
    public User connectToServer(String username, String password, IRemoteClient b) throws RemoteException {
        return CheckersServer.getServer().connect(username, password, b);
    }

    @Override
    public Set<String> getOnlineUsers() throws RemoteException {
        return CheckersServer.getServer().getOnlineClients().keySet();
    }

    @Override
    public User registerInServer(User u, String pass, IRemoteClient b) throws RemoteException {
        return CheckersServer.getServer().register(u, pass, b);
    }

    @Override
    public User getUser(String username, String password) throws RemoteException {
        return CheckersServer.getServer().getUser(username, password);
    }

    @Override
    public void changeGameTurn(GameState gameState) {
        CheckersServer.getServer().changeGameTurn(gameState);
    }

    @Override
    public void sendInvitation(GameInvitation invitation) throws RemoteException {
        CheckersServer.getServer().sendInvitation(invitation);
    }

    @Override
    public void writeSatistics(GameState gamestate) throws RemoteException {
        CheckersServer.getServer().writeStatistics(gamestate);
    }

    @Override
    public boolean closeConnection(String userId) {
        return CheckersServer.getServer().closeConnection(userId);
    }
    
    public void gameFinishReg(PlayerWonEvent e , GameState g){
        CheckersServer.getServer().finishGameHandling(e , g);
    }

}
