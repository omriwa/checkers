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

public class User implements Serializable{
    private String username;
    private String configPath;
    private String savedGamesDir;
    private IRemoteClient bridge;
    private Color color;
    
    public User(){
        bridge = (IRemoteClient)new RemoteClient();
        
    }

    public String getConfigPath() {
        return configPath;
    }

    public void setConfigPath(String configPath) {
        this.configPath = configPath;
    }

    public String getSavedGamesDir() {
        return savedGamesDir;
    }

    public void setSavedGamesDir(String savedGamesDir) {
        this.savedGamesDir = savedGamesDir;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public User(String username , String configPath , String saveGameDir){
        this();
        this.username = username;
        this.configPath = configPath;
        this.savedGamesDir = saveGameDir;
    }
    
    public String getUsername(){
        return username;
    }
    
    public IRemoteClient getBridge(){
        return bridge;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setBridge(IRemoteClient b){
        bridge = b;
    }
    
}
