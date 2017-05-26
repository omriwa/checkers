package Controller;

import Model.PlayerWonEvent;
import Model.Position;
import View.Vessel;
import View.MyButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.Timer;
import Model.GameState;
import Model.P1WonEvent;
import View.GameFrame;
import java.awt.Frame;
import javax.swing.JOptionPane;

public class VesselListener implements ActionListener, Serializable {

    private MyButton[][] board;
    private ArrayList<Position> posList;
    private Position from, to = null, curr;
    private int dist;
    private Judge judge;
    private Timer playTime = new Timer(3000, this);
    private boolean playerFinishMov = false;
    private GameState gamestate = null;
    private boolean player1 = true;

    public VesselListener(MyButton[][] b) {
        board = b;
        judge = new Judge(board, true);
        posList = new ArrayList<>();
    }

    public void actionPerformed(ActionEvent e) {
        gamestate = Client.Client.getClient().getGameState();
        if (gamestate != null && gamestate.canPlay()) {
            if (playTime.equals(e.getSource())) {//timer is finished
                playerFinishMov = true;
                playTime.stop();//stop the timer
            } else {
                try {

                    Vessel vessel = ((MyButton) e.getSource()).getVessel();
                    if (gamestate.isPlayer1Turn() && judge.isPlayer1()) {//first player turn
                        if (vessel == null || vessel.isPlayer1Vessel()) {
                            this.determineMove(e);
                        }
                    }
                    if (!gamestate.isPlayer1Turn() && !judge.isPlayer1()) {//second player turn
                        if (vessel == null || !vessel.isPlayer1Vessel()) {
                            this.determineMove(e);
                        }
                    }
                } catch (Exception ex) {

                }
            }

            if (playerFinishMov) {//player finished his moves
                posList.clear();
                System.out.println("player one turn " + judge.isPlayer1());
                Client.Client.getClient().changeTurn();//change the turn of players
                Client.Client.getClient().sendGameState(board);//send the board to the server
                playerWonHandler(judge.isGameEnd());//check if the game is finished

                playerFinishMov = false;
            }
        }
    }

    private void determineMove(ActionEvent e) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (e.getSource() == board[i][j]) {	 //saving pointer to the pressed button position 
                    curr = new Position(i, j);
                    this.addToPosList(curr);
                    if (posList.size() == 2)//if there is 2 positions in posList
                    {
                        from = posList.get(0);
                        to = posList.get(1);
                        Position pos = posList.get(0);//first place
                        Vessel vessel = board[pos.getI()][pos.getJ()].getVessel();
                        boolean isQueen = false;
                        if (!playTime.isRunning() && vessel.isQueen()) {//first time queen is eating
                            isQueen = true;
                        }
                        //determining which action to do according to the distance value
                        if (eatAndMove(isQueen, vessel)) {
                            System.out.println("eat and move");
                        } else if (simpleMove(vessel)) {
                            System.out.println("just move");
                        } else {//illegal move
                            posList.clear();//clearing the list for the next 2 positions
                        }
                        deserveQueen(to);
                        break;
                    }
                }
            }
        }
    }

    private boolean simpleMove(Vessel vessel) {
        if (!playTime.isRunning() && from.isLegalMove(curr, judge.isPlayer1(), vessel.isQueen(), board))//player just move without eating
        {//move
            move(from, to);
            posList.clear();
            playerFinishMov = true;
            return true;
        }
        return false;
    }

    private boolean eatAndMove(boolean isQueen, Vessel vessel) {
        if (from.legalEatMove(to, board, isQueen, vessel.isPlayer1Vessel())) {//eat & move
            eat(from.speEatMove(from, to, board, vessel.isPlayer1Vessel()));//and move
            // "to" will become "from" if there is another eat move - for multiple eating
            posList.remove(from);
            playTime.restart();
            return true;
        }
        return false;
    }

    /*add position to pos list*/
    private void addToPosList(Position p) {
        if ((posList.isEmpty() && board[p.getI()][p.getJ()].thereIsVessel())
                || // if the user hit a vessel
                (!(posList.isEmpty()) && !(board[p.getI()][p.getJ()].thereIsVessel())))//hit free spot
        {
            posList.add(p);
            System.out.println("cur add " + p.toString());
        }
    }

    //move the vessel from one position to another
    private void move(Position from, Position to) {
        Vessel vesToMove = board[from.getI()][from.getJ()].getVessel();
        board[from.getI()][from.getJ()].setVessel(null);//disconnect the vessel
        board[to.getI()][to.getJ()].setVessel(vesToMove);
        board[to.getI()][to.getJ()].repaint();
    }//end of move

    //eating the oponents's vessel
    public void eat(boolean specialEat)//when queen do a regular move first and then eat
    {
        int xDir = to.getI() - from.getI(), yDir = to.getJ() - from.getJ();
        int rAdd, cAdd;
        rAdd = to.getAddition(xDir);
        cAdd = to.getAddition(yDir);

        board[to.getI() - rAdd][to.getJ() - cAdd].getVessel().killVessel();
        board[to.getI() - rAdd][to.getJ() - cAdd].setVessel(null);
        board[to.getI() - rAdd][to.getJ() - cAdd].repaint();
        move(from, to);

    }//end of eat

    /*check if the player's vessel deserve a queen*/
    private void deserveQueen(Position to) {
        Vessel vessel = board[to.getI()][to.getJ()].getVessel();
        if (!vessel.isPlayer1Vessel() && !judge.isPlayer1()) {//vessel of player 1 and he is playing
            if (to.getI() == 0) {
                vessel.setQueen();
            }
        }
        if (vessel.isPlayer1Vessel() && judge.isPlayer1()) {//vessel of player 2 and he is playing
            if (to.getI() == board.length - 1) {
                vessel.setQueen();
            }
        }
        board[to.getI()][to.getJ()].repaint();
    }


    /*receive event of the player that won and show message to user*/
    public void playerWonHandler(PlayerWonEvent e) {
        if (e != null) {
            gamestate.disabledGame();
            String msg = "Player ";
            if (e instanceof P1WonEvent) {//player 1 won
                msg += "one won";
                gamestate.setWinner(gamestate.getUserId1());
            } else {//player 2 won
                msg += "two won";
                gamestate.setWinner(gamestate.getUserId2());
            }
            JOptionPane.showMessageDialog(new Frame(), msg);
            writeStatistic(e);
        }

    }
    
    public void setPlayer1(boolean isPlayer1){
        player1 = isPlayer1;
    }
    
    private void writeStatistic(PlayerWonEvent e){
        if(e instanceof P1WonEvent && player1)
            Client.Client.getClient().writeStatistics(gamestate);
        else
           Client.Client.getClient().writeStatistics(gamestate); 
    }
}
