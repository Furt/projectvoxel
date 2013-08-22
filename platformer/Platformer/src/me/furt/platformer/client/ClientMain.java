/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.furt.platformer.client;

import me.furt.platformer.client.*;
import com.jme3.app.SimpleApplication;
import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Network;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.furt.platformer.Globals;
import me.furt.platformer.PlatformerNetwork;
import me.furt.platformer.PlatformerNetwork.PlayerMessage;

/**
 * Platformer Server
 * @author furt
 */
public class ClientMain extends SimpleApplication {
    private Client client;
    private ConcurrentLinkedQueue<String> messageQueue;
    
    public static void main(String[] args) {
        PlatformerNetwork.initializeSerializables();
        ClientMain app = new ClientMain();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        try {
            client = Network.connectToServer(Globals.GAME_NAME, Globals.GAME_VERSION, Globals.SERVER_HOSTNAME, Globals.GAME_TCP_PORT, Globals.GAME_UCP_PORT);
            client.start();
        } catch (IOException ex) {
            Logger.getLogger(ClientMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        messageQueue = new ConcurrentLinkedQueue<String>();
        client.addMessageListener(new PlayerMessageListener());
    }
    
    @Override
    public void simpleUpdate(float tpf) {
        String message = messageQueue.poll();
        if(message != null) {
            
            fpsText.setText(message);
        }
        
    }
    
    public class PlayerMessageListener implements MessageListener<Client> {

        public void messageReceived(Client source, Message m) {
            if (m instanceof PlayerMessage) {
                PlayerMessage message = (PlayerMessage) m;
                messageQueue.add(message.getMessage());
            }
        }
        
    }
    
    @Override
    public void destroy() {
        client.close();
        super.destroy();
    }
}
