package Controller;


import Controller.LogWriter;
import Database.DatabaseManager;
import View.MyButton;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import Database.DatabaseManager;
import Model.ClientManager;
import Model.GameManager;
import Model.P1WonEvent;
import Model.PlayerWonEvent;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author omri
 */
public class Manager implements GameManager {

    private ClientManager client1, client2; // client stubs
    private boolean player1Turn = true;// holding the player turn
    private LogWriter logWriter = null;
    private static int numOfPlayers = 0;
    private final DatabaseManager databaseManager = new DatabaseManager();

    @Override
    /*start game for both clients after they are connected to the registery*/
    public void startGame() throws RemoteException {
        if (client1 != null && client2 != null) {//if two clients are signed in
            boolean player1 = true;
            client1.startGame(player1);//player1
            client2.startGame(!player1);//player2
            logWriter = LogWriter.getLogWriter();
            logWriter.createNewGameLog();
            databaseManager.addGameInfo();
        }
    }

    @Override
    /*update the clients about the player who won and the end of the game*/
    public void updateGame(PlayerWonEvent e) throws RemoteException {
        logWriter.setScores((e.getClass().equals(P1WonEvent.class)));
        client1.deleteGame();
        client2.deleteGame();
        this.startGame();//start a new game
    }

    public String getGameLog() {
        return logWriter.getLog();
    }

    @Override
    /*set the client into the registery table*/
    public void setClient() throws RemoteException {
        Registry registry = LocateRegistry.getRegistry("localhost");
        String regName;
        if (client1 == null) {
            regName = "client1";
            try {
                client1 = (ClientManager) registry.lookup(regName);
            } catch (NotBoundException ex) {
                Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            regName = "client2";
            try {
                client2 = (ClientManager) registry.lookup(regName);
                this.startGame();
            } catch (NotBoundException ex) {
                Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    /*change the turn of the players*/
    public void changeTurn(MyButton[][] board) throws RemoteException {
        if (player1Turn) {//if p1 moved
            player1Turn = false;
            client2.updateGame(board);
        } else {//p2 moved
            player1Turn = true;
            client1.updateGame(board);
        }
    }

    public boolean player1Turn() throws RemoteException {
        return player1Turn;
    }

    @Override
    public int getNumOfPlayers() throws RemoteException {
        numOfPlayers++;
        return numOfPlayers;
    }

}
