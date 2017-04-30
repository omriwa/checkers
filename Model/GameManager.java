package Model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Model.PlayerWonEvent;
import View.MyButton;
import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 *
 * @author omri
 */

public interface GameManager extends Remote{
    
    public void setClient() throws RemoteException;
    public void updateGame(PlayerWonEvent e) throws RemoteException;
    public void startGame() throws RemoteException;
    public void changeTurn(MyButton[][] board) throws RemoteException;
    public boolean player1Turn() throws RemoteException;
    public String getGameLog() throws RemoteException;
    public int getNumOfPlayers()throws RemoteException;
    
}
