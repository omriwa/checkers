/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import View.GameFrame;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.JPanel;
import CheckerServer.IRemoteServer;
import Model.GameState;

/**
 *
 * @author omri
 */
public class RemoteClient implements IRemoteClient{

    private static IRemoteServer gameManager;
    
    public RemoteClient(){
        
    }

    @Override
    public boolean isGoodObject() throws RemoteException {
//client.getGameS
        return true;
    }

    @Override
    public void sendGameState(GameState gameState) throws RemoteException {
        Client.getClient().setGameState(gameState);
    }
    
}
