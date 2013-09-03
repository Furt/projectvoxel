package com.cubes.test;

import com.cubes.BlockChunkControl;
import com.cubes.BlockChunkListener;
import com.cubes.BlockTerrainControl;
import com.cubes.CubesSettings;
import com.cubes.Vector3Int;
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

    private final Vector3Int terrainSize = new Vector3Int(100, 30, 100);
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
        this.settings = new AppSettings(true);
        this.settings.setWidth(1280);
        this.settings.setHeight(720);
        this.settings.setTitle("Cubes Demo - Physics");
        this.settings.setFrameRate(60);
    }

    public void simpleInitApp() {
        this.bulletAppState = new BulletAppState();
        this.stateManager.attach(this.bulletAppState);
        initControls();
        initBlockTerrain();
        initPlayer();
        this.cam.lookAtDirection(new Vector3f(1.0F, 0.0F, 1.0F), Vector3f.UNIT_Y);
    }

    private void initControls() {
        this.inputManager.addMapping("move_left", new Trigger[]{new KeyTrigger(30)});
        this.inputManager.addMapping("move_right", new Trigger[]{new KeyTrigger(32)});
        this.inputManager.addMapping("move_up", new Trigger[]{new KeyTrigger(17)});
        this.inputManager.addMapping("move_down", new Trigger[]{new KeyTrigger(31)});
        this.inputManager.addMapping("jump", new Trigger[]{new KeyTrigger(57)});
        this.inputManager.addListener(this, new String[]{"move_left"});
        this.inputManager.addListener(this, new String[]{"move_right"});
        this.inputManager.addListener(this, new String[]{"move_up"});
        this.inputManager.addListener(this, new String[]{"move_down"});
        this.inputManager.addListener(this, new String[]{"jump"});
    }

    private void initBlockTerrain() {
        CubesTestAssets.registerBlocks();
        CubesTestAssets.initializeEnvironment(this);

        this.cubesSettings = CubesTestAssets.getSettings(this);
        this.blockTerrain = new BlockTerrainControl(this.cubesSettings, new Vector3Int(7, 1, 7));
        this.blockTerrain.setBlocksFromNoise(new Vector3Int(), this.terrainSize, 0.8F, Block_Grass.class);
        this.blockTerrain.addChunkListener(new BlockChunkListener() {
            public void onSpatialUpdated(BlockChunkControl blockChunk) {
                Geometry optimizedGeometry = blockChunk.getOptimizedGeometry_Opaque();
                RigidBodyControl rigidBodyControl = (RigidBodyControl) optimizedGeometry.getControl(RigidBodyControl.class);
                if (rigidBodyControl == null) {
                    rigidBodyControl = new RigidBodyControl(0.0F);
                    optimizedGeometry.addControl(rigidBodyControl);
                    TestPhysics.this.bulletAppState.getPhysicsSpace().add(rigidBodyControl);
                }
                rigidBodyControl.setCollisionShape(new MeshCollisionShape(optimizedGeometry.getMesh()));
            }
        });
        this.terrainNode.addControl(this.blockTerrain);
        this.terrainNode.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        this.rootNode.attachChild(this.terrainNode);
    }

    private void initPlayer() {
        this.playerControl = new CharacterControl(new CapsuleCollisionShape(this.cubesSettings.getBlockSize() / 2.0F, this.cubesSettings.getBlockSize() * 2.0F), 0.05F);
        this.playerControl.setJumpSpeed(25.0F);
        this.playerControl.setFallSpeed(20.0F);
        this.playerControl.setGravity(70.0F);
        this.playerControl.setPhysicsLocation(new Vector3f(5.0F, this.terrainSize.getY() + 5, 5.0F).mult(this.cubesSettings.getBlockSize()));
        this.bulletAppState.getPhysicsSpace().add(this.playerControl);
    }

    @Override
    public void simpleUpdate(float lastTimePerFrame) {
        float playerMoveSpeed = this.cubesSettings.getBlockSize() * 6.5F * lastTimePerFrame;
        Vector3f camDir = this.cam.getDirection().mult(playerMoveSpeed);
        Vector3f camLeft = this.cam.getLeft().mult(playerMoveSpeed);
        this.walkDirection.set(0.0F, 0.0F, 0.0F);
        if (this.arrowKeys[0]) {
            this.walkDirection.addLocal(camDir);
        }
        if (this.arrowKeys[1]) {
            this.walkDirection.addLocal(camLeft.negate());
        }
        if (this.arrowKeys[2]) {
            this.walkDirection.addLocal(camDir.negate());
        }
        if (this.arrowKeys[3]) {
            this.walkDirection.addLocal(camLeft);
        }
        this.walkDirection.setY(0.0F);
        this.playerControl.setWalkDirection(this.walkDirection);
        this.cam.setLocation(this.playerControl.getPhysicsLocation());
    }

    public void onAction(String actionName, boolean value, float lastTimePerFrame) {
        if (actionName.equals("move_up")) {
            this.arrowKeys[0] = value;
        } else if (actionName.equals("move_right")) {
            this.arrowKeys[1] = value;
        } else if (actionName.equals("move_left")) {
            this.arrowKeys[3] = value;
        } else if (actionName.equals("move_down")) {
            this.arrowKeys[2] = value;
        } else if (actionName.equals("jump")) {
            this.playerControl.jump();
        }
    }
}