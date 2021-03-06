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
import java.awt.Color;

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
    private JMenuItem logItem, surrenderItem, loginItem, registerItem, disconnectItem , gamesHistoryItem;
    private Listener listener;
    private static MyMenu myMenu = null;

    private MyMenu(JFrame f) {
        listener = new Listener(f);
        JMenu menu = new JMenu("Menu");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription(
                "The only menu in this program that has menu items");
        this.add(menu);
        //logItem = new JMenuItem("Get the log of the games");
        surrenderItem = new JMenuItem("Surrender");
        registerItem = new JMenuItem("Register");
        loginItem = new JMenuItem("Login");
        disconnectItem = new JMenuItem("Disconnect");
        gamesHistoryItem  = new JMenuItem("Games history");
        logItem = new JMenu();
//        menu.add(logItem);
//        menu.add(surrenderItem);
        menu.add(loginItem);
        menu.add(registerItem);
        menu.add(disconnectItem);
        menu.add(gamesHistoryItem);
        disconnectItem.addActionListener(listener);
        logItem.addActionListener(listener);
        surrenderItem.addActionListener(listener);
        loginItem.addActionListener(listener);
        registerItem.addActionListener(listener);
        gamesHistoryItem.addActionListener(listener);
        intialOptions();
        this.setBackground(new Color(237, 237, 237));
    }

    public static MyMenu getMenuPanel(JFrame f) {
        if (myMenu == null) {
            myMenu = new MyMenu(f);
        }
        return myMenu;
    }

    public static MyMenu getMenuPanel() {
        return myMenu;
    }

    private class Listener implements ActionListener, Serializable {

        private MainPanel mainPanel;
        private JFrame frame;

        public Listener(JFrame f) {
            mainPanel = (MainPanel) f.getContentPane().getComponent(0);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(logItem)) {

            } else if (e.getSource().equals(surrenderItem)) {

            } else if (e.getSource().equals(loginItem)) {
                mainPanel.setLoginPanel();
                MyMenu.getMenuPanel().enableInputItems();

            } else if (e.getSource().equals(registerItem)) {
                mainPanel.setRegisterPanel();
                MyMenu.getMenuPanel().enableInputItems();
            } else if (e.getSource().equals(disconnectItem)) {
                Client.Client.getClient().onDisconnect();
                mainPanel.setFormPanel();
                mainPanel.setLoginPanel();
                MyMenu.getMenuPanel().enableInputItems();
                MyMenu.getMenuPanel().setBackground(new Color(237, 237, 237));
                Client.Client.getClient().setUser(null);
            }
            else if(e.getSource().equals(gamesHistoryItem)){
            	mainPanel.setGamesHistoryPanel();
                MyMenu.getMenuPanel().disableInputItems();
            }

        }

    }

    private void intialOptions() {
        registerItem.setEnabled(true);
        loginItem.setEnabled(true);
        disconnectItem.setEnabled(false);
        logItem.setEnabled(false);
        surrenderItem.setEnabled(false);
        gamesHistoryItem.setEnabled(false);
    }

    public void disableInputItems() {
        registerItem.setEnabled(false);
        logItem.setEnabled(true);
        surrenderItem.setEnabled(true);
        loginItem.setEnabled(false);
        disconnectItem.setEnabled(true);
        gamesHistoryItem.setEnabled(true);

        this.repaint();
    }

    public void enableInputItems() {
        registerItem.setEnabled(true);
        logItem.setEnabled(false);
        surrenderItem.setEnabled(false);
        loginItem.setEnabled(true);
        disconnectItem.setEnabled(false);
        gamesHistoryItem.setEnabled(true);

        this.repaint();
    }

    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setVisible(true);
        f.setSize(300, 300);
        f.setJMenuBar(new MyMenu(f));
    }
}
