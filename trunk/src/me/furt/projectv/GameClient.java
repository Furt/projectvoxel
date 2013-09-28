package me.furt.projectv;

import com.jme3.app.SimpleApplication;
import com.jme3.audio.AudioNode;
import com.jme3.bullet.BulletAppState;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapText;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import tonegod.skydome.SkyDome;

/**
 * Project V
 *
 * @author Furt
 */
public class GameClient extends SimpleApplication implements ScreenController {

    private static GameClient app;
    private World world;
    private BulletAppState bulletAppState;
    public SkyDome skyDome;
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
    private AudioNode bgMusic;

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
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        world = new World(app, bulletAppState);
        rootNode.attachChild(world.generateTestWorld());
        skyDome = new SkyDome(assetManager, cam);
        skyDome.setEnabled(true);
        Node sky = new Node();
        sky.setQueueBucket(RenderQueue.Bucket.Sky);
        sky.addControl(skyDome);
        sky.setCullHint(Spatial.CullHint.Never);
        rootNode.attachChild(sky);
        // TODO player model and set location
        // Load a model from test_data (OgreXML + material + texture)
        Spatial ninja = assetManager.loadModel("Models/Ninja/Ninja.mesh.xml");
        ninja.scale(0.05f, 0.05f, 0.05f);
        ninja.rotate(0.0f, -3.0f, 0.0f);
        ninja.setLocalTranslation(4.0f, 104.0f, 4.0f);
        rootNode.attachChild(ninja);
        // You must add a light to make the model visible
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-0.1f, -0.7f, -1.0f));
        rootNode.addLight(sun);
        
        cam.setLocation(setBlockVector(1f, 30f, 1f));
        // this is still kinda wonky
        cam.lookAtDirection(setBlockVector(2f, -13f, 8f), Vector3f.UNIT_Y);
        flyCam.setMoveSpeed(50);
        initHUD();
        /**
         * Music Test
         */
        bgMusic = new AudioNode(assetManager, "Sounds/NightTime.ogg", true);
        bgMusic.setLooping(false);
        bgMusic.setVolume(1);
        rootNode.attachChild(bgMusic);
        bgMusic.play();
        /**
         *
         */
    }

    @Override
    public void simpleUpdate(float tpf) {
    }

    @Override
    public void simpleRender(RenderManager rm) {
    }

    private void initHUD() {
        guiNode.detachAllChildren();
        this.setDisplayStatView(false);
        this.setDisplayFps(true);
        guiFont = assetManager.loadFont("/Interface/Default.fnt");
        BitmapText tittle = new BitmapText(guiFont, false);
        tittle.setSize(guiFont.getCharSet().getRenderedSize());
        tittle.setText("Project: V - alpha 1");
        tittle.setColor(ColorRGBA.White);
        tittle.setLocalTranslation(0, 700 + tittle.getLineHeight(), 0);
        guiNode.attachChild(tittle);

        BitmapText ch = new BitmapText(guiFont, false);
        ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
        ch.setText("+");
        ch.setLocalTranslation(settings.getWidth() / 2 - ch.getLineWidth() / 2, settings.getHeight() / 2 + ch.getLineHeight() / 2, 0);
        guiNode.attachChild(ch);

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

    public void bind(Nifty nifty, Screen screen) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void onStartScreen() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void onEndScreen() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Vector3f setBlockVector(float x, float y, float z) {
        float blockSize = WorldSettings.getSettings(app).getBlockSize();
        return new Vector3f(x * blockSize, y * blockSize, z * blockSize);
    }
}
