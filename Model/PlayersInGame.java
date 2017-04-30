/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author omri
 */
public class PlayersInGame {
    
    private User user1 , user2;
    
    public PlayersInGame(User u1 , User u2){
        user1 = u1;
        user2 = u2;
    }
    
    public User getP1(){
        return user1;
    }
    
    public User getP2(){
        return user2;
    }
    
    public boolean isPlayerInGame(User u){
        if(u.getUsername().equalsIgnoreCase(user1.getUsername()) 
                ||  
           u.getUsername().equalsIgnoreCase(user2.getUsername()))
            return true;
        else
            return false;
    }
}
