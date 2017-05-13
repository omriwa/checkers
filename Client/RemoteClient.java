/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.rmi.RemoteException;
import Model.GameState;
import java.util.ArrayList;

public class RemoteClient implements IRemoteClient {

    boolean connected = true;

    public RemoteClient() {

    }

    @Override
    public void sendGameState(GameState gameState) throws RemoteException {
        Client.getClient().setGameState(gameState);
    }

    @Override
    public void updateOnlineUsersList(ArrayList<String> onlineUsers) {
        Client.getClient().updateOnlineUserPanel(onlineUsers);
    }

    @Override
    public void disconnect() {
        connected = false;
    }

    @Override
    public boolean isAlive() {
        return connected;
    }
}
