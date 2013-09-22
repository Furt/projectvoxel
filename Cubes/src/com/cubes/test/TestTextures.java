/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cubes.test;

import com.cubes.BlockChunkControl;
import com.cubes.BlockChunkListener;
import com.cubes.BlockTerrainControl;
import com.cubes.CubesSettings;
import com.cubes.Vector3Int;
import com.cubes.test.blocks.*;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.MeshCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Terry
 */
public class TestTextures extends SimpleApplication implements ActionListener {
    //private final Vector3Int terrainSize = new Vector3Int(100, 30, 100);
    private BulletAppState bulletAppState;
    private CharacterControl playerControl;
    private Vector3f walkDirection = new Vector3f();
    private boolean[] arrowKeys = new boolean[4];
    private CubesSettings cubesSettings;
    private BlockTerrainControl blockTerrain;
    private Node terrainNode = new Node();
    
    public static void main(String[] args) {
        Logger.getLogger("").setLevel(Level.SEVERE);
        TestTextures app = new TestTextures();
        app.start();
    }

    public TestTextures() {
        settings = new AppSettings(true);
        settings.setWidth(1280);
        settings.setHeight(720);
        settings.setTitle("Jerry's Texture Demo");
    }

    @Override
    public void simpleInitApp() {
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        CubesTestAssets1.registerBlocks();

        //This is your terrain, it contains the whole
        //block world and offers methods to modify it
        blockTerrain = new BlockTerrainControl(CubesTestAssets1.getSettings(this), new Vector3Int(20, 1, 20));

        blockTerrain.setBlockArea(new Vector3Int(0, 0, 0), new Vector3Int(320, 20, 320), Block_Stone.class);
        blockTerrain.setBlockArea(new Vector3Int(0, 20, 0), new Vector3Int(320, 5, 320), Block_Grass.class);
        
        //Hmm cobblestone house with plank flooring you say??
        //left side
        blockTerrain.setBlockArea(new Vector3Int(3, 25, 3), new Vector3Int(6, 3, 1), Block_Cobble.class);
        blockTerrain.setBlockArea(new Vector3Int(5, 26, 3), new Vector3Int(2, 1, 1), Block_Glass.class);
        //back side
        blockTerrain.setBlockArea(new Vector3Int(8, 25, 3), new Vector3Int(1, 3, 6), Block_Cobble.class);
        blockTerrain.setBlockArea(new Vector3Int(8, 26, 5), new Vector3Int(1, 1, 2), Block_Glass.class);
        //right side
        blockTerrain.setBlockArea(new Vector3Int(3, 25, 8), new Vector3Int(6, 3, 1), Block_Cobble.class);
        //front side
        blockTerrain.setBlockArea(new Vector3Int(3, 25, 4), new Vector3Int(1, 3, 1), Block_Cobble.class);
        blockTerrain.setBlockArea(new Vector3Int(3, 27, 5), new Vector3Int(1, 1, 2), Block_Cobble.class);
        blockTerrain.setBlockArea(new Vector3Int(3, 25, 7), new Vector3Int(1, 3, 1), Block_Cobble.class);
        //roof
        blockTerrain.setBlockArea(new Vector3Int(4, 28, 4), new Vector3Int(4, 1, 4), Block_Gravel.class);
        // floor
        blockTerrain.setBlockArea(new Vector3Int(4, 24, 4), new Vector3Int(4, 1, 4), Block_Plank.class);
        
        // Lava Pit
        blockTerrain.setBlockArea(new Vector3Int(10, 24, 12), new Vector3Int(6, 1, 6), Block_Lava.class);
        // Stone layer
        blockTerrain.setBlockArea(new Vector3Int(10, 23, 12), new Vector3Int(6, 1, 6), Block_Stone.class);
        
        // Pond floor
        blockTerrain.setBlockArea(new Vector3Int(3, 23, 12), new Vector3Int(6, 1, 6), Block_Mud.class);
        // water layer
        blockTerrain.setBlockArea(new Vector3Int(3, 24, 12), new Vector3Int(6, 1, 6), Block_Water.class);
        
        // did someone say tree?
        blockTerrain.setBlockArea(new Vector3Int(3, 25, 20), new Vector3Int(1, 5, 1), Block_Log.class);
        blockTerrain.setBlockArea(new Vector3Int(2, 28, 19), new Vector3Int(3, 2, 3), Block_Leaves.class);
        blockTerrain.setBlock(3, 30, 20, Block_Leaves.class);
        //blockTerrain.setBlock(0, 0, 0, Block_Grass.class); //For the lazy users :P
        //blockTerrain.setBlockArea(new Vector3Int(1, 1, 1), new Vector3Int(1, 3, 1), Block_Stone.class);
        //Removing a block works in a similar way
        //blockTerrain.removeBlock(new Vector3Int(1, 2, 1));
        blockTerrain.addChunkListener(new BlockChunkListener() {

            public void onSpatialUpdated(BlockChunkControl blockChunk) {
                Geometry optimizedGeometry = blockChunk.getOptimizedGeometry_Opaque();
                RigidBodyControl rigidBodyControl = optimizedGeometry.getControl(RigidBodyControl.class);
                if(rigidBodyControl == null){
                    rigidBodyControl = new RigidBodyControl(0);
                    optimizedGeometry.addControl(rigidBodyControl);
                    bulletAppState.getPhysicsSpace().add(rigidBodyControl);
                }
                rigidBodyControl.setCollisionShape(new MeshCollisionShape(optimizedGeometry.getMesh()));
            }
        });
        terrainNode.addControl(blockTerrain);
        terrainNode.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        rootNode.attachChild(terrainNode);

        cam.setLocation(new Vector3f(-6, 90, -6));
        cam.lookAtDirection(new Vector3f(0,0,0), Vector3f.UNIT_Y);
        flyCam.setMoveSpeed(50);
    }
    
    private void initControls(){
        inputManager.addMapping("move_left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("move_right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("move_up", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("move_down", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("jump", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(this, "move_left");
        inputManager.addListener(this, "move_right");
        inputManager.addListener(this, "move_up");
        inputManager.addListener(this, "move_down");
        inputManager.addListener(this, "jump");
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}