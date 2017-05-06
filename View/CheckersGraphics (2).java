package View;



import Controller.Judge;
import Controller.VesselListener;
import java.awt.BorderLayout;
import java.awt.*;
import java.awt.GridLayout;
import java.io.Serializable;

import javax.swing.JFrame;
import javax.swing.JPanel;
import CheckerServer.IRemoteServer;

public class CheckersGraphics extends JFrame implements Serializable{
	
	private int WIDTH = 600 , HEIGHT = 600 , ROW = 10, COL = 10;
	private MyButton [][] buttons ;
	private JPanel buttonsComponent = new JPanel();
	private VesselListener listener;
        private MyMenu menu;
	/*constructor of the graphics of the checkers*/
	public CheckersGraphics(){
		this.setVisible(true);
		buttons = new MyButton[ROW][COL];
                listener = new VesselListener(this);
                menu = new MyMenu();
                this.setJMenuBar(menu);
		BorderLayout bl = new BorderLayout();
		
		this.setLayout(bl);
		buttonsComponent.setLayout(new GridLayout(10,10));
		this.add(buttonsComponent,BorderLayout.CENTER);
		buttonSetUp();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(WIDTH , HEIGHT);
		vesselSetUp(0,4,true);//player1 set up vessels
		vesselSetUp(6,10,false);//player1 set up vessels
		//temporary
		//printMat();
	}
	/*board set up as black and white squares*/
	public void buttonSetUp(){
		boolean white = true;
		int count = 1;
		for(int i = 0 ; i < ROW ; i++){
			for(int j = 0 ; j < COL ; j++){
				buttons[i][j] = new MyButton();
				buttons[i][j].addActionListener(listener);
				buttonsComponent.add(buttons[i][j]);
				if((i+j)%2==0)
					buttons[i][j].setBackground(Color.white);
				else
					buttons[i][j].setBackground(Color.black);
			}
			
		}
	}
        /*set up the board from a new board*/
        public void setUpNewBoard(MyButton board[][]){
            for(int i = 0 ; i < ROW ; i++){
			for(int j = 0 ; j < COL ; j++){ 
                           if(board[i][j].thereIsVessel()){//move the vessel to this.board
                               buttons[i][j].setVessel(board[i][j].getVessel());
                           }
                           else{
                               buttons[i][j].setVessel(null);
                           }
                        }
            }
            this.repaint();
        }
	
	
	/*connect the vessel to buttons*/
	public void vesselSetUp(int startRow , int endRow,boolean player1){
		for(; startRow < endRow ; startRow++){
			for(int j=0 ; j < COL ; j++){
				if((startRow + j) % 2 != 0)
					buttons[startRow][j].setVessel(new Vessel(player1)); 
			}
		}
	}
	
	public void printMat()
	{
		for(int i= 0 ; i < buttons.length ; i++)
		{	
			for(int j = 0 ; j < buttons[i].length ; j++)
			{
				System.out.print("("+i+","+j+")");
			}
			System.out.println("");
		}
	}
	
	

	public MyButton [][] getButtons(){
		return buttons;
	}
	
	public int getRow(){
		return ROW;
	}
	
	public int getCol(){
		return COL;
	}
        
        public void setJudge(Judge j){
            listener.setJudge(j);
        }

	public CheckersGraphics getDamkaGraphics()
	{
		return this;
	}
        
        public void setManager(IRemoteServer m){
            listener.setManager(m);
            menu.setManager(m);
        }
        
        public void setClientNum(boolean c){
            menu.setClientNum(c);
        }
        
        public VesselListener getListener(){
            return listener;
        }
	
	
}
