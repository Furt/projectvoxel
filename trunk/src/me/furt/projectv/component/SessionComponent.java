/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.furt.projectv.component;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import com.simsilica.es.EntityComponent;
import com.simsilica.es.PersistentComponent;

/**
 *
 * @author Terry
 */
@Serializable
public class SessionComponent extends AbstractMessage implements EntityComponent, PersistentComponent {

    int sessionId;

    public SessionComponent(int session) {
        this.sessionId = session;
    }

    public int getSessionId() {
        return sessionId;
    }
}
