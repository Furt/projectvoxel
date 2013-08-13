package me.furt.projectv.test;

import com.cubes.BlockChunkControl;
import com.cubes.BlockChunkListener;
import com.cubes.BlockTerrainControl;
import com.cubes.CubesSettings;
import com.cubes.Vector3Int;
import com.cubes.test.CubesTestAssets;
import com.cubes.test.blocks.Block_Grass;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.MeshCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.Trigger;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestPhysics extends SimpleApplication
        implements ActionListener {

    private final Vector3Int terrainSize = new Vector3Int(100, 20, 100);
    private BulletAppState bulletAppState;
    private CharacterControl playerControl;
    private Vector3f walkDirection = new Vector3f();
    private boolean[] arrowKeys = new boolean[4];
    private CubesSettings cubesSettings;
    private BlockTerrainControl blockTerrain;
    private Node terrainNode = new Node();

    public static void main(String[] args) {
        Logger.getLogger("").setLevel(Level.SEVERE);
        TestPhysics app = new TestPhysics();
        app.start();
    }

    public TestPhysics() {
        settings = new AppSettings(true);
        settings.setWidth(1280);
        settings.setHeight(720);
        settings.setTitle("Cubes Demo - Physics");
        settings.setFrameRate(60);
    }

    public void simpleInitApp() {
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        initControls();
        initBlockTerrain();
        initPlayer();
        cam.lookAtDirection(new Vector3f(1.0F, 0.0F, 1.0F), Vector3f.UNIT_Y);
    }

    private void initControls() {
        inputManager.addMapping("move_left", new Trigger[]{new KeyTrigger(30)});
        inputManager.addMapping("move_right", new Trigger[]{new KeyTrigger(32)});
        inputManager.addMapping("move_up", new Trigger[]{new KeyTrigger(17)});
        inputManager.addMapping("move_down", new Trigger[]{new KeyTrigger(31)});
        inputManager.addMapping("jump", new Trigger[]{new KeyTrigger(57)});
        inputManager.addListener(this, new String[]{"move_left"});
        inputManager.addListener(this, new String[]{"move_right"});
        inputManager.addListener(this, new String[]{"move_up"});
        inputManager.addListener(this, new String[]{"move_down"});
        inputManager.addListener(this, new String[]{"jump"});
    }

    private void initBlockTerrain() {
        CubesTestAssets.registerBlocks();
        CubesTestAssets.initializeEnvironment(this);

        cubesSettings = CubesTestAssets.getSettings(this);
        blockTerrain = new BlockTerrainControl(cubesSettings, new Vector3Int(7, 1, 7));
        blockTerrain.setBlocksFromNoise(new Vector3Int(), terrainSize, 0.8F, Block_Grass.class);
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

    private void initPlayer() {
        playerControl = new CharacterControl(new CapsuleCollisionShape(cubesSettings.getBlockSize() / 2.0F, cubesSettings.getBlockSize() * 2.0F), 0.05F);
        playerControl.setJumpSpeed(25.0F);
        playerControl.setFallSpeed(20.0F);
        playerControl.setGravity(70.0F);
        playerControl.setPhysicsLocation(new Vector3f(5.0F, terrainSize.getY() + 5, 5.0F).mult(cubesSettings.getBlockSize()));
        bulletAppState.getPhysicsSpace().add(playerControl);
    }

    @Override
    public void simpleUpdate(float lastTimePerFrame) {
        float playerMoveSpeed = cubesSettings.getBlockSize() * 6.5F * lastTimePerFrame;
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

    public void onAction(String actionName, boolean value, float lastTimePerFrame) {
        if (actionName.equals("move_up")) {
            arrowKeys[0] = value;
        } else if (actionName.equals("move_right")) {
            arrowKeys[1] = value;
        } else if (actionName.equals("move_left")) {
            arrowKeys[3] = value;
        } else if (actionName.equals("move_down")) {
            arrowKeys[2] = value;
        } else if (actionName.equals("jump")) {
            playerControl.jump();
        }
    }
}