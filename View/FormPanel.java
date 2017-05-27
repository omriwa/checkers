/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import javax.swing.*;
import Model.User;

/**
 *
 * @author omri
 */
public class FormPanel extends JPanel implements Serializable {

    protected JLabel headline, passLbl, userLbl, colorLbl;
    protected JTextField usernameTxt, passwordTxt;
    protected JPanel centerPanel;
    protected JButton registerBtn, sendFormBtn, directoryBtn;
    protected JFileChooser directoryChoser;
    protected JComboBox<String> colors;
    protected String path = null;
    private final String[] colorOptions = {"Black","Green","Yellow","Blue"};
    private FormPanel formPanel = this;

    public FormPanel() {
        this.setLayout(new BorderLayout());
        colors = new JComboBox<String>(colorOptions);
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
        directoryBtn.addActionListener(new FormListener());
    }

    private class FormListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(formPanel,"choose a directory,for the xml file");
            JFileChooser pathChooser = new JFileChooser();
            pathChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int result = pathChooser.showOpenDialog(pathChooser);
            if (result == JFileChooser.APPROVE_OPTION) {
               path = pathChooser.getSelectedFile().getAbsolutePath() + "\\config.xml";
            }
            System.out.println(path);
        }

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
    
    public User getUserInfo(){
        if(usernameTxt.getText().length() > 0 && passwordTxt.getText().length() > 0 && path != null){
            Color color = User.converStringToColor(colors.getSelectedItem().toString()); 
            return new User(getUsername(), path , color);
        }
        else
            return null;
    }
    
    public void fireLoginEvent(){
        setLoginForm();
        sendFormBtn.doClick();
    }

    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setSize(300, 300);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new FormPanel());
        f.setVisible(true);
    }
}
