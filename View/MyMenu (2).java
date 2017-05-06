package View;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import CheckerServer.IRemoteServer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author omri
 */
public class MyMenu extends JMenuBar implements Serializable {

    private IRemoteServer manager = null;
    private JMenuItem logItem, surrenderItem , loginItem , registerItem;
    private Listener listener;

    public MyMenu(JFrame f) {
        listener = new Listener(f);
        JMenu menu = new JMenu("Menu");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription(
                "The only menu in this program that has menu items");
        this.add(menu);
        logItem = new JMenuItem("Get the log of the games");
        surrenderItem = new JMenuItem("Surrender");
        loginItem = new JMenuItem("Login");
        registerItem = new JMenuItem("Register");
        menu.add(logItem);
        menu.add(surrenderItem);
        menu.add(loginItem);
        menu.add(registerItem);
        logItem.addActionListener(listener);
        surrenderItem.addActionListener(listener);
        loginItem.addActionListener(listener);
        registerItem.addActionListener(listener);
    }

    public void setManager(IRemoteServer m) {
        manager = m;
    }

    private class Listener implements ActionListener, Serializable {

        private MainPanel mainPanel;
        private JFrame frame;
        
        public Listener(JFrame f){
            mainPanel = (MainPanel) f.getContentPane().getComponent(0);
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(logItem)) {
                
            } 
            else if(e.getSource().equals(surrenderItem)) {
                
            }
            else if(e.getSource().equals(loginItem)){
                mainPanel.setLoginPanel();
            }
            else{
                mainPanel.setRegisterPanel();
            }

        }

    }

    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setVisible(true);
        f.setSize(300, 300);
        f.setJMenuBar(new MyMenu(f));
    }
}
