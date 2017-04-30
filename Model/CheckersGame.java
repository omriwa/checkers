package Model;


import Controller.Judge;
import View.CheckersGraphics;
import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author omri
 */
public class CheckersGame implements Serializable {
    
    private CheckersGraphics gui;
    private Judge judge;
    
    public CheckersGame(CheckersGraphics g , Judge j){
        gui = g;
        judge = j;
        gui.setJudge(j);
    }
    
    public Judge getJudge(){
        return judge;
    }
    
    public CheckersGraphics getBoard(){
        return gui;
    }
}
