/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.User;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
        System.out.println(onlineUsers.get(0));
        usersName = onlineUsers;
        onlineUsersList = new JList(usersName.toArray());
        onlineUsersList.addListSelectionListener(new Listener());
        this.remove(listScroller);
        listScroller = new JScrollPane(onlineUsersList);
        this.add(listScroller, BorderLayout.CENTER);
        this.refreshPanel();
        GamePanel.getGamePlayPanel().invalidate();
        GamePanel.getGamePlayPanel().validate();
        //fix that the new users will appear, use usersName
    }

    public void removeUser(User user) {
        usersName.remove(user.getUsername());
        this.refreshPanel();
    }

    public ArrayList<String> getOnlineUsersName() {
        return usersName;
    }

    private void refreshPanel() {
        this.invalidate();
        this.validate();
    }

    private class Listener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {//This line prevents double events
                String opponent = onlineUsersList.getSelectedValue().toString();
                if(!opponent.isEmpty())//choosed an opponent
                    Client.Client.getClient().sendInvitation(opponent);
            }

        }

    }

    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setVisible(true);
        f.setSize(300, 300);
        OnlineUsersPanel p = new OnlineUsersPanel();
        f.add(p);
        f.invalidate();
        f.validate();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ArrayList<String> arr = new ArrayList<>();
        arr.add("aaa");
        arr.add("bbb");
        arr.add("ccc");
        p.setOnlineUsers(arr);
    }

}
