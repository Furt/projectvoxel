/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.furt.projectv.component;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Terry
 */
@Serializable
public class SessionComponent extends AbstractMessage {

    int sessionId;

    public SessionComponent(int session) {
        this.sessionId = session;
    }

    public int getSessionId() {
        return sessionId;
    }
}
