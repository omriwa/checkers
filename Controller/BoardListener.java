package Controller;

import Model.GameManager;
import Model.PlayerWonEvent;
import Model.Position;
import View.CheckersGraphics;
import View.Vessel;
import View.MyButton;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class BoardListener implements ActionListener ,Serializable {
    
	private MyButton[][] buttons;
	private ArrayList <Position> posList;
	private Position from , to = null , curr ;	
	private int dist;
	private Judge judge;
        private Timer playTime = new Timer(3000 , this);
        private boolean playerFinishMov = false;
        private Model.GameManager manager;
	
	public BoardListener(CheckersGraphics dg)
	{	
		buttons = dg.getButtons();
		posList = new ArrayList<Position>();
	}
	
        
        public void setJudge(Judge j){
            judge = j;
        }
		
	
		public void actionPerformed(ActionEvent e) {
                    if(playTime.equals(e.getSource())){//timer is finished
                        playerFinishMov = true;
                        playTime.stop();//stop the timer
                    }
                    else{
                        try{
                            
                        
                            Vessel vessel = ((MyButton) e.getSource()).getVessel();
                            if(manager.player1Turn() && judge.isPlayer1()){//first player turn
                                if(vessel == null || vessel.isPlayer1Vessel()){
                                    this.determineMove(e);
                                }
                            }
                            if(!manager.player1Turn() && !judge.isPlayer1()){//second player turn
                                if(vessel == null || !vessel.isPlayer1Vessel()){
                                    this.determineMove(e);
                                }
                            }  
                        }
                        catch(Exception ex){
                            
                        }
                    }
                    
                if(playerFinishMov){//player finished his moves
                        try {
                            posList.clear();
                            System.out.println("player one turn " + judge.isPlayer1());
                            manager.changeTurn(buttons);//change the turn of players
                            playerWonHandler(judge.isGameEnd());//check if the game is finished
                        } catch (RemoteException ex) {
                            Logger.getLogger(BoardListener.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        playerFinishMov = false;
                    }
		
		
		
		
		
		}//end of actionPerformed

                private void determineMove(ActionEvent e){
                    //scanning the checkers board to determine which position to handle	
                    
                    
                    for(int i = 0 ; i < buttons.length ; i++)
                    {
                            for(int j = 0 ; j < buttons[i].length ; j++)
                            {
                                    //adding positions to the positoonList
                                    if(e.getSource() == buttons[i][j])
                                    {	 //saving pointer to the pressed button position 
                                            curr = new Position(i,j);
                                            
                                         if((posList.isEmpty() && buttons[i][j].thereIsVessel()) || // if the user hit a vessel
                                                    (!(posList.isEmpty()) && !(buttons[i][j].thereIsVessel())))//hit free spot
                                            {
                                                posList.add(curr);
                                                System.out.println("cur add " + curr.toString());                                                            
                                            }

                                            if(posList.size() == 2)//if there is 2 positions in posList
                                            {
                                                    from = posList.get(0);
                                                    to = posList.get(1);
                                                    Position pos = posList.get(0);//first place
                                                    Vessel vessel = buttons[pos.getI()][pos.getJ()].getVessel();
                                                    
                                                    
                                                    System.out.println(from + " -> " + to ); 
                                                    boolean isQueen = false;
                                                    if(!playTime.isRunning() && vessel.isQueen()){//first time queen is eating
                                                        isQueen = true;
                                                    }
                                                    //determining which action to do according to the distance value
                                                    
                                                    if(from.legalEatMove(to, buttons, isQueen , vessel.isPlayer1Vessel()))    
                                                    { 
                                                        System.out.println(" eat & move");
                                                            eat(from.speEatMove(from, to, buttons, vessel.isPlayer1Vessel()));//and move
                                                            // "to" will become "from" if there is another eat move - for multiple eating
                                                            posList.remove(from);
                                                            playTime.restart();    
                                                    }

                                                    else if(!playTime.isRunning() && from.isLegalMove(curr , judge.isPlayer1() , vessel.isQueen(),buttons))//player just move without eating
                                                    {
                                                        System.out.println(" move");
                                                            move(from , to);
                                                            posList.clear();
                                                            playerFinishMov = true;
                                                            
                                   
                                                    }
                                                    else{//dist = other illegal distance 
                                                            System.out.println("illegal move");
                                                            posList.clear();//clearing the list for the next 2 positions
                                                    }
                                                    deserveQueen(to);
                                                    break;
                                            }
                                    }
                            }
                    }//end of for loop
                }
                
		//move the vessel from one position to another
		private void move(Position from , Position to)
		{
                    Vessel vesToMove = buttons[from.getI()][from.getJ()].getVessel();
                    buttons[from.getI()][from.getJ()].setVessel(null);//disconnect the vessel
                    buttons[to.getI()][to.getJ()].setVessel(vesToMove);
                    buttons[to.getI()][to.getJ()].repaint();
		}//end of move
		

		
		//eating the oponents's vessel
		public void eat(boolean specialEat)//when queen do a regular move first and then eat
		{
                    int xDir = to.getI() - from.getI(), yDir = to.getJ() - from.getJ();
                            int rAdd , cAdd;
                    rAdd = to.getAddition(xDir);
                    cAdd = to.getAddition(yDir); 
                            
       
                        buttons[to.getI() - rAdd][to.getJ() - cAdd].getVessel().killVessel();
                        buttons[to.getI() - rAdd][to.getJ() - cAdd].setVessel(null);
                         buttons[to.getI() - rAdd][to.getJ() - cAdd].repaint();
                        move(from , to);
                        
		}//end of eat
		
			
            /*check if the player's vessel deserve a queen*/
            private void deserveQueen(Position to){
                Vessel vessel = buttons[to.getI()][to.getJ()].getVessel();
                if(!vessel.isPlayer1Vessel() && !judge.isPlayer1()){//vessel of player 1 and he is playing
                    if(to.getI() == 0)
                        vessel.setQueen();
                }
                if(vessel.isPlayer1Vessel() && judge.isPlayer1()){//vessel of player 2 and he is playing
                    if(to.getI() == buttons.length - 1)
                        vessel.setQueen();
                }
                buttons[to.getI()][to.getJ()].repaint();
            }
		

	    /*receive event of the player that won and show message to user*/
		public void playerWonHandler(PlayerWonEvent e) 
		{
                    if(e != null){
                        try {
                          manager.startGame();
                        } catch (RemoteException ex) {
                            Logger.getLogger(BoardListener.class.getName()).log(Level.SEVERE, null, ex);
                        }  
                    }
                    
		}

		
                public void setManager(GameManager m){
                    manager = m;
                }
	
			
	}//end of class listener
		


	
	

