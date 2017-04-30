/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Client.RemoteClient;
import java.io.Serializable;
import Client.IRemoteClient;

/**
 *
 * @author omri
 */
public class User implements Serializable{
    private String username;
    private String configPath;
    private String savedGamesDir;
    private IRemoteClient bridge;
    
    public User(){
        bridge = (IRemoteClient)new RemoteClient();
        
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
    
    
}
