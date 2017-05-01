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
    private MyButton[][] board;
    private static GamePanel gamePanel = null;
    private final int ROW = 10, COL = 10;

    private GamePanel() {
        buttonsComponent = new JPanel();
        buttonsComponent.setLayout(new GridLayout(10, 10));
        board = new MyButton[ROW][COL];
        //onlineUsersPanel = (JPanel)(new OnlineUsersPanel(users));
        onlineUsersPanel = (JPanel) (new OnlineUsersPanel());
        listener = new VesselListener(board);

        //setup
        this.setLayout(new BorderLayout());
        this.add(onlineUsersPanel, BorderLayout.EAST);
        this.add(buttonsComponent, BorderLayout.CENTER);
        buttonSetUp();
        vesselSetUp(0, 4, true);//player1 set up vessels
        vesselSetUp(6, 10, false);//player1 set up vessels
    }

    public static GamePanel getGamePlayPanel() {
        if (gamePanel == null) {
            gamePanel = new GamePanel();
        }
        return gamePanel;
    }

    /*board set up as black and white squares*/
    public void buttonSetUp() {
        boolean white = true;
        int count = 1;
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                board[i][j] = new MyButton();
                board[i][j].addActionListener(listener);
                buttonsComponent.add(board[i][j]);
                if ((i + j) % 2 == 0) {
                    board[i][j].setBackground(Color.white);
                } else {
                    board[i][j].setBackground(Color.black);
                }
            }

        }
    }

    /*set up the board from a new board*/
    public void setUpNewBoard(MyButton board[][]) {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                if (board[i][j].thereIsVessel()) {//move the vessel to this.board
                    board[i][j].setVessel(board[i][j].getVessel());
                } else {
                    board[i][j].setVessel(null);
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
                    board[startRow][j].setVessel(new Vessel(player1));
                }
            }
        }
    }
    
    public MyButton [][] getBoard(){
        return board;
    }

}
