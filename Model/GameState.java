/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class GameState implements Serializable{
    
    private View.MyButton [][] gameVessels = null;
    private boolean player1Turn = true;
    private String userID1 , userID2;
    private boolean enable = false;
    private String startTime = null;
    private String winner = null;
    private String endTime = null;
    
    public GameState(String u1 , String u2){
        userID1 = u1;
        userID2 = u2;
        startTime = getCurTime();
    }

    public String getOtherUser(String user){
        if (user == userID1)
            return userID2;
        else
            return userID1;
    }

    public static void saveGame(String path, GameState game){
        ObjectOutputStream oos = null;
        FileOutputStream fout = null;
        try{
            fout = new FileOutputStream(path, true);
            oos = new ObjectOutputStream(fout);
            oos.writeObject(game);
            oos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static GameState loadGame(String path){

        GameState game = null;

        FileInputStream fin = null;
        ObjectInputStream ois = null;

        try {

            fin = new FileInputStream(path);
            ois = new ObjectInputStream(fin);
            game = (GameState) ois.readObject();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {

            if (fin != null) {
                try {
                    fin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return game;

    }

    public View.MyButton[][] getState(){
        return gameVessels;
    }
    
    public void changeTurn(){
        player1Turn = !player1Turn;
    }
    
    public boolean isPlayer1Turn(){
        return player1Turn;
    }
    
    public void setState(View.MyButton[][] vessels){
        gameVessels = vessels;
    }
    public String getUserId1(){
        return userID1;
    }
    public String getUserId2(){
        return userID2;
    }
    public void enablePlaying(){
        enable = true;
    }
    public boolean canPlay(){
        return enable;
    }
    public String getStartTime(){
        return startTime;
    }
    public String getWinner(){
        return winner;
    }
    public void setWinner(String w){
        winner = w;
    }
    public void setEndTime(){
        endTime = getCurTime();
    }
    public String getEndTime(){
        return endTime;
    }
    public void disabledGame(){
        enable = false;
    }
    
    private String getCurTime(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return dateFormat.format(Calendar.getInstance().getTime());
    }
    
    
}
