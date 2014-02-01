/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.furt.projectv.core.network;

import com.jme3.network.Client;
import com.jme3.network.ClientStateListener;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.furt.projectv.client.GameClient;
import me.furt.projectv.core.Globals;
import me.furt.projectv.core.network.messages.ChatMessage;
import me.furt.projectv.core.network.messages.ClientJoinMessage;
import me.furt.projectv.core.network.messages.HandshakeMessage;
import me.furt.projectv.core.network.messages.ServerJoinMessage;

/**
 *
 * @author Terry
 */
public class ClientListener implements MessageListener, ClientStateListener {

    private GameClient app;
    private Client client;
    private String name = "";
    private String pass = "";

    public ClientListener(GameClient app, Client client) {
        this.app = app;
        this.client = client;
        client.addClientStateListener(this);
        client.addMessageListener(this, HandshakeMessage.class, ServerJoinMessage.class, ChatMessage.class);
    }

    public void clientConnected(Client clienst) {
        HandshakeMessage msg = new HandshakeMessage(Globals.PROTOCOL_VERSION, Globals.CLIENT_VERSION, -1);
        client.send(msg);
    }

    public void clientDisconnected(Client c, DisconnectInfo info) {
    }

    public void messageReceived(Object source, Message message) {
        if (message instanceof HandshakeMessage) {
            HandshakeMessage msg = (HandshakeMessage) message;
            Logger.getLogger(ClientListener.class.getName()).log(Level.INFO, "Got handshake message back");
            if (msg.protocol_version != Globals.PROTOCOL_VERSION) {
                setStatusText("Protocol mismatch - update client!");
                Logger.getLogger(ClientListener.class.getName()).log(Level.INFO, "Client protocol mismatch, disconnecting");
                return;
            }
            client.send(new ClientJoinMessage(name, pass));
        }
    }

    private void setStatusText(String message) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
