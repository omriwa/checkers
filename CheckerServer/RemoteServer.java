/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CheckerServer;

import Model.UserInfo;
import Client.IRemoteClient;
import Model.GameState;
import Model.User;
import java.rmi.RemoteException;
import java.util.Set;

public class RemoteServer implements IRemoteServer{
    @Override
    public User connectToServer(String username , String password , IRemoteClient b) throws RemoteException {  
        return CheckersServer.getServer().connect(username , password , b);
       
    }

    @Override
    public Set<String> getOnlineUsers() throws RemoteException {
        return CheckersServer.getServer().getOnlineClients().keySet();
    }

    @Override
    public User registerInServer(UserInfo u , IRemoteClient b) throws RemoteException {
        return CheckersServer.getServer().register(u, b);
    }

    @Override
    public User getUser(String username, String password) throws RemoteException {
        return CheckersServer.getServer().getUser(username , password);
    }
    @Override
    public void sendGameState(GameState gameState){
        try {
            CheckersServer.getServer().updateGameState(gameState);
        } catch (RemoteException ex) {
//            Logger.getLogger(RemoteServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void changeTurn(GameState gameState){
        CheckersServer.getServer().changeGameTurn(gameState);
    }
 
}
