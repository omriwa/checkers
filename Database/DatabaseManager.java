package Database;

import Model.User;
import com.mysql.jdbc.DatabaseMetaData;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
    private static String url = "jdbc:mysql://localhost:3306/checkers_database", username = "root", password = "root";

    public DatabaseManager() {
        try {
            connection = DriverManager.getConnection(url, username, password);

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

    public void disconnectDatabase() {
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addGameInfo() {
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

    /*public User userExist(String username, String password) {
        try {
            state = connection.createStatement();
            String query = "SELECT username FROM users WHERE username="
                    + "'" + username + "'"
                    + " AND password=" + "'" + password + "'";
            ResultSet rs = state.executeQuery(query);
            if (rs.first())//there is such username with this password
            {
                return new User(rs.getString("username"), rs.getString("configPath"), "game_dir");
            }

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }*/

    public User getUserFromDB(String uname, String pass) {
        User res = null;
        try {
            state = connection.createStatement();
            String sql = "SELECT username, configPath FROM users WHERE username = " + "\"" + uname + "\"" + " AND password = " + "\"" + pass + "\"";
            ResultSet rs = state.executeQuery(sql);
            rs.next();

            res = new User(rs.getString("username"), rs.getString("configPath"), "game_dir");//need to fixs

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;
    }

    public boolean registerUser(String username, String password) {
        try {
            if (!checkIfUserExists(username)) {
                state = connection.createStatement();
                String query, dateStr;
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                dateStr = (dateFormat.format(date));
                query = "INSERT INTO users VALUES(" + "'" + username + "'"
                        + "," + "'" + password + "'" + "," + "'color'"
                        + "," + "'config'" + "," + "'" + dateStr + "'" + ")";
                state.execute(query);
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void createTables() {
        try {
            //if its the 1st registration, than create the "users" & "gameHistory" tables in DB
            DatabaseMetaData dbmd = (DatabaseMetaData) connection.getMetaData();
            ResultSet regTable = dbmd.getTables(null, null, "users", null);
            if (!regTable.next()) {

                String createUsersTableQuery = "CREATE TABLE Users "
                        + "(username VARCHAR(255) not NULL, "
                        + " password VARCHAR(255), "
                        + " color VARCHAR(255), "
                        + " configPath VARCHAR(255), "
                        + " lastOnline DATE, "
                        + " PRIMARY KEY ( username ))";

                String createGameHistoryTableQuery = "CREATE TABLE GamesHistory "
                        + "(player1 VARCHAR(255) not NULL, "
                        + " player2 VARCHAR(255) not NULL, "
                        + " winner VARCHAR(255), "
                        + " start VARCHAR(255), "
                        + " finish VARCHAR(255))";

                //create the users & gameHistory table
                state = connection.createStatement();
                state.executeUpdate(createUsersTableQuery);
                state = connection.createStatement();
                state.executeUpdate(createGameHistoryTableQuery);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private boolean checkIfUserExists(String username) {
        String query = "SELECT username FROM users WHERE username="
                + "'" + username + "'";
        try {
            state = connection.createStatement();
            ResultSet rs = state.executeQuery(query);
            if (!rs.first())//there is such username with this password
            {
                return false;
            }
        } catch (Exception e) {
        }
        return true;//in a case it will not create user
    }

    public static void main(String[] argv) {
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.addGameInfo();
        databaseManager.disconnectDatabase();
    }

}
