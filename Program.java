

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import CheckerServer.CheckersServer;
import java.rmi.RemoteException;

/**
 *
 * @author omri
 */
public class Program {

    public static void main(String[] args) throws RemoteException {
        CheckersServer server = CheckersServer.getServer();
        while(true){}
    }

}
