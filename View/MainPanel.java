/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import CheckerServer.CheckersServer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import CheckerServer.IRemoteServer;
import java.awt.Component;
import java.awt.Dimension;

/**
 *
 * @author omri
 */
public class MainPanel extends JPanel implements Serializable {
    
    private JLabel userMessage;
    private FormPanel formPanel;
    private GamePanel gamePanel;
    private MyMenu menu;
    private MainPanelLis listener;
    private static MainPanel mainPanel = null;
    
    private MainPanel(){
        listener = new MainPanelLis();
        formPanel = new FormPanel();
        gamePanel = new GamePanel();
        
        //setup
        gamePanel.setVisible(true);
        formPanel.setVisible(false);
        this.add(formPanel);
        this.add(gamePanel);
        Dimension d = (this.getSize());
        formPanel.getFormBtn().addActionListener(listener);
        
    }
    
    public static MainPanel getMainPanel(){
        if(mainPanel == null)
            return new MainPanel();
        else
            return mainPanel;
    }
    
    public void setLoginPanel(){
        formPanel.setLoginForm();
        this.revalidate();
        this.repaint();
    }
    
    public void setRegisterPanel(){
        formPanel.setRegisterForm();
        this.revalidate();
        this.repaint();
    }
    
    public void setGamePanel(){
//        Component[] components = this.getComponents();
//        for(Component component : components)
//            if(component == formPanel)
//                this.remove(component);
        formPanel.setVisible(false);
        gamePanel.setVisible(true);
        this.invalidate();
        this.validate();
        this.repaint();
    }
    
    private class MainPanelLis implements ActionListener ,Serializable {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            String btn =((JButton)(e.getSource())).getText(),
                   username = formPanel.getUsername() , 
                   password = formPanel.getPassword();
            
            if(btn.equalsIgnoreCase("Login")){
                try {
                    if(true){
                        MainPanel.getMainPanel().setGamePanel();
                        throw new RemoteException();
                    }
                    else{//incorrect input
                        formPanel.setHeadline("incorrect password or username");
                        formPanel.clearInputs();
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            else{
                try {
                    if(Client.Client.getClient().getremoteServer().registerInServer(username,password)){
                        
                    }
                    else{
                        formPanel.setHeadline("User Is Already Exists!");
                    }
                        
                } catch (RemoteException ex) {
                    Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }
        
    }
}
