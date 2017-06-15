/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import Client.Client;
import View.GameFrame;

/**
 *
 * @author omri
 */
public class Main {
      public static void main(String [] args){
        try{
            
            GameFrame gameFrame = GameFrame.getGameFrame();
            Client client = Client.getClient("localhost" , "gameManager");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
