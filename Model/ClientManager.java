package Model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author omri
 */
import View.MyButton;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientManager extends Remote {

    public void updateGame(MyButton[][] board) throws RemoteException;

    public void startGame(boolean p1) throws RemoteException;

    public void deleteGame() throws RemoteException;
}
