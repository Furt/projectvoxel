/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.furt.platformer.client;

import me.furt.platformer.client.*;
import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector4f;
import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Network;
import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.controls.Window;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.furt.platformer.Globals;
import me.furt.platformer.network.PlatformerNetwork;
import me.furt.platformer.network.PlatformerNetwork.ServerMessage;
import org.lwjgl.input.Keyboard;
import tonegod.gui.controls.extras.ChatBox;
import tonegod.gui.core.Element;
import tonegod.gui.core.Screen;

/**
 * Platformer Server
 *
 * @author furt
 */
public class ClientMain extends SimpleApplication {

    private Client client;
    private Screen screen;
    private ChatBox chatbox;
    private ConcurrentLinkedQueue<String> messageQueue;

    public static void main(String[] args) {
        PlatformerNetwork.initializeSerializables();
        ClientMain app = new ClientMain();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        screen = new Screen(this, "tonegod/gui/style/def/style_map.xml");
        chatbox = new ChatBox(screen, new Vector2f(screen.getWidth()/2, screen.getHeight()/2)) {

            @Override
            public void onSendMsg(String msg) {
            }
        };
        chatbox.setSendKey(Keyboard.KEY_RETURN);
        screen.addElement(chatbox);
        guiNode.addControl(screen);
        try {
            client = Network.connectToServer(Globals.GAME_NAME, Globals.GAME_VERSION, Globals.SERVER_HOSTNAME, Globals.GAME_TCP_PORT, Globals.GAME_UCP_PORT);
            client.start();
        } catch (IOException ex) {
            Logger.getLogger(ClientMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        messageQueue = new ConcurrentLinkedQueue<String>();
        client.addMessageListener(new ServerMessageListener());
    }

    @Override
    public void simpleUpdate(float tpf) {
        String message = messageQueue.poll();
        if (message != null) {
            // add messages to chat
        }
    }

    public class ServerMessageListener implements MessageListener<Client> {

        public void messageReceived(Client source, Message m) {
            if (m instanceof ServerMessage) {
                ServerMessage message = (ServerMessage) m;
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
