/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.furt.projectv.state;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.bullet.BulletAppState;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.FlyByCamera;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;
import java.util.concurrent.ConcurrentLinkedQueue;
import me.furt.projectv.World;
import me.furt.projectv.WorldSettings;

/**
 *
 * @author Terry
 */
public class IngameState extends AbstractAppState {

    private SimpleApplication app;
    private AppSettings settings;
    private Node rootNode;
    private Node guiNode;
    private AssetManager assetManager;
    private Camera cam;
    private FlyByCamera flyCam;
    private World world;
    private BulletAppState bulletAppState;
    private AudioNode bgMusic;
    private BitmapFont guiFont;
    private ConcurrentLinkedQueue<String> chatMessageQueue;
    private AppStateManager stateManager;

    public IngameState(AppSettings settings) {
        this.settings = settings;
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        this.app = (SimpleApplication) app;
        this.stateManager = stateManager;
        this.assetManager = this.app.getAssetManager();
        this.rootNode = this.app.getRootNode();
        this.guiNode = this.app.getGuiNode();
        this.cam = this.app.getCamera();
        this.flyCam = this.app.getFlyByCamera();
        bulletAppState = new BulletAppState();
        stateManager.detach(stateManager.getState(LoginState.class));
        stateManager.attach(bulletAppState);
        world = new World(this.app, bulletAppState);
        rootNode.attachChild(world.generateTestWorld());
        // TODO player model and set location
        
        // You must add a light to make the model visible
        //WorldSettings.initializeEnvironment(this.app);

        cam.setLocation(setBlockVector(1f, 30f, 1f));
        // this is still kinda wonky
        cam.lookAtDirection(setBlockVector(2f, -13f, 8f), Vector3f.UNIT_Y);
        this.flyCam.setMoveSpeed(50);
        initHUD();
        /**
         * Music Test
         */
        //bgMusic = new AudioNode(assetManager, "Sounds/Music/DayTime.ogg", true);
        //bgMusic.setLooping(false);
        //bgMusic.setVolume(1);
        //rootNode.attachChild(bgMusic);
        //bgMusic.play();
        /**
         *
         */
    }

    @Override
    public void update(float tpf) {
    }

    private void initHUD() {
        guiNode.detachAllChildren();
        this.app.setDisplayStatView(false);
        this.app.setDisplayFps(true);
        guiFont = assetManager.loadFont("/Interface/Default.fnt");
        BitmapText tittle = new BitmapText(guiFont, false);
        tittle.setSize(guiFont.getCharSet().getRenderedSize());
        tittle.setText("Project: V - alpha 0.0.1");
        tittle.setColor(ColorRGBA.White);
        tittle.setLocalTranslation(0, 700 + tittle.getLineHeight(), 0);
        guiNode.attachChild(tittle);

        BitmapText ch = new BitmapText(guiFont, false);
        ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
        ch.setText("+");
        ch.setLocalTranslation(settings.getWidth() / 2 - ch.getLineWidth() / 2, settings.getHeight() / 2 + ch.getLineHeight() / 2, 0);
        guiNode.attachChild(ch);

    }

    public Vector3f setBlockVector(float x, float y, float z) {
        float blockSize = WorldSettings.getSettings(app).getBlockSize();
        return new Vector3f(x * blockSize, y * blockSize, z * blockSize);
    }
}
