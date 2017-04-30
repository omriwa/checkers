package Database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author omri
 */
public class DatabaseManager {
    
    private Connection connection = null;
    private Statement state = null;
    private static int gameNum = 1;
    private static String url = "jdbc:mysql://localhost:3306/checkers_database" , username = "root" , password = "root";
    
    public DatabaseManager(){
        try {
		connection = DriverManager.getConnection(url , username, password);

	} catch (SQLException e) {
		System.out.println("Connection Failed! Check output console");
		e.printStackTrace();
		return;
	}

	if (connection != null) {
		System.out.println("connected to the database");
	} else {
		System.out.println("Failed to make connection!");
	}
    }
    
    public void disconnectDatabase(){
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void addGameInfo(){
        try {
            state = connection.createStatement();
            String values = "'";
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            values += dateFormat.format(Calendar.getInstance().getTime());
            values += "'";
            System.out.println(values);
            String query = "INSERT INTO games_log VALUES(" + values + ")";
            state.executeUpdate(query);
            gameNum++;
        } catch (SQLException ex) {
            System.out.println("can't add data to database");
            System.out.println(ex);
            return;
        }
        System.out.println("add info seccessfuly");
    }
    
    public boolean checkUserLogin(String username , String password){
        try {
            state = connection.createStatement();
            String query = "SELECT username FROM users WHERE username=" 
                   + "'" + username + "'" 
                   + " AND password=" + "'" + password + "'";
            ResultSet rs = state.executeQuery(query);
            if(rs.first())//there is such username with this password
                return true;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean registerUser(String username , String password){
           try {
                state = connection.createStatement();
                String query = "SELECT username FROM users WHERE username=" 
                       + "'" + username + "'";
                ResultSet rs = state.executeQuery(query);
                if(rs.first())//there is such username with this password
                    return false;
                query = "INSERT INTO users VALUES(" + "'" + username + "'" 
                   + "," + "'" + password + "')";
                state.execute(query);
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
            } 
        return false;
    }
    
    public static void main(String[] argv) {
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.addGameInfo();
        databaseManager.disconnectDatabase();
    }
    
}
