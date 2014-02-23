package me.furt.projectv.core.network;

import me.furt.projectv.core.network.messages.LoginMessage;
import me.furt.projectv.core.network.messages.ChatMessage;
import me.furt.projectv.core.network.messages.HandshakeMessage;
import com.jme3.network.ConnectionListener;
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Server;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.furt.projectv.server.GameServer;
import me.furt.projectv.core.Globals;

/**
 *
 * @author Terry
 */
public class ServerListener implements MessageListener<HostedConnection>, ConnectionListener {

    GameServer app;
    Server server;

    public ServerListener(GameServer app, Server server) {
        this.app = app;
        this.server = server;
        server.addConnectionListener(this);
        server.addMessageListener(this, HandshakeMessage.class, LoginMessage.class, ChatMessage.class);
    }

    public void messageReceived(HostedConnection source, Message message) {
        if (message instanceof HandshakeMessage) {
            HandshakeMessage msg = (HandshakeMessage) message;
            Logger.getLogger(ServerListener.class.getName()).log(Level.INFO, "Got handshake message");
            if (msg.protocol_version != Globals.PROTOCOL_VERSION) {
                source.close("Connection Protocol Mismatch - Update Client");
                Logger.getLogger(ServerListener.class.getName()).log(Level.INFO, "Client protocol mismatch, disconnecting");
                return;
            }
            msg.server_version = Globals.SERVER_VERSION;
            source.send(msg);
            Logger.getLogger(ServerListener.class.getName()).log(Level.INFO, "Sent back handshake message");
        }
    }

    public void connectionAdded(Server server, HostedConnection conn) {
    }

    public void connectionRemoved(Server server, HostedConnection conn) {
    }
}
