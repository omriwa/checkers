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
public class GameInvitation implements Serializable{
    
    private String userId1 , userId2;
    private boolean accept = false;
    
    
    public GameInvitation(String u1 , String u2){
        userId1 = u1;
        userId2 = u2;
    }

    public void setUserId1(String userId1) {
        this.userId1 = userId1;
    }

    public void setUserId2(String userId2) {
        this.userId2 = userId2;
    }

    public void setAccept(boolean accept) {
        this.accept = accept;
    }

    public String getUserId1() {
        return userId1;
    }

    public String getUserId2() {
        return userId2;
    }

    public boolean isAccept() {
        return accept;
    }
    
}
