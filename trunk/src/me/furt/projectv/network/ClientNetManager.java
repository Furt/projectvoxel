package me.furt.projectv.network;

import com.jme3.network.Client;
import com.jme3.network.ClientStateListener;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.furt.projectv.GameClient;
import me.furt.projectv.Globals;
import me.furt.projectv.network.messages.ChatMessage;
import me.furt.projectv.network.messages.ClientJoinMessage;
import me.furt.projectv.network.messages.HandshakeMessage;
import me.furt.projectv.network.messages.ServerJoinMessage;
import me.furt.projectv.network.messages.ServerTerrainMessage;

/**
 * Project V
 *
 * @author Furt
 */
public class ClientNetManager implements MessageListener, ClientStateListener {

    private GameClient app;
    private Client client;
    private String name = "";
    private String pass = "";

    public ClientNetManager(GameClient app, Client client) {
        this.app = app;
        this.client = client;
        client.addClientStateListener(this);
        client.addMessageListener(this, HandshakeMessage.class, ServerJoinMessage.class, ServerTerrainMessage.class, ChatMessage.class);
    }

    public void clientConnected(Client client) {
        setStatusText("Requesting login..");
        HandshakeMessage msg = new HandshakeMessage(Globals.PROTOCOL_VERSION, Globals.CLIENT_VERSION, -1);
        client.send(msg);
        Logger.getLogger(ClientNetManager.class.getName()).log(Level.INFO, "Sent handshake message");
    }

    public void clientDisconnected(Client c, DisconnectInfo info) {
        setStatusText("Server connection failed!");
    }

    public void messageReceived(Object source, Message message) {
        if (message instanceof HandshakeMessage) {
            HandshakeMessage msg = (HandshakeMessage) message;
            Logger.getLogger(ClientNetManager.class.getName()).log(Level.INFO, "Got handshake message back");
            if (msg.protocol_version != Globals.PROTOCOL_VERSION) {
                setStatusText("Protocol mismatch - update client!");
                Logger.getLogger(ClientNetManager.class.getName()).log(Level.INFO, "Client protocol mismatch, disconnecting");
                return;
            }
            client.send(new ClientJoinMessage(name, pass));

        } else if (message instanceof ServerJoinMessage) {
            final ServerJoinMessage msg = (ServerJoinMessage) message;
            if (!msg.rejected) {
                Logger.getLogger(ClientNetManager.class.getName()).log(Level.INFO, "Got login message back, we're in");
                setStatusText("Connected!");
                client.send(new ServerTerrainMessage());
                Logger.getLogger(ClientNetManager.class.getName()).log(Level.INFO, "Requested world data.");

            } else {
                Logger.getLogger(ClientNetManager.class.getName()).log(Level.INFO, "Server ditched us! Cant login.");
                setStatusText("Server rejected login!");
            }
        } else if (message instanceof ChatMessage) {
            final ChatMessage msg = (ChatMessage) message;
            app.addChat(msg.getPlayer() + ": " + msg.getMessage());
        }
    }

    private void setStatusText(String message) {
        app.setStatusText(message);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
