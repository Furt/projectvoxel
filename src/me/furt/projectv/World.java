/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.furt.projectv;

import com.cubes.BlockChunkControl;
import com.cubes.BlockChunkListener;
import com.cubes.BlockTerrainControl;
import com.cubes.Vector3Int;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.MeshCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import me.furt.projectv.block.*;

/**
 *
 * @author Terry
 */
public class World {

    private BlockTerrainControl blockTerrain;
    private Node terrainNode = new Node();
    private final BulletAppState bas;
    private final SimpleApplication app;

    public World(SimpleApplication app, BulletAppState bas) {
        this.app = app;
        this.bas = bas;
    }

    public Node generateTestWorld() {
        WorldSettings.registerBlocks();
        blockTerrain = new BlockTerrainControl(WorldSettings.getSettings(app), new Vector3Int(10, 1, 10));
        // World base
        blockTerrain.setBlockArea(new Vector3Int(0, 0, 0), new Vector3Int(320, 20, 320), Block_Stone.class);
        blockTerrain.setBlockArea(new Vector3Int(0, 20, 0), new Vector3Int(320, 5, 320), Block_Grass.class);

        //Cobblestone house
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
        blockTerrain.setBlockArea(new Vector3Int(4, 28, 4), new Vector3Int(4, 1, 4), Block_Stone_Slab.class);
        //used to be gravel; testing stone slabs
        // floor
        blockTerrain.setBlockArea(new Vector3Int(4, 24, 4), new Vector3Int(4, 1, 4), Block_Plank.class);

        // Lava pond
        blockTerrain.setBlockArea(new Vector3Int(10, 24, 12), new Vector3Int(6, 1, 6), Block_Lava.class);
        // Stone layer
        blockTerrain.setBlockArea(new Vector3Int(10, 23, 12), new Vector3Int(6, 1, 6), Block_Stone.class);

        // Water pond
        // pond floor
        blockTerrain.setBlockArea(new Vector3Int(3, 23, 12), new Vector3Int(6, 1, 6), Block_Mud.class);
        // water layer
        blockTerrain.setBlockArea(new Vector3Int(3, 24, 12), new Vector3Int(6, 1, 6), Block_Water.class);
        
        //Sand Pit 
        blockTerrain.setBlockArea(new Vector3Int(10, 24, 3), new Vector3Int(6, 1, 6), Block_Sand.class);
        
        //clay deposit
        blockTerrain.setBlockArea(new Vector3Int(18, 24, 3), new Vector3Int(6, 1, 6), Block_Red_Clay.class);

        // Tree
        blockTerrain.setBlockArea(new Vector3Int(3, 25, 20), new Vector3Int(1, 5, 1), Block_Log.class);
        blockTerrain.setBlockArea(new Vector3Int(2, 28, 19), new Vector3Int(3, 2, 3), Block_Leaves.class);
        blockTerrain.setBlock(3, 30, 20, Block_Leaves.class);

        blockTerrain.addChunkListener(new BlockChunkListener() {
            public void onSpatialUpdated(BlockChunkControl blockChunk) {
                Geometry optimizedGeometry = blockChunk.getOptimizedGeometry_Opaque();
                RigidBodyControl rigidBodyControl = optimizedGeometry.getControl(RigidBodyControl.class);
                if (rigidBodyControl == null) {
                    rigidBodyControl = new RigidBodyControl(0);
                    optimizedGeometry.addControl(rigidBodyControl);
                    bas.getPhysicsSpace().add(rigidBodyControl);
                }
                rigidBodyControl.setCollisionShape(new MeshCollisionShape(optimizedGeometry.getMesh()));
            }
        });
        terrainNode.addControl(blockTerrain);
        terrainNode.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        terrainNode.addControl(new RigidBodyControl(0));
        bas.getPhysicsSpace().addAll(terrainNode);
        return terrainNode;

    }
}
