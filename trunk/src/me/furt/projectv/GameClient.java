package me.furt.projectv;

import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.network.Client;
import com.jme3.network.Network;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import com.simsilica.es.EntityData;
import com.simsilica.es.base.DefaultEntityData;
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
import me.furt.projectv.states.IngameState;
import me.furt.projectv.states.LoginState;
import tonegod.gui.core.Screen;

/**
 * Project V
 *
 * @author Furt
 */
public class GameClient extends SimpleApplication implements ScreenController {

    private static GameClient app;
    public Client client;
    public Screen screen;
    public EntityData entityData;
    private ScheduledExecutorService exec;
    static final Logger log = Logger.getLogger("Project-V");

    public static void main(String[] args) {
        for (int i = 0; i < args.length; i++) {
            String string = args[i];
            if ("-server".equals(string)) {
                GameServer.main(args);
                return;
            }
        }
        Util.registerSerializers();
        app = new GameClient();
        app.setSettings(getSettings());
        app.setPauseOnLostFocus(false);
        app.start();
    }

    public GameClient() {
        settings = new AppSettings(true);
        settings.setWidth(Globals.WINDOW_WIDTH);
        settings.setHeight(Globals.WINDOW_HEIGHT);
        settings.setTitle(Globals.VERSION);
        try {
            settings.setIcons(new BufferedImage[]{
                        ImageIO.read(getClass().getResourceAsStream("/Textures/magex16.png")),
                        ImageIO.read(getClass().getResourceAsStream("/Textures/magex32.png"))
                    });
        } catch (IOException e) {
            log.log(Level.WARNING, "Unable to load program icons", e);
        }

        log.info("ProjectV Initialized.");
    }

    @Override
    public void simpleInitApp() {
        entityData = new DefaultEntityData();
        client = Network.createClient(Globals.VERSION, Globals.CLIENT_VERSION);
        //client.start();
        screen = new Screen(this);
        guiNode.addControl(screen);
        stateManager.attach(new IngameState(settings));
        //stateManager.attach(new LoginState(this, screen, client));
        
        // attach logic
        exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleAtFixedRate(new GameLogic(this), 0, 20, TimeUnit.MILLISECONDS);
    }
    public EntityData getEntityData() {
        return entityData;
    }

    @Override
    public void simpleUpdate(float tpf) {
    }

    @Override
    public void simpleRender(RenderManager rm) {
    }

    private CollisionResults getRayCastingResults(Node node) {
        Vector3f origin = cam.getWorldCoordinates(new Vector2f(settings.getWidth() / 2, settings.getHeight() / 2), 0.0F);
        Vector3f direction = cam.getWorldCoordinates(new Vector2f(settings.getWidth() / 2, settings.getHeight() / 2), 0.3F);
        direction.subtractLocal(origin).normalizeLocal();
        Ray ray = new Ray(origin, direction);
        CollisionResults results = new CollisionResults();
        node.collideWith(ray, results);
        return results;
    }

    public static AppSettings getSettings() {
        AppSettings settings = new AppSettings(true);
        settings.setFrameRate(Globals.SCENE_FPS);
        settings.setSettingsDialogImage("/Interface/pv-splash.png");
        settings.setTitle("ProjectV");
        return settings;
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
