package me.furt.projectv;

import com.cubes.BlockNavigator;
import com.cubes.TerrainControl;
import com.cubes.Vector3Int;
import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.ScreenController;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import me.furt.projectv.state.MainMenuState;
import tonegod.gui.core.Screen;

/**
 * Project V
 *
 * @author Furt
 */
public class GameClient extends SimpleApplication implements ScreenController {

    private static GameClient app;
    private Screen screen;
    private TextRenderer statusText;
    private ScheduledExecutorService exec;
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
        settings.setTitle("ProjectV");
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
        exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleAtFixedRate(new GameLogic(this), 0, 20, TimeUnit.MILLISECONDS);
    }

    @Override
    public void simpleUpdate(float tpf) {
    }

    @Override
    public void simpleRender(RenderManager rm) {
    }

    @Override
    public void destroy() {
        exec.shutdown();
        super.destroy();
    }
    
    public void bind(Nifty nifty, de.lessvoid.nifty.screen.Screen screen) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void onStartScreen() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void onEndScreen() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
