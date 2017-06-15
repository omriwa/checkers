package View;

import java.awt.BorderLayout;
import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import Database.DatabaseManager;

 
public class GamesHistoryPanel extends JPanel implements Serializable{
	
	protected JTable table;
	protected JScrollPane scrollPane;
	protected JButton goBackBtn;
	private String[][] data;
	private final String[] columnNames = {"player1",
            "player2",
            "winner",
            "start time",
            "finish time"};
	private static GamesHistoryPanel gamesHistoryPanel = null;
	
	private GamesHistoryPanel(){
		this.setLayout(new BorderLayout());
		goBackBtn = new JButton("go back");
		setLayout(new BorderLayout());
		this.add(goBackBtn,BorderLayout.SOUTH);
	}
        
        public static GamesHistoryPanel getGameHistoryPanel(){
            if(gamesHistoryPanel == null)
                gamesHistoryPanel = new GamesHistoryPanel();
            return gamesHistoryPanel;
        }
	
	public JButton getGoBackBtn(){
		return goBackBtn;
	}
	//updating the data in the games history table of the current user
	public void updateData(){
		data = DatabaseManager.getDatabaseManager().retrieveGamesHistoryData(Client.Client.getClient().getUser());
		table = new JTable(data,columnNames);
		scrollPane = new JScrollPane(table);
		this.add(scrollPane,BorderLayout.CENTER);		
	}
	
        public void setData(String [][] data){
            this.data = data;
        }
        

}
