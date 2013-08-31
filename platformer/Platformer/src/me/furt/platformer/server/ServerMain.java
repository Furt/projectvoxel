/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.furt.platformer.server;

import com.jme3.app.SimpleApplication;
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.system.JmeContext;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.furt.platformer.Globals;
import me.furt.platformer.network.PlatformerNetwork;
import me.furt.platformer.network.PlatformerNetwork.ChatMessages;

/**
 * Platformer Server
 * @author furt
 */
public class ServerMain extends SimpleApplication {
    private Server server;
    private ConcurrentLinkedQueue<String> chatMessageQueue;
    
    public static void main(String[] args) {
        PlatformerNetwork.initializeSerializables();
        ServerMain app = new ServerMain();
        app.start(JmeContext.Type.Headless);
    }

    @Override
    public void simpleInitApp() {
        try {
            server = Network.createServer(Globals.GAME_NAME, Globals.GAME_VERSION, Globals.GAME_TCP_PORT, Globals.GAME_UCP_PORT);
            server.start();
        } catch (IOException ex) {
            Logger.getLogger(ServerMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        chatMessageQueue = new ConcurrentLinkedQueue<String>();
        server.addMessageListener(new ServerChatListener());
    }
    
    @Override
    public void simpleUpdate(float tpf) {
        String message = chatMessageQueue.poll();
        if (message != null) {
            // do logic here
            String[] s = message.split(":");
            server.broadcast(new ChatMessages(s[0], s[1], s[2]));
        }
        
        //server.getConnection(1).send(null);
    }
    
    public class ServerChatListener implements MessageListener<HostedConnection> {

        public void messageReceived(HostedConnection source, Message m) {
            if (m instanceof ChatMessages) {
                ChatMessages message = (ChatMessages) m;
                chatMessageQueue.add(message.getMessage());
            }
        }
    }
    
    @Override
    public void destroy() {
        server.close();
        super.destroy();
    }
}
