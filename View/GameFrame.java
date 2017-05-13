/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.Judge;
import java.io.Serializable;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author omri
 */
public class GameFrame extends JFrame implements Serializable{
    
    private MainPanel mainPanel; 
    private static GameFrame gameFrame = null;
    private Judge judge;
    
    private GameFrame() {
        mainPanel = MainPanel.getMainPanel();
        
        //setup
        this.setSize(450, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(mainPanel);
        this.setJMenuBar(MyMenu.getMenuPanel(this));
        this.setVisible(true);
        this.setResizable(false);
    }
    
    public static GameFrame getGameFrame(){
        if(gameFrame == null)
           gameFrame = new GameFrame();
           
        return gameFrame;
    }
    
    public MainPanel getMainPanel(){
        return mainPanel;
    }
  
    
    public static void main(String [] args){
        GameFrame f = new GameFrame();
       
    }
    
}
