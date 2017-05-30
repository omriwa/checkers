/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.Judge;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JFrame;

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
        this.add(mainPanel);
        this.setJMenuBar(MyMenu.getMenuPanel(this));
        this.setVisible(true);
        this.setResizable(false);
        this.addWindowListener(new Listener());
    }
    
    public static GameFrame getGameFrame(){
        if(gameFrame == null)
           gameFrame = new GameFrame();
           
        return gameFrame;
    }
    
    public MainPanel getMainPanel(){
        return mainPanel;
    }
    
    private class Listener extends WindowAdapter {
            public void windowClosing(WindowEvent e)
            {
                if(Client.Client.getClient().onClose())
                    GameFrame.getGameFrame().setDefaultCloseOperation(GameFrame.EXIT_ON_CLOSE);
            }
    }
  
    
    public static void main(String [] args){
        GameFrame f = new GameFrame();
        f.getMainPanel().setGamePanel();
        ArrayList<String> arr = new ArrayList<String>();
        arr.add("omri");
        arr.add("vit");
        OnlineUsersPanel.getOnlineUsersPanel().setOnlineUsers(arr);
        GamePanel.getGamePlayPanel().invalidate();
        GamePanel.getGamePlayPanel().validate();

    }
    
}
