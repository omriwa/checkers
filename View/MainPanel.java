/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.User;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
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

    private MainPanel() {
        listener = new MainPanelLis();
        formPanel = new FormPanel();
        gamePanel = new GamePanel();

        //setup
        gamePanel.setVisible(false);
        formPanel.setVisible(true);
        this.add(formPanel);
        this.add(gamePanel);
        Dimension d = (this.getSize());
        formPanel.getFormBtn().addActionListener(listener);

    }

    public static MainPanel getMainPanel() {
        if (mainPanel == null) {
            return new MainPanel();
        } else {
            return mainPanel;
        }
    }

    public void setLoginPanel() {
        formPanel.setLoginForm();
        this.revalidate();
        this.repaint();
    }

    public void setRegisterPanel() {
        formPanel.setRegisterForm();
        this.revalidate();
        this.repaint();
    }

    public void setGamePanel() {
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

    private class MainPanelLis implements ActionListener, Serializable {

        @Override
        public void actionPerformed(ActionEvent e) {
            String btn = ((JButton) (e.getSource())).getText(),
                    username = formPanel.getUsername(),
                    password = formPanel.getPassword();

            if (btn.equalsIgnoreCase("Login")) {

                if (Client.Client.getClient().onLogOn(username, password)) {
                    System.out.println("user connected");
                } else {//incorrect input
                    formPanel.setHeadline("incorrect password or username");
                    formPanel.clearInputs();
                }

            } else {
                try {
                    if (Client.Client.getClient().getremoteServer().registerInServer(username, password)) {
                        System.out.println("user register");
                    } else {
                        formPanel.setHeadline("User Is Already Exists!");
                    }

                } catch (RemoteException ex) {
                    Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

    }
}
