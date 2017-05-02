/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.User;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author omri
 */
public class OnlineUsersPanel extends JPanel {

    private ArrayList<JButton> onlineUserBtn;
    private static OnlineUsersPanel onlineUsersPanel = null;

    private OnlineUsersPanel() {
        onlineUserBtn = new ArrayList<>();
    }

    public static OnlineUsersPanel getOnlineUsersPanel() {
        if (onlineUsersPanel == null) {
            onlineUsersPanel = new OnlineUsersPanel();
        }
        return onlineUsersPanel;
    }

    /*get the online users from the server*/
    public void setOnlineUsers(ArrayList<User> onlineUsers) {
        onlineUserBtn.clear();
        if (onlineUsers != null) {
            for (User user : onlineUsers) {
                OnlineUsersPanel.getOnlineUsersPanel().getOnlineUsersBtn()
                        .add(new JButton(user.getUsername()));
            }
        }

        OnlineUsersPanel.getOnlineUsersPanel().repaint();
    }

    public void removeUser(User user) {
        onlineUserBtn.remove(user);
        this.invalidate();
        this.validate();
        this.repaint();
    }

    public ArrayList<JButton> getOnlineUsersBtn() {
        return onlineUserBtn;
    }

    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setVisible(true);
        f.setSize(300, 300);
        f.add(new OnlineUsersPanel());
    }

}
