package View;


import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

import javax.swing.JButton;

public class MyButton extends JButton implements Serializable{
	private Vessel vessel;
	
	
	
	public boolean thereIsVessel(){
		if(vessel != null)
			return true;
		else
			return false;
	}
	
	
	public void setVessel(Vessel v){
		vessel = v;
	}
	
	public void delVessel(){
		vessel.killVessel();
		this.vessel = null;
		repaint();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		if(thereIsVessel()){
			if(vessel.isPlayer1Vessel())
				g.setColor(Color.darkGray);
			else
				g.setColor(new Color(90,29,29));
			
			int x=(int)(this.getWidth()*0.25),y=(int)(this.getHeight()*0.3),
			    width=(int)(this.getWidth()*0.5),height=(int)(this.getHeight()*0.5);
			g.fillOval(x, y, width, height);
			
			if(vessel.isQueen()){
				x=(int)(this.getWidth()*0.35);
				y=(int)(this.getHeight()*0.4);
				width=(int)(this.getWidth()*0.3);
				height=(int)(this.getHeight()*0.3);
                                if(vessel.isPlayer1Vessel())
                                    g.setColor(Color.cyan);
                                else
                                    g.setColor(Color.blue);
				g.fillOval(x, y, width, height);
			}
		}
			
	}
	/*move the vessel from origin button to destination button*/
	public void moveVessel(MyButton button){
		Vessel v = this.vessel;
		this.vessel = null;
		button.setVessel(v);
		repaint();
	}

	public Vessel getVessel()
	{
		return vessel;
	}
	
	
	
	public void repaintButton()
	{
		repaint();
	}
	
}
