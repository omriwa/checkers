package Controller;

import Model.GameState;
import Model.PlayerWonEvent;
import Model.P1WonEvent;
import Model.P2WonEvent;
import View.MyButton;
import java.awt.Component;
import java.io.Serializable;

public class Judge extends Component implements Serializable {

    private MyButton[][] boardGame;
    private boolean player1Turn;
    private int p1NumVessel = 0, p2NumVessel = 0;

    public Judge(MyButton[][] board, boolean p1) {
        player1Turn = p1;
        setBoard(board);
    }
    
    public void setBoard(MyButton [][] board){
        boardGame = board;
        setNumVessels();
    }
    
    private void setNumVessels(){
        for (int i = 0; i < boardGame.length; i++)//count the number of vessels for each player
        {
            for (int j = 0; j < boardGame.length; j++) {
                if (boardGame[i][j] != null && boardGame[i][j].thereIsVessel()) {//there are vessels 
                    if (boardGame[i][j].getVessel().isPlayer1Vessel())//vessel of p1
                    {
                        p1NumVessel++;
                    } else {
                        p2NumVessel++;
                    }
                }
            }
        }
    }

    /*return true if p1 won else false p2 won*/
    public boolean player1Won() {
        if (p1NumVessel == 0) {
            return true;
        } else {
            return false;
        }

    }

    /*return the board of buttons*/
    public MyButton[][] getBoard() {
        return boardGame;
    }

    /*check if game end and send event to listener about who win*/
    public PlayerWonEvent isGameEnd() {
        System.out.println("p1 " + p1NumVessel + " p2 " + p2NumVessel);
        if (p1NumVessel != 0 && p2NumVessel != 0)//game ended
        {
            return null;
        } else {//send massage who won
            if (player1Won()) {//player 1 won
                return new P1WonEvent(this);
            } else {//player2 won
                return new P2WonEvent(this);
            }

        }

    }


    /*return the players turn*/
    public boolean isPlayer1() {
        return player1Turn;
    }

}
