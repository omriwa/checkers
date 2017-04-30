/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.io.Serializable;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author omri
 */
public class GameFrame extends JFrame implements Serializable{
    
    private MainPanel mainPanel; 
    
    public GameFrame() {
        mainPanel = MainPanel.getMainPanel();
        
        this.setSize(400, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(mainPanel);
        this.setJMenuBar(new MyMenu(this));
        this.setVisible(true);
    }
  
    
    public static void main(String [] args){
        GameFrame f = new GameFrame();
       
    }
    
}
