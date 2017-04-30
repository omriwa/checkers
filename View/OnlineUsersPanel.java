/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.User;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author omri
 */
public class OnlineUsersPanel extends JPanel{
    
    private ArrayList<JButton> users;
    
    public OnlineUsersPanel(ArrayList<User> users){
        this.users = new ArrayList<>();
        if(users != null)
            for(User user : users){
                this.users.add(new JButton(user.getUsername()));
            }
    }
    
    public OnlineUsersPanel(){
                    this.add(new JLabel("aaaaaaa"));

    }
    
    public void removeUser(User user){
        users.remove(user);
        this.invalidate();
        this.validate();
        this.repaint();
    }
    
    public static void main(String [] args){
        JFrame f = new JFrame();
        f.setVisible(true);
        f.setSize(300, 300);
        f.add(new OnlineUsersPanel(null));
    }
    
}
