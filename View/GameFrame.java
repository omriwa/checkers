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
    private WindowListener listener = null;
    
    private GameFrame() {
        mainPanel = MainPanel.getMainPanel();
        listener = new WindowListener();
        //setup
        this.setSize(450, 400);
        this.add(mainPanel);
        this.setJMenuBar(MyMenu.getMenuPanel(this));
        this.setVisible(true);
        this.setResizable(false);
        this.addWindowListener(listener);
    }

    public void closeFrame(){
        System.exit(0);
    }
    
    public static GameFrame getGameFrame(){
        if(gameFrame == null)
           gameFrame = new GameFrame();
           
        return gameFrame;
    }
    
    public MainPanel getMainPanel(){
        return mainPanel;
    }
    
    private class WindowListener extends WindowAdapter {
        
            @Override
            public void windowClosing(WindowEvent e)
            {
                if(Client.Client.getClient().onClose()){
                        closeFrame();
                }
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
