package me.furt.projectv;

import com.cubes.BlockChunkControl;
import com.cubes.BlockChunkListener;
import com.cubes.BlockNavigator;
import com.cubes.BlockTerrainControl;
import com.cubes.CubesSettings;
import com.cubes.Vector3Int;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.MeshCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapText;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.Trigger;
import com.jme3.light.DirectionalLight;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import me.furt.projectv.blocks.*;

/**
 * Project V
 *
 * @author Furt
 */
public class GameClient extends SimpleApplication {

    static final Logger log = Logger.getLogger("Project-V");
    private final Vector3Int terrainSize = new Vector3Int(256, 30, 256);
    private BulletAppState bulletAppState;
    private CharacterControl playerControl;
    private Vector3f walkDirection = new Vector3f();
    private boolean[] arrowKeys = new boolean[4];
    private CubesSettings cubesSettings;
    private BlockTerrainControl blockTerrain;
    private Node terrainNode = new Node();
    BitmapText debugger;

    public static void main(String[] args) {
        GameClient app = new GameClient();
        app.start();
    }

    public GameClient() {
        settings = new AppSettings(true);
        settings.setWidth(1280);
        settings.setHeight(720);
        settings.setTitle("Project V");
        try {
            settings.setIcons(new BufferedImage[]{
                        ImageIO.read(getClass().getResourceAsStream("/Textures/magex16.png")),
                        ImageIO.read(getClass().getResourceAsStream("/Textures/magex32.png"))
                    });
        } catch (IOException e) {
            log.log(Level.WARNING, "Unable to load program icons", e);
        }
    }

    @Override
    public void simpleInitApp() {
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        initControls();
        initBlockTerrain();
        initPlayer();
        initHUD();
        cam.lookAtDirection(new Vector3f(1.0F, 0.0F, 1.0F), Vector3f.UNIT_Y);
    }

    @Override
    public void simpleUpdate(float tpf) {
        /* increase this float to walk faster */
        float playerMoveSpeed = cubesSettings.getBlockSize() * 10.5F * tpf;
        Vector3f camDir = cam.getDirection().mult(playerMoveSpeed);
        Vector3f camLeft = cam.getLeft().mult(playerMoveSpeed);
        walkDirection.set(0.0F, 0.0F, 0.0F);
        if (arrowKeys[0]) {
            walkDirection.addLocal(camDir);
        }
        if (arrowKeys[1]) {
            walkDirection.addLocal(camLeft.negate());
        }
        if (arrowKeys[2]) {
            walkDirection.addLocal(camDir.negate());
        }
        if (arrowKeys[3]) {
            walkDirection.addLocal(camLeft);
        }
        walkDirection.setY(0.0F);
        playerControl.setWalkDirection(walkDirection);
        cam.setLocation(playerControl.getPhysicsLocation());
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    protected Spatial makeCharacter() {
        // load a character from jme3test-test-data
        Spatial golem = assetManager.loadModel("Models/Oto/Oto.mesh.xml");
        golem.scale(0.5f);
        golem.setLocalTranslation(-1.0f, -1.5f, -0.6f);

        // We must add a light to make the model visible
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-0.1f, -0.7f, -1.0f));
        golem.addLight(sun);
        return golem;
    }

    private void initControls() {
        inputManager.addMapping("move_left", new Trigger[]{new KeyTrigger(30)});
        inputManager.addMapping("move_right", new Trigger[]{new KeyTrigger(32)});
        inputManager.addMapping("move_up", new Trigger[]{new KeyTrigger(17)});
        inputManager.addMapping("move_down", new Trigger[]{new KeyTrigger(31)});
        inputManager.addMapping("jump", new Trigger[]{new KeyTrigger(57)});
        inputManager.addListener(actionListener, new String[]{"move_left"});
        inputManager.addListener(actionListener, new String[]{"move_right"});
        inputManager.addListener(actionListener, new String[]{"move_up"});
        inputManager.addListener(actionListener, new String[]{"move_down"});
        inputManager.addListener(actionListener, new String[]{"jump"});
    }
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean value, float tpf) {
            if (name.equals("move_up")) {
                arrowKeys[0] = value;
                if (value) {
                    enableDebugger("Walking Forward.");
                } else {
                    disableDebugger();
                }
            } else if (name.equals("move_right")) {
                arrowKeys[1] = value;
                if (value) {
                    enableDebugger("Strafing Right.");
                } else {
                    disableDebugger();
                }
            } else if (name.equals("move_left")) {
                arrowKeys[3] = value;
                if (value) {
                    enableDebugger("Strafing Left.");
                } else {
                    disableDebugger();
                }
            } else if (name.equals("move_down")) {
                arrowKeys[2] = value;
                if (value) {
                    enableDebugger("Walking Backwards.");
                } else {
                    disableDebugger();
                }
            } else if (name.equals("jump")) {
                playerControl.jump();
            }
        }
    };

    private void initBlockTerrain() {
        PVAssets.registerBlocks();
        PVAssets.initializeEnvironment(this);

        cubesSettings = PVAssets.getSettings(this);
        blockTerrain = new BlockTerrainControl(cubesSettings, new Vector3Int(7, 1, 7));
        blockTerrain.setBlocksFromNoise(new Vector3Int(), terrainSize, 0.3f, DirtBlock.class);
        blockTerrain.addChunkListener(new BlockChunkListener() {
            public void onSpatialUpdated(BlockChunkControl blockChunk) {
                Geometry optimizedGeometry = blockChunk.getOptimizedGeometry_Opaque();
                RigidBodyControl rigidBodyControl = (RigidBodyControl) optimizedGeometry.getControl(RigidBodyControl.class);
                if (rigidBodyControl == null) {
                    rigidBodyControl = new RigidBodyControl(0.0F);
                    optimizedGeometry.addControl(rigidBodyControl);
                    bulletAppState.getPhysicsSpace().add(rigidBodyControl);
                }
                rigidBodyControl.setCollisionShape(new MeshCollisionShape(optimizedGeometry.getMesh()));
            }
        });
        terrainNode.addControl(blockTerrain);
        terrainNode.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        rootNode.attachChild(terrainNode);
    }

    private void initHUD() {
        guiNode.detachAllChildren();
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        BitmapText tittle = new BitmapText(guiFont, false);
        tittle.setSize(guiFont.getCharSet().getRenderedSize());
        tittle.setText("Project-V alpha 1");
        tittle.setLocalTranslation(0, 700 + tittle.getLineHeight(), 0);
        guiNode.attachChild(tittle);

        BitmapText ch = new BitmapText(guiFont, false);
        ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
        ch.setText("+");
        ch.setLocalTranslation(settings.getWidth() / 2 - ch.getLineWidth() / 2, settings.getHeight() / 2 + ch.getLineHeight() / 2, 0);
        guiNode.attachChild(ch);

    }

    private void enableDebugger(String s) {
        debugger = new BitmapText(guiFont, false);
        debugger.setSize(guiFont.getCharSet().getRenderedSize());
        debugger.setText(s);
        debugger.setLocalTranslation(0, 650 + debugger.getLineHeight(), 0);
        guiNode.attachChild(debugger);

    }

    private void disableDebugger() {
        guiNode.detachChild(debugger);
    }

    private void initPlayer() {
        playerControl = new CharacterControl(new CapsuleCollisionShape(cubesSettings.getBlockSize() / 2.0F, cubesSettings.getBlockSize() * 2.0F), 0.05F);
        playerControl.setJumpSpeed(30.0F);
        playerControl.setFallSpeed(30.0F);
        playerControl.setGravity(120.0F);
        playerControl.setPhysicsLocation(new Vector3f(5.0F, terrainSize.getY() + 5, 5.0F).mult(cubesSettings.getBlockSize()));
        bulletAppState.getPhysicsSpace().add(playerControl);
    }

    private Vector3Int getCurrentPointedBlockLocation(boolean getNeighborLocation) {
        CollisionResults results = getRayCastingResults(terrainNode);
        if (results.size() > 0) {
            Vector3f collisionContactPoint = results.getClosestCollision().getContactPoint();
            return BlockNavigator.getPointedBlockLocation(blockTerrain, collisionContactPoint, getNeighborLocation);
        }
        return null;
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
}
