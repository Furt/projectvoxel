/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.furt.projectv.states;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.math.Vector2f;
import com.jme3.network.Client;
import com.jme3.system.AppSettings;
import me.furt.projectv.network.messages.LoginMessage;
import tonegod.gui.controls.windows.LoginBox;
import tonegod.gui.core.Screen;

/**
 *
 * @author Terry
 */
public class LoginState extends AbstractAppState {

    Client client;
    Screen screen;
    SimpleApplication app;
    FlyByCamera flyCam;
    InputManager inputManager;
    LoginBox loginWindow;
    AppStateManager stateManager;
    AppSettings settings;

    public LoginState(SimpleApplication app, AppSettings settings, Screen screen, Client client) {
        this.app = app;
        this.screen = screen;
        this.client = client;
        this.settings = settings;
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.flyCam = this.app.getFlyByCamera();
        this.inputManager = this.app.getInputManager();
        this.stateManager = stateManager;
        initLoginWindow();
    }

    public void initLoginWindow() {
        flyCam.setDragToRotate(true);
        inputManager.setCursorVisible(true);
        app.setDisplayStatView(false);
        app.setDisplayFps(false);
        loginWindow = new LoginBox(screen,
                "loginWindow",
                new Vector2f(screen.getWidth() / 2 - 175, screen.getHeight() / 2 - 125)) {
            @Override
            public void onButtonLoginPressed(MouseButtonEvent evt, boolean toggled) {
                //client.send(new LoginMessage(getTextUserName(), getTextPassword()));
                System.out.println(getTextUserName() + " : " + getTextPassword());
                stateManager.attach(new IngameState(settings));
            }

            @Override
            public void onButtonCancelPressed(MouseButtonEvent evt, boolean toggled) {
            }
        };
        screen.addElement(loginWindow);
    }

    @Override
    public void cleanup() {
        super.cleanup();

        screen.removeElement(loginWindow);
    }

    public void finalizeUserLogin() {
    }
}
