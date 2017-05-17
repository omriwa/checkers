/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;

/**
 *
 * @author omri
 */
public class UserInfo implements Serializable{
    
    private String pass , username , color , path;
    
    public UserInfo(String uname , String pas , String p , String c){
        username = uname;
        pass = pas;
        path = p;
        color = c;
    }
    
    public String getUsername(){
        return username;
    }

    public String getPass() {
        return pass;
    }

    public String getColor() {
        return color;
    }

    public String getPath() {
        return path;
    }
}
