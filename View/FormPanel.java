/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.BorderLayout;
import java.io.Serializable;
import javax.swing.*;

/**
 *
 * @author omri
 */
public class FormPanel extends JPanel implements Serializable {

    protected JLabel headline, passLbl, userLbl, colorLbl;
    protected JTextField usernameTxt, passwordTxt, colorTxt;
    protected JPanel centerPanel;
    protected JButton registerBtn, sendFormBtn , directoryBtn;
    protected JFileChooser directoryChoser;
    protected JComboBox colors;
    private final String [] colorOptions = {"Black" , "White" , "Blue" , "Red"};
    
    public FormPanel() {
        this.setLayout(new BorderLayout());
        colors = new JComboBox(colorOptions);
        directoryChoser = new JFileChooser();
        directoryBtn = new JButton("choose directory");
        headline = new JLabel("Please Login");
        userLbl = new JLabel("username:");
        passLbl = new JLabel("password:");
        usernameTxt = new JTextField(5);
        passwordTxt = new JTextField(5);
        centerPanel = new JPanel();
        sendFormBtn = new JButton("Login");

        //setup
        this.add(centerPanel, BorderLayout.CENTER);
        this.add(headline, BorderLayout.NORTH);
        centerPanel.add(userLbl);
        centerPanel.add(usernameTxt);
        centerPanel.add(passLbl);
        centerPanel.add(passwordTxt);
        centerPanel.add(colors);
        centerPanel.add(directoryBtn);
        centerPanel.add(sendFormBtn);
        setLoginForm();
    }

    public String getUsername() {
        return usernameTxt.getText();
    }

    public String getPassword() {
        return passwordTxt.getText();
    }

    public JButton getFormBtn() {
        return sendFormBtn;
    }

    public void setRegisterForm() {
        sendFormBtn.setText("Register");
        headline.setText("Please Register");
        colors.setVisible(true);
        directoryBtn.setVisible(true);
    }

    public void setLoginForm() {
        sendFormBtn.setText("Login");
        headline.setText("Please Login");
        colors.setVisible(false);
        directoryBtn.setVisible(false);
    }

    public void setHeadline(String s) {
        headline.setText(s);
        this.repaint();
    }

    public void clearInputs() {
        usernameTxt.setText("");
        passwordTxt.setText("");
    }

    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setSize(300, 300);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new FormPanel());
        f.setVisible(true);
    }
}
