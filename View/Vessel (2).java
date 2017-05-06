package View;


import java.io.Serializable;


public class Vessel implements Serializable {
	private boolean queen = false ,player1Vessel;
	
	public Vessel(boolean b){
		player1Vessel = b;
	}
	
	public boolean isPlayer1Vessel(){
		return player1Vessel;
	}
    
    public void killVessel(){
    	try {
			this.finalize();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void setPlayer1(boolean b)
    {
    	player1Vessel = b;
    }
    
    public void setQueen(){
    	queen = true;
    }
    
    public boolean isQueen(){
    	return queen;
    }
    
}
