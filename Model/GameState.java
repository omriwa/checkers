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
public class GameState {
    
    private View.MyButton [][] gameVessels = null;
    private boolean player1Turn = true;
    private String userID1 , UserID2;
    
    public GameState(String u1 , String u2){
        userID1 = u1;
        UserID2 = u2;
    }
    
    public View.MyButton[][] getState(){
        return gameVessels;
    }
    
    public void changeTurn(){
        player1Turn = !player1Turn;
    }
    
    public boolean isPlayer1Turn(){
        return player1Turn;
    }
    
    public void setState(View.MyButton[][] vessels){
        gameVessels = vessels;
    }
    public String getUserId1(){
        return userID1;
    }
    public String getUserId2(){
        return UserID2;
    }
    
}
