package me.furt.test;

import com.cubes.CubesSettings;
import com.cubes.TerrainControl;
import com.cubes.Vector3Int;
import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapText;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.logging.Logger;
import me.furt.projectv.WorldSettings;
import me.furt.projectv.block.Block_Grass;

/**
 * Project V
 *
 * @author Furt
 */
public class ModelTest extends SimpleApplication {

    public static final Logger log = Logger.getLogger("Project-V");
    public Spatial blue;
    public Spatial red;
    public BitmapText playerLoc;
    private BitmapText playerDir;
    private TerrainControl blockTerrain;
    private CubesSettings cubeSettings;
    private Node terrainNode;

    public static void main(String[] args) {
        ModelTest app = new ModelTest();
        app.start();
    }
    
    public ModelTest() {
    }

    @Override
    public void simpleInitApp() {
        // Get location debug information
        cam.setLocation(new Vector3f(87f, 4f, 71f));
        cam.lookAtDirection(new Vector3f(0.01f, -0.2f, 1f), Vector3f.UNIT_Y);
        flyCam.setMoveSpeed(50);
        
        Vector3f loc = cam.getLocation();
        Vector3f dir = cam.getDirection();
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        
        playerLoc = new BitmapText(guiFont, false);
        playerLoc.setSize(guiFont.getCharSet().getRenderedSize());
        playerLoc.setText("Player : X= " + String.format("%.3f", loc.getX()) + ", Y= " + String.format("%.3f", loc.getY()) + ", Z= " + String.format("%.3f", loc.getZ()));
        playerLoc.setLocalTranslation(300, playerLoc.getLineHeight() * 2, 0);
        guiNode.attachChild(playerLoc);
        
        playerDir = new BitmapText(guiFont, false);
        playerDir.setSize(guiFont.getCharSet().getRenderedSize());
        playerDir.setText("Direction : X= " + String.format("%.4f", dir.getX()) + ", Y= " + String.format("%.4f", dir.getY()) + ", Z= " + String.format("%.4f", dir.getZ()));
        playerDir.setLocalTranslation(300, playerDir.getLineHeight(), 0);
        guiNode.attachChild(playerDir);
        
        // Setup Lighting
        DirectionalLight sun = new DirectionalLight();
        sun.setColor(ColorRGBA.White);
        sun.setDirection(new Vector3f(-0.5f, -0.5f, -0.5f).normalizeLocal());
        rootNode.addLight(sun);
        
        DirectionalLight sun1 = new DirectionalLight();
        sun1.setColor(ColorRGBA.White);
        sun1.setDirection(new Vector3f(0.5f, 0.5f, 0.5f).normalizeLocal());
        rootNode.addLight(sun1);

        // Blue Model
        blue = assetManager.loadModel("Models/BLUE/BlueMan.j3o");
        blue.setLocalTranslation(84.5f, 1f, 79.5f);
        blue.scale(0.20f);
        this.getRootNode().attachChild(blue);

        // Red Model
        red = assetManager.loadModel("Models/RED/RedMan.j3o");
        red.setLocalTranslation(89.5f, 1f, 79.5f);
        red.scale(0.20f);
        this.getRootNode().attachChild(red);
        
        // Give it some terrain to check scale
        WorldSettings.registerBlocks();
        WorldSettings.initializeEnvironment(this);
        cubeSettings = WorldSettings.getSettings(this);
        blockTerrain = new TerrainControl(cubeSettings, new Vector3Int(10, 1, 10));
        blockTerrain.setBlockArea(new Vector3Int(0,0,0), new Vector3Int(160,1,160), Block_Grass.class);
        terrainNode = new Node("Terrain");
        terrainNode.addControl(blockTerrain);
        rootNode.attachChild(terrainNode);
    }

    @Override
    public void simpleUpdate(float tpf) {
        Vector3f loc = cam.getLocation();
        Vector3f dir = cam.getDirection();
        playerLoc.setText("Player    : X= " + String.format("%.3f", loc.getX()) + ", Y= " + String.format("%.3f", loc.getY()) + ", Z= " + String.format("%.3f", loc.getZ()));
        playerDir.setText("Direction : X= " + String.format("%.4f", dir.getX()) + ", Y= " + String.format("%.4f", dir.getY()) + ", Z= " + String.format("%.4f", dir.getZ()));

    }

    @Override
    public void simpleRender(RenderManager rm) {
        super.simpleRender(rm);
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
