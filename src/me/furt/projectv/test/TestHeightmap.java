package me.furt.projectv.test;

import com.cubes.BlockTerrainControl;
import com.cubes.Vector3Int;
import com.cubes.test.CubesTestAssets;
import com.cubes.test.blocks.Block_Grass;
import com.cubes.test.blocks.Block_Stone;
import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestHeightmap extends SimpleApplication {

    public static void main(String[] args) {
        Logger.getLogger("").setLevel(Level.SEVERE);
        TestHeightmap app = new TestHeightmap();
        app.start();
    }

    public TestHeightmap() {
        settings = new AppSettings(true);
        settings.setWidth(1280);
        settings.setHeight(720);
        settings.setTitle("Cubes Demo - Heightmap");
    }

    public void simpleInitApp() {
        CubesTestAssets.registerBlocks();

        BlockTerrainControl blockTerrain = new BlockTerrainControl(CubesTestAssets.getSettings(this), new Vector3Int(4, 1, 4));
        blockTerrain.setBlockArea(new Vector3Int(0, 0, 0), new Vector3Int(64, 1, 64), Block_Stone.class);
        blockTerrain.setBlocksFromHeightmap(new Vector3Int(0, 1, 0), "Textures/cubes/heightmap.jpg", 20, Block_Grass.class);
        Node terrainNode = new Node();
        terrainNode.addControl(blockTerrain);
        rootNode.attachChild(terrainNode);

        cam.setLocation(new Vector3f(-3.0F, 88.0F, 300.0F));
        cam.lookAtDirection(new Vector3f(0.44F, -0.35F, -0.83F), Vector3f.UNIT_Y);
        flyCam.setMoveSpeed(300.0F);
    }
}