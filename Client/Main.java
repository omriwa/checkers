/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Model.User;
import View.GameFrame;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import CheckerServer.IRemoteServer;

/**
 *
 * @author omri
 */
public class Main {
      public static void main(String [] args){
        try{
            User user = new User();
            
            GameFrame gameFrame = new GameFrame(gameManager);
            while(true){}
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
