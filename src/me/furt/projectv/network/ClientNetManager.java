/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.furt.projectv.network;

import com.jme3.network.Client;
import com.jme3.network.ClientStateListener;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;

/**
 *
 * @author Terry
 */
public class ClientNetManager implements MessageListener, ClientStateListener {

    public void messageReceived(Object source, Message m) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void clientConnected(Client c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void clientDisconnected(Client c, DisconnectInfo info) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
