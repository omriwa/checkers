/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.User;
import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author omri
 */
public class OnlineUsersPanel extends JPanel {

    private static OnlineUsersPanel onlineUsersPanel = null;
    private JList onlineUsersList = null;
    private ArrayList<String> usersName = null;
    private JScrollPane listScroller = null;

    OnlineUsersPanel() {
        this.setSize(50, 400);
        this.setLayout(new BorderLayout());
        usersName = new ArrayList<>();
        onlineUsersList = new JList(usersName.toArray());
        listScroller = new JScrollPane(onlineUsersList);
        this.add(new JLabel("Online Users"), BorderLayout.NORTH);
        this.add(listScroller, BorderLayout.CENTER);
    }

    public static OnlineUsersPanel getOnlineUsersPanel() {
        if (onlineUsersPanel == null) {
            onlineUsersPanel = new OnlineUsersPanel();
        }
        return onlineUsersPanel;
    }

    /*get the online users from the server*/
    public void setOnlineUsers(ArrayList<String> onlineUsers) {
        usersName.clear();
        if (onlineUsers != null) {
            for (String uName : onlineUsers) {
                usersName.add(uName);
            }
        }

        //fix that the new users will appear, use usersName
    }

    public void removeUser(User user) {
        usersName.remove(user.getUsername());
        this.invalidate();
        this.validate();
        this.repaint();
    }

    public ArrayList<String> getOnlineUsersName() {
        return usersName;
    }

    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setVisible(true);
        f.setSize(300, 300);
        f.add(new OnlineUsersPanel());
    }

}
