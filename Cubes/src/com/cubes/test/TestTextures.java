/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cubes.test;

import com.cubes.BlockTerrainControl;
import com.cubes.Vector3Int;
import com.cubes.test.blocks.Block_Cobble;
import com.cubes.test.blocks.Block_Grass;
import com.cubes.test.blocks.Block_Ice;
import com.cubes.test.blocks.Block_Leaves;
import com.cubes.test.blocks.Block_Log;
import com.cubes.test.blocks.Block_Mud;
import com.cubes.test.blocks.Block_Plank;
import com.cubes.test.blocks.Block_Stone;
import com.cubes.test.blocks.Block_Water;
import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Terry
 */
public class TestTextures extends SimpleApplication {

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
        CubesTestAssets1.registerBlocks();

        //This is your terrain, it contains the whole
        //block world and offers methods to modify it
        BlockTerrainControl blockTerrain = new BlockTerrainControl(CubesTestAssets1.getSettings(this), new Vector3Int(20, 1, 20));

        blockTerrain.setBlockArea(new Vector3Int(0, 0, 0), new Vector3Int(320, 20, 320), Block_Stone.class);
        blockTerrain.setBlockArea(new Vector3Int(0, 20, 0), new Vector3Int(320, 5, 320), Block_Grass.class);
        
        //Hmm cobblestone house with plank flooring you say??
        //left side
        blockTerrain.setBlockArea(new Vector3Int(3, 25, 3), new Vector3Int(6, 3, 1), Block_Cobble.class);
        //back side
        blockTerrain.setBlockArea(new Vector3Int(8, 25, 3), new Vector3Int(1, 3, 6), Block_Cobble.class);
        //right side
        blockTerrain.setBlockArea(new Vector3Int(3, 25, 8), new Vector3Int(6, 3, 1), Block_Cobble.class);
        //front side
        blockTerrain.setBlockArea(new Vector3Int(3, 25, 4), new Vector3Int(1, 3, 1), Block_Cobble.class);
        blockTerrain.setBlockArea(new Vector3Int(3, 27, 5), new Vector3Int(1, 1, 2), Block_Cobble.class);
        blockTerrain.setBlockArea(new Vector3Int(3, 25, 7), new Vector3Int(1, 3, 1), Block_Cobble.class);
        //roof
        blockTerrain.setBlockArea(new Vector3Int(4, 28, 4), new Vector3Int(4, 1, 4), Block_Ice.class);
        // floor
        blockTerrain.setBlockArea(new Vector3Int(4, 24, 4), new Vector3Int(4, 1, 4), Block_Plank.class);
        
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

        Node terrainNode = new Node();
        terrainNode.addControl(blockTerrain);
        rootNode.attachChild(terrainNode);

        cam.setLocation(new Vector3f(-6, 90, -6));
        cam.lookAtDirection(new Vector3f(0,0,0), Vector3f.UNIT_Y);
        flyCam.setMoveSpeed(50);
    }
}