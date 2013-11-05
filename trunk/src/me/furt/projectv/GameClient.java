package me.furt.projectv;

import com.cubes.BlockNavigator;
import com.cubes.TerrainControl;
import com.cubes.Vector3Int;
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
import com.simsilica.es.sql.SqlEntityData;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.ScreenController;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.Callable;
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
    private TextRenderer statusText;
    private ScheduledExecutorService exec;
    static final Logger log = Logger.getLogger("Project-V");

    public static void main(String[] args) {
        log.setLevel(Level.WARNING);
        for (int i = 0; i < args.length; i++) {
            String string = args[i];
            if ("-server".equals(string)) {
            }
        }
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
        Util.registerSerializers();
        log.info("ProjectV has started.");
    }

    @Override
    public void simpleInitApp() {
        try {
            entityData = new SqlEntityData("/Data", 20000L);
        } catch (SQLException ex) {
            Logger.getLogger(GameClient.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            //entityData = new DefaultEntityData();
            client = Network.connectToServer(Globals.NAME, Globals.CLIENT_VERSION, Globals.DEFAULT_SERVER, Globals.DEFAULT_PORT_TCP, Globals.DEFAULT_PORT_UDP);
            client.start();
        } catch (IOException ex) {
            Logger.getLogger(GameClient.class.getName()).log(Level.SEVERE, null, ex);
        }

        screen = new Screen(this);
        guiNode.addControl(screen);
        //stateManager.attach(new IngameState(settings));
        stateManager.attach(new LoginState(this, settings, screen, client));

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

    @Override
    public void destroy() {
        exec.shutdown();
        entityData.close();
        client.close();
        super.destroy();
    }

    private Vector3Int getCurrentPointedBlockLocation(Node terrainNode, TerrainControl blockTerrain, boolean getNeighborLocation) {
        CollisionResults results = getRayCastingResults(terrainNode);
        if (results.size() > 0) {
            Vector3f collisionContactPoint = results.getClosestCollision().getContactPoint();
            return BlockNavigator.getPointedBlockLocation(blockTerrain, collisionContactPoint, getNeighborLocation);
        }
        return null;
    }

    private CollisionResults getRayCastingResults(Node node) {
        Vector3f origin = cam.getWorldCoordinates(new Vector2f((settings.getWidth() / 2), (settings.getHeight() / 2)), 0.0f);
        Vector3f direction = cam.getWorldCoordinates(new Vector2f((settings.getWidth() / 2), (settings.getHeight() / 2)), 0.3f);
        direction.subtractLocal(origin).normalizeLocal();
        Ray ray = new Ray(origin, direction);
        CollisionResults results = new CollisionResults();
        node.collideWith(ray, results);
        return results;
    }
    
    public void setStatusText(final String text) {
        enqueue(new Callable<Void>() {

            public Void call() throws Exception {
                statusText.setText(text);
                return null;
            }
        });
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
