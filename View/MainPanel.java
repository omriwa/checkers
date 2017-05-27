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
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;

/**
 *
 * @author omri
 */
public class MainPanel extends JPanel implements Serializable {

    private CardLayout cardLayout;
    private JPanel centerPanel;
    private OnlineUsersPanel ouPanel;
    private JLabel userMessage;
    private FormPanel formPanel;
    private GamePanel gamePanel;
    private MainPanelLis listener;
    private static MainPanel mainPanel = null;

    private MainPanel() {

        cardLayout = new CardLayout();//this layout manages the game & form panels
        centerPanel = new JPanel();
        listener = new MainPanelLis();
        formPanel = new FormPanel();
        gamePanel = GamePanel.getGamePlayPanel();
        ouPanel = new OnlineUsersPanel();
        //sets the layouts
        this.setLayout(new BorderLayout());
        centerPanel.setLayout(cardLayout);
        //sets the panels
        centerPanel.add(gamePanel, "gamePanel");
        centerPanel.add(formPanel, "formPanel");
        this.add(centerPanel, BorderLayout.CENTER);
        this.add(ouPanel, BorderLayout.EAST);
        //first presents only the login Panel 
        ouPanel.setVisible(false);
        cardLayout.show(centerPanel, "formPanel");
        formPanel.getFormBtn().addActionListener(listener);
        ouPanel.setPreferredSize(new Dimension(100, 400));

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
        formPanel.setVisible(false);
        gamePanel.setVisible(true);
        ouPanel.setVisible(true);
        this.invalidate();
        this.validate();
        this.repaint();
    }

    public void setFormPanel() {
        formPanel.setVisible(true);
        gamePanel.setVisible(false);
        ouPanel.setVisible(false);
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
                Client.Client.getClient().setConnectionAlive();
                if (Client.Client.getClient().onLogOn(username, password)) {
                    System.out.println("user connected");
                    cardLayout.show(centerPanel, "gamePanel");
                    ouPanel.setVisible(true);
                    MyMenu.getMenuPanel().disableInputItems();
                } else {//incorrect input
                    formPanel.setHeadline("incorrect password or username");
                }

            } else if (btn.equalsIgnoreCase("Register")) {
                User userInfo = formPanel.getUserInfo();
                System.out.println(formPanel.getUsername());
                if (userInfo != null) {
                    if (Client.Client.getClient().onRegister(userInfo, password) != null) {
                        System.out.println("user registered");
                        formPanel.fireLoginEvent();
                    } else {
                        formPanel.setHeadline("User Is Already Exists!");
                    }
                } else {
                    formPanel.setHeadline("Forgot to fill all fields");
                }

            }
            formPanel.clearInputs();
        }

    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }
}
