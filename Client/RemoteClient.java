/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.rmi.RemoteException;
import CheckerServer.IRemoteServer;
import Model.GameState;
import Model.User;
import View.OnlineUsersPanel;
import java.util.ArrayList;

/**
 *
 * @author omri
 */
public class RemoteClient implements IRemoteClient{
    
    public RemoteClient(){}

    @Override
    public boolean isGoodObject() throws RemoteException {
//client.getGameS
        return true;
    }

    @Override
    public void sendGameState(GameState gameState) throws RemoteException {
        Client.getClient().setGameState(gameState);
    }
    
    public void updateOnlineUsersList(ArrayList<User> onlineUsers){
        Client.getClient().updateOnlineUserPanel(onlineUsers);
    }
    
}
