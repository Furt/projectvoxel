/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.furt.platformer.client;

import com.jme3.app.SimpleApplication;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Network;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.furt.platformer.Globals;
import me.furt.platformer.network.PlatformerNetwork;
import me.furt.platformer.network.PlatformerNetwork.ChatMessages;
import org.lwjgl.input.Keyboard;
import tonegod.gui.controls.extras.ChatBoxExt;
import tonegod.gui.core.Screen;

/**
 * Platformer Server
 *
 * @author furt
 */
public class ClientMain extends SimpleApplication {

    private Client client;
    private Screen screen;
    private ChatBoxExt chatbox;
    private ConcurrentLinkedQueue<String> chatMessageQueue;
    public String playerName;

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
        playerName = "player" + new Random().nextInt();
        screen = new Screen(this, "tonegod/gui/style/def/style_map.xml");
        
        chatbox = new ChatBoxExt(screen, new Vector2f(screen.getWidth()/2, screen.getHeight()/2)) {

            @Override
            public void onSendMsg(Object command, String msg) {
                client.send(new ChatMessages(command, playerName, msg));
            }
        };
        chatbox.addChatChannel("general", "General", "general", "", ColorRGBA.White, true);
        chatbox.setSendKey(Keyboard.KEY_RETURN);
        chatbox.showSendButton(false);
        
        screen.addElement(chatbox);
        guiNode.addControl(screen);
        chatMessageQueue = new ConcurrentLinkedQueue<String>();
        client.addMessageListener(new ClientMessageListener());
    }

    @Override
    public void simpleUpdate(float tpf) {
        String message = chatMessageQueue.poll();
        if (message != null) {
            String[] s = message.split(":");
            chatbox.receiveMsg(s[0], "<" + s[1] + "> " + s[2]);
        }
        
        screen.update(tpf);
    }

    public class ClientMessageListener implements MessageListener<Client> {

        public void messageReceived(Client source, Message m) {
            if (m instanceof ChatMessages) {
                ChatMessages message = (ChatMessages) m;
                chatMessageQueue.add(message.getMessage());
            }
            
        }
    }

    @Override
    public void destroy() {
        client.close();
        super.destroy();
    }
}
