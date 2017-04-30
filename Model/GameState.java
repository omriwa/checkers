/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import View.MyButton;

/**
 *
 * @author omri
 */
public class GameState {
    
    private MyButton [][] gameVessels = null;
    private boolean player1Turn = true;
    
    public GameState(){
        
    }
    
    public MyButton[][] getState(){
        return gameVessels;
    }
    
    public boolean isPlayer1Turn(){
        return player1Turn;
    }
    
    public void changeTurn(){
        player1Turn = !player1Turn;
    }
    
    public void setState(MyButton[][] vessels){
        gameVessels = vessels;
    }
    
}
