/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.furt.projectv.network;

import com.jme3.network.ConnectionListener;
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Server;
import me.furt.projectv.GameServer;
import me.furt.projectv.network.messages.*;

/**
 *
 * @author Terry
 */
public class ServerNetManager implements MessageListener<HostedConnection>, ConnectionListener {
    GameServer app;
    Server server;
    
    public ServerNetManager(GameServer app, Server server) {
        this.app = app;
        this.server = server;
        server.addConnectionListener(this);
        server.addMessageListener(this, HandshakeMessage.class, LoginMessage.class, ChatMessage.class);
    }
    
    public void messageReceived(HostedConnection source, Message m) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void connectionAdded(Server server, HostedConnection conn) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void connectionRemoved(Server server, HostedConnection conn) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
