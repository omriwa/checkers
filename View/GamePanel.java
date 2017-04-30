/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.VesselListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author omri
 */
public class GamePanel extends JPanel {

    private JPanel buttonsComponent, onlineUsersPanel;
    private VesselListener listener;
    private MyButton[][] buttons;
    private final int ROW = 10, COL = 10;

    ;
    
    public GamePanel() {
        buttonsComponent = new JPanel();
        buttonsComponent.setLayout(new GridLayout(10, 10));
        buttons = new MyButton[ROW][COL];
        //onlineUsersPanel = (JPanel)(new OnlineUsersPanel(users));
        onlineUsersPanel = (JPanel) (new OnlineUsersPanel());
        //listener = new VesselListener(); fix

        //setup
        this.setLayout(new BorderLayout());
        this.add(onlineUsersPanel, BorderLayout.EAST);
        this.add(buttonsComponent, BorderLayout.CENTER);
        buttonSetUp();
        vesselSetUp(0, 4, true);//player1 set up vessels
        vesselSetUp(6, 10, false);//player1 set up vessels
    }

    /*board set up as black and white squares*/
    public void buttonSetUp() {
        boolean white = true;
        int count = 1;
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                buttons[i][j] = new MyButton();
                buttons[i][j].addActionListener(listener);
                buttonsComponent.add(buttons[i][j]);
                if ((i + j) % 2 == 0) {
                    buttons[i][j].setBackground(Color.white);
                } else {
                    buttons[i][j].setBackground(Color.black);
                }
            }

        }
    }

    /*set up the board from a new board*/
    public void setUpNewBoard(MyButton board[][]) {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                if (board[i][j].thereIsVessel()) {//move the vessel to this.board
                    buttons[i][j].setVessel(board[i][j].getVessel());
                } else {
                    buttons[i][j].setVessel(null);
                }
            }
        }
        this.repaint();
    }

    /*connect the vessel to buttons*/
    public void vesselSetUp(int startRow, int endRow, boolean player1) {
        for (; startRow < endRow; startRow++) {
            for (int j = 0; j < COL; j++) {
                if ((startRow + j) % 2 != 0) {
                    buttons[startRow][j].setVessel(new Vessel(player1));
                }
            }
        }
    }

}
