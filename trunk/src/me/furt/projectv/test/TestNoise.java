package me.furt.projectv.test;

import com.cubes.BlockTerrainControl;
import com.cubes.Vector3Int;
import com.cubes.test.CubesTestAssets;
import com.cubes.test.blocks.Block_Grass;
import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestNoise extends SimpleApplication {

    public static void main(String[] args) {
        Logger.getLogger("").setLevel(Level.SEVERE);
        TestNoise app = new TestNoise();
        app.start();
    }

    public TestNoise() {
        settings = new AppSettings(true);
        settings.setWidth(1280);
        settings.setHeight(720);
        settings.setTitle("Cubes Demo - Noise");
    }

    public void simpleInitApp() {
        CubesTestAssets.registerBlocks();

        BlockTerrainControl blockTerrain = new BlockTerrainControl(CubesTestAssets.getSettings(this), new Vector3Int(4, 1, 4));
        blockTerrain.setBlocksFromNoise(new Vector3Int(0, 0, 0), new Vector3Int(64, 30, 64), 0.3F, Block_Grass.class);
        Node terrainNode = new Node();
        terrainNode.addControl(blockTerrain);
        rootNode.attachChild(terrainNode);

        cam.setLocation(new Vector3f(-64.0F, 187.0F, -55.0F));
        cam.lookAtDirection(new Vector3f(0.64F, -0.45F, 0.6F), Vector3f.UNIT_Y);
        flyCam.setMoveSpeed(200.0F);
    }
}