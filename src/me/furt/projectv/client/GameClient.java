package me.furt.projectv.client;

import me.furt.projectv.core.Globals;
import com.jme3.app.SimpleApplication;
import com.jme3.renderer.RenderManager;
import com.jme3.system.AppSettings;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.ScreenController;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import me.furt.projectv.GameLogic;
import me.furt.projectv.Util;
import me.furt.projectv.state.MainMenuState;
import tonegod.gui.core.Screen;

/**
 * Project V
 *
 * @author Furt
 */
public class GameClient extends SimpleApplication {

    private static GameClient app;
    private Screen screen;
    private ScheduledExecutorService logicService;
    public static final Logger log = Logger.getLogger("Project-V");

    public static void main(String[] args) {
        log.setLevel(Level.WARNING);
        Util.registerSerializers();
        app = new GameClient();
        app.setPauseOnLostFocus(false);
        app.start();
    }

    public GameClient() {
        settings = new AppSettings(true);
        settings.setWidth(Globals.WINDOW_WIDTH);
        settings.setHeight(Globals.WINDOW_HEIGHT);
        settings.setTitle(Globals.NAME);
        settings.setFrameRate(Globals.SCENE_FPS);
        settings.setSettingsDialogImage("/Interface/pv-splash.png");
        try {
            settings.setIcons(new BufferedImage[]{
                        ImageIO.read(getClass().getResourceAsStream("/Interface/magex16.png")),
                        ImageIO.read(getClass().getResourceAsStream("/Interface/magex32.png"))
                    });
        } catch (IOException e) {
            log.log(Level.WARNING, "Unable to load program icons", e);
        }
    }

    @Override
    public void simpleInitApp() {
        // setup screen
        screen = new Screen(this);
        guiNode.addControl(screen);
        stateManager.attach(new MainMenuState());

        // attach logic
        logicService = Executors.newSingleThreadScheduledExecutor();
        logicService.scheduleAtFixedRate(new GameLogic(this), 0, 20, TimeUnit.MILLISECONDS);
    }

    @Override
    public void simpleUpdate(float tpf) {
    }

    @Override
    public void simpleRender(RenderManager rm) {
    }

    @Override
    public void destroy() {
        logicService.shutdown();
        super.destroy();
    }
}
