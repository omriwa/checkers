/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Client.RemoteClient;

import java.awt.*;
import java.io.Serializable;
import Client.IRemoteClient;

public class User implements Serializable {

    private String username;
    private String configPath;
    private IRemoteClient bridge;
    private Color color;

    public User() {
        bridge = (IRemoteClient) new RemoteClient();

    }

    public User(String username, String configPath, Color color) {
        this();
        this.username = username;
        this.configPath = configPath;
        this.color = color;
    }

    public String getConfigPath() {
        return configPath;
    }

    public void setConfigPath(String configPath) {
        this.configPath = configPath;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getUsername() {
        return username;
    }

    public IRemoteClient getBridge() {
        return bridge;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setBridge(IRemoteClient b) {
        bridge = b;
    }

    public static Color converStringToColor(String str){
    	if(str.equals("Yellow"))
    		return Color.yellow;
    	else if (str.equals("Blue"))
    		return Color.blue;
    	else if(str.equals("Green"))
    		return Color.green;
    	else 
    		return Color.black;
    		
    }
}
