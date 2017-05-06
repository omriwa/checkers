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
public class FormPanel  extends JPanel implements Serializable{
    
    protected JLabel headline , passLbl , userLbl;
    protected JTextField usernameTxt , passwordTxt;
    protected JPanel centerPanel;
    protected JButton registerBtn , sendFormBtn;
    
    public FormPanel(){
        this.setLayout(new BorderLayout());
        headline = new JLabel("Please Login");
        userLbl = new JLabel("username:");
        passLbl = new JLabel("password:");
        usernameTxt = new JTextField(5);
        passwordTxt = new JTextField(5);
        centerPanel = new JPanel();
        sendFormBtn = new JButton("Login");
        
        //setup
        this.add(centerPanel , BorderLayout.CENTER);
        this.add(headline , BorderLayout.NORTH);
        centerPanel.add(userLbl);
        centerPanel.add(usernameTxt);
        centerPanel.add(passLbl);
        centerPanel.add(passwordTxt);
        centerPanel.add(sendFormBtn);
    }
    
    public String getUsername(){
        return usernameTxt.getText();
    }
    
    public String getPassword(){
        return passwordTxt.getText();
    }
    
    public JButton getFormBtn(){
        return sendFormBtn;
    }
    
    public void setRegisterForm(){
        sendFormBtn.setText("Register");
        headline.setText("Please Register");
    }
    
    public void setLoginForm(){
        sendFormBtn.setText("Login");
        headline.setText("Please Login");
    }
    
    public void setHeadline(String s){
        headline.setText(s);
        this.repaint();
    }
    
    public void clearInputs(){
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
