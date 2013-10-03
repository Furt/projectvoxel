/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.furt.projectv.states;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.math.Vector2f;
import tonegod.gui.controls.windows.LoginBox;
import tonegod.gui.core.Screen;

/**
 *
 * @author Terry
 */
public class LoginState extends AbstractAppState {
    Screen screen;
    SimpleApplication app;
    LoginBox loginWindow;
    
    public LoginState(SimpleApplication app, Screen screen) {
        this.app = app;
        this.screen = screen;
    }
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        
        initLoginWindow();
    }
 
    public void initLoginWindow() {
        loginWindow = new LoginBox(screen, 
                "loginWindow",
                new Vector2f(screen.getWidth()/2-175,screen.getHeight()/2-125)) {

            @Override
            public void onButtonLoginPressed(MouseButtonEvent evt, boolean toggled) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void onButtonCancelPressed(MouseButtonEvent evt, boolean toggled) {
                throw new UnsupportedOperationException("Not supported yet.");
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
