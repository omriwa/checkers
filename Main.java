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
            Client client = Client.getClient("localhost" , "gameManager");
            GameFrame gameFrame = new GameFrame();
            while(true){}
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
