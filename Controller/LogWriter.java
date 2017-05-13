package Controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LogWriter {

    private String fileUrl = "C:\\Users\\omri\\Desktop";
    private String fileName = "\\gameLog.xml";
    private static LogWriter logWriter = null;
    private static int numOfGames = 0;

    private LogWriter() {
        logWriter = this;
    }

    public void createNewGameLog() {
        numOfGames++;
        try {
            File file = new File(fileUrl + fileName);
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements
            Document doc = null;
            if (numOfGames == 1) {
                doc = docBuilder.newDocument();
                doc.appendChild(doc.createElement("games"));
            } else {
                doc = docBuilder.parse(file);

            }

            doc.getElementsByTagName("games").item(0).appendChild(createDomLog(doc));

            saveFileChanges(doc);

        } catch (Exception e) {
            System.out.println("unable to create logfile");
        }
    }

    private Element createDomLog(Document doc) {
        Element game = doc.createElement("game" + numOfGames);
        //time Element
        Element currentTime = doc.createElement("startTime");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        currentTime.appendChild(doc.createTextNode(dateFormat.format(date)));//set the startTime to current time
        game.appendChild(currentTime);

        for (int numOfPlayer = 1; numOfPlayer <= 2; numOfPlayer++) {//player score
            Element player = doc.createElement("player" + numOfPlayer);
            Element score = doc.createElement("score");
            score.appendChild(doc.createTextNode("0"));
            player.appendChild(score);
            game.appendChild(player);
        }

        return game;
    }

    public static LogWriter getLogWriter() {
        if (logWriter == null) {
            return new LogWriter();
        } else {
            return logWriter;
        }
    }

    public void addEndGameTime() {

        try {
            File file = new File(fileUrl + fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            NodeList game = doc.getElementsByTagName("game" + numOfGames);
            Element endGame = doc.createElement("endGame");// root elements
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            endGame.appendChild(doc.createTextNode(dateFormat.format(date)));
            game.item(0).appendChild(endGame);

            saveFileChanges(doc);
        } catch (Exception e) {
            System.out.println("error in adding the end of the game");
        }
    }

    private void saveFileChanges(Document doc) {
        try {
            //save the changes
            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(fileUrl + fileName));

            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);
            transformer.transform(source, result);

            System.out.println("Changes saved!");
        } catch (Exception e) {
            System.out.println("cant save file");
        }
    }

    public void setScores(boolean player1Won) {
        String p1Score = "0", p2Score = "0";

        if (player1Won) {
            p1Score = "1";
        } else {
            p2Score = "1";
        }

        try {
            File file = new File(fileUrl + fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            NodeList scores = doc.getElementsByTagName("score");
            scores.item(scores.getLength() - 2).setTextContent(p1Score);//setting player 1 score
            scores.item(scores.getLength() - 1).setTextContent(p2Score);//setting player 2 score
            saveFileChanges(doc);
        } catch (Exception e) {
            System.out.println("unable to set scores");
        }

    }

    public String getLog() {
        String output = "";

        try {
            File file = new File(fileUrl + fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            NodeList games = doc.getDocumentElement().getChildNodes();
            String space = "\n";
            int p1Score = 0, p2Score = 0;

            output += "game log!" + space;
            output += "number of games: " + games.getLength() + space;
            output += "start of games: " + games.item(0).getChildNodes().item(0).getTextContent() + space;
            output += "end of games: " + games.item(games.getLength() - 1).getChildNodes().item(0).getTextContent() + space;

            for (int i = 0; i < games.getLength(); i++) {//getting scores
                //get score for player1
                String temp = games.item(i).getChildNodes().item(1).getChildNodes().item(0).getTextContent();
                p1Score += Integer.parseInt(temp);
                //get score for player2
                temp = games.item(i).getChildNodes().item(2).getChildNodes().item(0).getTextContent();
                p2Score += Integer.parseInt(temp);
            }

            output += "player1 score: " + p1Score + space + "player2 score: " + p2Score;
        } catch (Exception e) {
            output = "cant get log";
        }

        return output;
    }

//    public static void main(String [] args){
//        LogWriter.getLogWriter();
//        LogWriter.getLogWriter().addEndGameTime();
//        LogWriter.getLogWriter().setScores(true);
//        LogWriter.getLogWriter().createNewGameLog();
//        LogWriter.getLogWriter().addEndGameTime();
//        LogWriter.getLogWriter().setScores(true);
//        System.out.println(LogWriter.getLogWriter().getLog());
//        
//    }
}
