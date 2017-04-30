package Controller;



import Model.PlayerWonEvent;
import Model.P1WonEvent;
import Model.P2WonEvent;
import View.CheckersGraphics;
import View.MyButton;
import java.awt.Component;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.EventListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Judge extends Component implements Serializable{
	private CheckersGraphics boardGame;
	private boolean player1Turn;
	private int p1NumVessel = 0, p2NumVessel = 0;
        
	
	public Judge(CheckersGraphics board , boolean p1){
		boardGame = board;
                player1Turn = p1;
		MyButton [][] buttons = boardGame.getButtons();
		for(int i=0 ; i<boardGame.getRow();i++)//count the number of vessels for each player
			for(int j=0;j<boardGame.getCol();j++){
				if(buttons[i][j] != null && buttons[i][j].thereIsVessel()){//there are vessels 
					 if(buttons[i][j].getVessel().isPlayer1Vessel())//vessel of p1
						 p1NumVessel++;
					 else
						 p2NumVessel++;
					}
			}
	}
	
	/*return true if p1 won else false p2 won*/
	public boolean player1Won(){
		if(p1NumVessel == 0)
			return true;
		else
			return false;
		
	}
        /*return the board of buttons*/
        public MyButton[][] getBoard(){
            return boardGame.getButtons();
        }
	
	/*check if game end and send event to listener about who win*/
	public PlayerWonEvent isGameEnd() {
		if(p1NumVessel != 0 && p2NumVessel != 0)//game ended
			return null;
		else{//send massage who won
			if(player1Won()){//player 1 won
				return new P1WonEvent(this);
			}
			else{//player2 won
				return new P2WonEvent(this);
			}
				
		}
		
	}
	
	//need to fix it
	
	/*return the players turn*/
	public boolean isPlayer1()
	{
		return player1Turn;
	}
                

}
