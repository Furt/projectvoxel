package me.furt.projectv.network;

import com.jme3.network.ConnectionListener;
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Server;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.furt.projectv.GameServer;
import me.furt.projectv.Globals;
import me.furt.projectv.network.messages.*;

/**
 * Project V
 *
 * @author Furt
 */
public class ServerNetManager implements MessageListener<HostedConnection>, ConnectionListener {

    GameServer app;
    Server server;
    byte[] worldData;

    public ServerNetManager(GameServer app, Server server) {
        this.app = app;
        this.server = server;
        this.app.getWorldData();
        server.addConnectionListener(this);
        server.addMessageListener(this, HandshakeMessage.class, LoginMessage.class, ServerTerrainMessage.class, ChatMessage.class);
    }

    public void messageReceived(HostedConnection source, Message message) {
        if (message instanceof HandshakeMessage) {
            HandshakeMessage msg = (HandshakeMessage) message;
            Logger.getLogger(ServerNetManager.class.getName()).log(Level.INFO, "Got handshake message");
            if (msg.protocol_version != Globals.PROTOCOL_VERSION) {
                source.close("Connection Protocol Mismatch - Update Client");
                Logger.getLogger(ServerNetManager.class.getName()).log(Level.INFO, "Client protocol mismatch, disconnecting");
                return;
            }
            msg.server_version = Globals.SERVER_VERSION;
            source.send(msg);
            Logger.getLogger(ServerNetManager.class.getName()).log(Level.INFO, "Sent back handshake message");
        } else if (message instanceof ClientJoinMessage) {
            final ClientJoinMessage msg = (ClientJoinMessage) message;
            Logger.getLogger(ServerNetManager.class.getName()).log(Level.INFO, "Got client join message");
            final int clientId = (int) source.getId();
            //TODO: login user/pass check
            ServerJoinMessage sjm = new ServerJoinMessage(source.getId(), 0, msg.name, false);
            source.send(sjm);
        } else if (message instanceof ServerTerrainMessage) {
            final ServerTerrainMessage serverTerrain = new ServerTerrainMessage(worldData);
            source.send(serverTerrain);
        }
    }

    public void connectionAdded(Server server, HostedConnection conn) {
        // TODO check if client id exists
    }

    public void connectionRemoved(Server server, HostedConnection conn) {
        // TODO remove player from list
    }
}
