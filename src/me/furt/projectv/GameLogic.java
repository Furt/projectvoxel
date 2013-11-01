package me.furt.projectv;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import me.furt.projectv.states.DamageAppState;
import me.furt.projectv.states.GuiAppState;

/**
 * Project V
 *
 * @author Furt
 */
public class GameLogic implements Runnable {
 
    private final float tpf = 0.02f;
    private AppStateManager stateManager;
 
    public GameLogic(Application app) {
        stateManager = new AppStateManager(app);
 
        //add the logic AppStates to this thread
        stateManager.attach(new GuiAppState());
        stateManager.attach(new DamageAppState());
    }
 
    public void run() {
        stateManager.update(tpf);
    }
}


