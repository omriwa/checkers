package Database;

import Model.User;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;


public class UserConfiguration {

    private final static String
            CONFIGURATION = "Configuration",
            USER_NAME = "username",
            ID = "id",
            COLOR = "color",
            SAVED_GAME_DIR = "savedGame";


    public static void saveUserConfig(User user){
        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement(CONFIGURATION);
            doc.appendChild(rootElement);

            // staff elements
            Element username = doc.createElement(USER_NAME);
            rootElement.appendChild(username);

            // set attribute to staff element
            Attr attr = doc.createAttribute(ID);
            attr.setValue(user.getUsername());
            username.setAttributeNode(attr);

            // color elements
            Element color = doc.createElement(COLOR);
            color.appendChild(doc.createTextNode(user.getColor().toString()));
            rootElement.appendChild(color);

            // color elements
            Element savedGame = doc.createElement(SAVED_GAME_DIR);
            color.appendChild(doc.createTextNode(user.getSavedGamesDir()));
            rootElement.appendChild(savedGame);

            //...

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(user.getConfigPath()));

            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);

            transformer.transform(source, result);

            System.out.println("File saved!");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }


        public static void loadUserConfig(User user){

        try {

            File fXmlFile = new File(user.getConfigPath());
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());


            Node node = doc.getElementsByTagName(CONFIGURATION).item(0);

            if (node.getNodeType() == Node.ELEMENT_NODE) {

                Element eElement = (Element) node;

                user.setUsername(((Element) eElement.getElementsByTagName(USER_NAME).item(0)).getAttribute("id"));
                user.setColor(java.awt.Color.getColor(eElement.getElementsByTagName(COLOR).item(0).getTextContent()));
                user.setSavedGamesDir(eElement.getElementsByTagName(SAVED_GAME_DIR).item(0).getTextContent());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String [] args){
            User user = new User();
            user.setSavedGamesDir("gg");
            user.setColor(java.awt.Color.BLUE);
            user.setUsername("aa");
            user.setConfigPath("c:\\Users\\omri\\Desktop\\a.xml");
            saveUserConfig(user);

            User user2 = new User();
            user2.setConfigPath("c:\\Users\\omri\\Desktop\\a.xml");
            loadUserConfig(user2);
    }
}
