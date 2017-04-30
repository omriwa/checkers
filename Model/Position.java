package Model;


import View.Vessel;
import View.MyButton;
import java.io.Serializable;


public class Position implements Serializable{

	private int i , j;
	
	
	public Position(int i,int j)
	{
		this.i = i;
		this.j = j;
	}
	
	
	public int getI()
	{
		return i;
	}
	
	public int getJ()
	{
		return j;
	}
	
	//calculates the distance between 2 positions
	//returns the distance as absolute value
	//returning zero means other distance then one and two
	
	public int checkDis(Position p)
	{
		int di , dj;
		
		di = Math.abs(this.i - p.i);
		dj = Math.abs(this.j - p.j);
                
                if(di == 2 && dj == 2)//eating move
			return 2;
		else if(di == 1 && dj == 1)//simple move
			return 1;
		else 
			return 0;
	}
	
	//returns the distance between 2 positions
	
	public boolean equals(Object o)
	{
	Position p = (Position)o;
		
		if(this.i == p.i && this.j == p.j)
			return true;
		
		return false;
	}
	
	public String toString()
	{
		return "("+i+","+j+")" ;
	}
        
        public boolean isLegalMove(Position to ,boolean player1 , boolean isQueen
        , MyButton [][] board)
        {
            Position from = this;
            
                int rowDis = (from.getI() - to.getI()),
                    colDis = (from.getJ() - to.getJ());
                
                if(!isQueen){
                    if(legalRegMove(rowDis, player1) == false){
                        return false;
                    }
                    rowDis = Math.abs(rowDis);
                    colDis = Math.abs(colDis);
                    
                     if(rowDis == 1 && colDis == 1){
                        return true;
                    }
                    else
                         return false;
                }
                //else its queen
                    return legalQueenRegMove(from, to, board);
                
        }
        /*check if legal just for move without eating and for regular vessel*/
        private boolean legalRegMove(int rowDis , boolean player1){
            if(player1){
                if(rowDis < 0 && player1)
                    return true;
            }
            else{
                if(rowDis > 0 && !player1)
                    return true;
            }
            
            return false;
        }
        /*check if legal move for queen vessel*/
        private boolean legalQueenRegMove(Position from , Position to , MyButton[][] board){
            int xDir = to.getI() - from.getI(), yDir = to.getJ() - from.getJ();
            int rAdd , cAdd , numOfStep = getNumOfStep(xDir, yDir);
            
            rAdd = getAddition(xDir);
            cAdd = getAddition(yDir);
            
            for(int i = from.getI() + rAdd, j = from.getJ() + cAdd , count = 0
                    ; count < numOfStep; i+= rAdd , j += cAdd , count++){
                    if(board[i][j].thereIsVessel())
                        return false;   
            }
            
            
            return true;
        }
        
        /*get the increcment for row and col in legal queen reg move*/
        public int getAddition(int x){
            if(x > 0)
                return 1;
            else if(x == 0)
                return 0;
            else
                return -1;
        }
        /*check if its a legal eat move*/
        public boolean legalEatMove(Position to , MyButton[][] board ,boolean isQueen , boolean player1){
            Position from = this;
            
            if(from.checkDis(to) == 0 && isQueen){
                return speEatMove(from, to, board , player1);
            }
            else if(from.checkDis(to) == 2 && !isQueen){
                return regEatMove(from, to, board , player1);
            }
            
            
            return false;
        }
        /*check if you eat a vessel and move just 2 squares*/
        private boolean regEatMove(Position from , Position to , MyButton[][] board , boolean player1){
            int xDir = to.getI() - from.getI(), yDir = to.getJ() - from.getJ();
            int rAdd , cAdd;
            
            rAdd = getAddition(xDir);
            cAdd = getAddition(yDir);
            
            Vessel vesToEat = board[from.getI() + rAdd][from.getJ() + cAdd].getVessel();
            if(vesToEat == null || vesToEat.isPlayer1Vessel() == player1)
                return false;
            
            return true;
        }
        
        public boolean speEatMove(Position from , Position to , MyButton[][] board , boolean player1){
            int xDir = to.getI() - from.getI(), yDir = to.getJ() - from.getJ();
            int rAdd , cAdd;
            
            rAdd = getAddition(xDir);
            cAdd = getAddition(yDir);
            
            Position f =  new Position(to.getI() - 2 * rAdd , to.getJ() - 2 * cAdd);
            if(!regEatMove(f, to, board , player1)){
                return false;
            }
            if(!legalQueenRegMove(from, f, board)){
                return false;
            }
                
            return true;
        }
        
        private int getNumOfStep(int xSpace , int ySpace){
            xSpace = Math.abs(xSpace);
            ySpace = Math.abs(ySpace);
            
            if(xSpace == 0)
                return ySpace;
            else
                return xSpace;
        }
	
}
