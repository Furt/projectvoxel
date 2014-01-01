/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.furt.projectv.state;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import org.lwjgl.input.Keyboard;
import tonegod.gui.controls.extras.ChatBoxExt;
import tonegod.gui.core.Screen;

/**
 *
 * @author Terry
 */
public class GuiAppState extends AbstractAppState {
    
    private Screen screen;
    private ChatBoxExt chatbox;
    private Node guiNode;
    private SimpleApplication app;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        this.app = (SimpleApplication) app;
        this.guiNode = this.app.getGuiNode();
        this.setupChatBox();
    }

    @Override
    public void update(float tpf) {
    }
    
    private void setupChatBox() {
        screen = new Screen(app, "tonegod/gui/style/def/style_map.xml");
        chatbox = new ChatBoxExt(screen, new Vector2f(30, 30)) {
            @Override
            public void onSendMsg(Object command, String msg) {
                chatbox.receiveMsg(command, "<player> " + msg);
            }
        };
        chatbox.setIsMovable(true);
        chatbox.addChatChannel("general", "General", "general", "", ColorRGBA.White, true);
        chatbox.addChatChannel("trade", "Trade", "trade", "", ColorRGBA.Yellow, true);

        chatbox.setSendKey(Keyboard.KEY_RETURN);
        chatbox.showSendButton(true);

        screen.addElement(chatbox);
        guiNode.addControl(screen);

        chatbox.setIsVisible(false);
    }
}
