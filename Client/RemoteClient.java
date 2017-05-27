/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import static Client.Client.getClient;
import Model.GameInvitation;
import java.rmi.RemoteException;
import Model.GameState;
import java.awt.Frame;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class RemoteClient implements IRemoteClient {

    boolean connected = true;

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
    
    public void setAlive(){
        connected = true;
    }

    @Override
    /*disconnect the player from game , by disabling the game movement , and announce
    him about the disconnected opponent*/
    public void diconnect(String m) throws RemoteException {
        Client.getClient().getGameState().disabledGame();//stop the game
        JOptionPane.showMessageDialog(new Frame(),m);
    }
    
    @Override
    public GameInvitation receiveGameInvitation(GameInvitation invitation){
        return Client.getClient().receiveInvitation(invitation);
    }

}
