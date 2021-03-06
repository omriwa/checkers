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
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GamePanel extends JPanel {

    private JPanel buttonsComponent;
    private JLabel userTurnLbl;
    private String userTurnStr;
    private VesselListener listener;
    private MyButton[][] board;//matrix of buttons
    private static GamePanel gamePanel = null;
    private final int ROW = 10, COL = 10;

    private GamePanel() {

        setLayout(new BorderLayout());
        buttonsComponent = new JPanel();
        buttonsComponent.setLayout(new GridLayout(10, 10));
        board = new MyButton[ROW][COL];
        listener = new VesselListener(board);
        userTurnLbl = new JLabel("");//check if the panel updates each turn
        //setup
        this.setLayout(new BorderLayout());
        this.add(userTurnLbl, BorderLayout.NORTH);
        buttonSetUp();
        vesselSetUp(0, 4, true);//player1 set up vessels
        vesselSetUp(6, 10, false);//player1 set up vessels
        this.add(buttonsComponent, BorderLayout.CENTER);

    }

    public static GamePanel getGamePlayPanel() {
        if (gamePanel == null) {
            gamePanel = new GamePanel();
        }
        return gamePanel;
    }

    //returns the name/userID of the current player's turn
    public void setUsersTurnName(boolean player1Turn) {
//        if (player1Turn && listener.getPlayer1Flag())//p1 and c2
//        {
//            userTurnLbl.setText(Client.Client.getClient().getGameState().getUserId1() + "'s turn");
//        }
//        if (!player1Turn && !listener.getPlayer1Flag())//p2 and c2
//        {
//            userTurnLbl.setText(Client.Client.getClient().getGameState().getUserId2() + "'s turn");
//        }
//        if (!player1Turn && listener.getPlayer1Flag())//p2 and c1
//        {
//            userTurnLbl.setText(Client.Client.getClient().getGameState().getUserId2() + "'s turn");
//        }
//        if (player1Turn && !listener.getPlayer1Flag())//p1 and c2
//        {
//            userTurnLbl.setText(Client.Client.getClient().getGameState().getUserId1() + "'s turn");
//        }

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
    public void setUpNewBoard(MyButton b[][]) {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                if (b[i][j].thereIsVessel()) {//move the vessel to this.board
                    board[i][j].setVessel(b[i][j].getVessel());
                } else {
                    board[i][j].setVessel(null);
                }
            }
        }
        System.out.println("got new board");
        this.invalidate();
        this.validate();
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

    public static MyButton[][] getNewBoard() {
        GamePanel gamePanel = new GamePanel();
        return gamePanel.getBoard();
    }

    public MyButton[][] getBoard() {
        return board;
    }

    public VesselListener getListener() {
        return listener;
    }

    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setVisible(true);
        f.setSize(500, 500);
        f.add(new GamePanel());
    }
}
