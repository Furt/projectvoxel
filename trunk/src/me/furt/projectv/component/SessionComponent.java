/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.furt.projectv.component;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import com.simsilica.es.PersistentComponent;

/**
 *
 * @author Terry
 */
@Serializable
public class SessionComponent extends AbstractMessage implements PersistentComponent {
    int sessionId;
    String playerName;
    
    public SessionComponent(int session, String player) {
        this.sessionId = session;
        this.playerName = player;
    }
    
    public int getSessionId() {
        return sessionId;
    }
    
    public String getPlayerName() {
        return playerName;
    }
}
