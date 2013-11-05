package me.furt.projectv;

import com.cubes.*;
import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.furt.projectv.block.*;

public class TestNoise extends SimpleApplication {

    public TerrainControl blockTerrain;
    
    public static void main(String[] args) {
        Logger.getLogger("").setLevel(Level.SEVERE);
        TestNoise app = new TestNoise();
        app.start();
    }

    public TestNoise() {
        settings = new AppSettings(true);
        settings.setWidth(1280);
        settings.setHeight(720);
        settings.setTitle("ProjectV Demo - Noise");
    }

    @Override
    public void simpleInitApp() {
        System.out.println(Material.COBBLESTONE.toString());
        System.out.println(Material.valueOf("COBBLESTONE").getId());
        System.out.println("There are " + Material.values().length + " blocks.");
        
        WorldSettings.registerBlocks();
        WorldSettings.initializeEnvironment(this);

        // Setup initial world generation
        blockTerrain = new TerrainControl(WorldSettings.getSettings(this), new Vector3Int(100, 1, 100));
        // setBlocksFromNoise(Vector3Int location, Vector3Int size, float roughness, Block blockClass)
        blockTerrain.setBlocksFromNoise(new Vector3Int(0, 0, 0), new Vector3Int(16, 5, 16), 0.1f, Block_Grass.class);
        blockTerrain.setBlocksFromNoise(new Vector3Int(16, 0, 0), new Vector3Int(16, 5, 16), 0.1f, Block_Grass.class);
        blockTerrain.setBlocksFromNoise(new Vector3Int(16, 0, 16), new Vector3Int(16, 5, 16), 0.1f, Block_Grass.class);
        blockTerrain.setBlocksFromNoise(new Vector3Int(0, 0, 16), new Vector3Int(16, 5, 16), 0.1f, Block_Grass.class);
        Node terrainNode = new Node();
        terrainNode.addControl(blockTerrain);
        terrainNode.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        rootNode.attachChild(terrainNode);

        // Setup camera
        cam.setLocation(new Vector3f(0, 187, 0));
        cam.lookAtDirection(new Vector3f(0.64f, 5f, 0.6f), Vector3f.UNIT_Y);
        flyCam.setMoveSpeed(200);
        
        
    }
    
    public Vector3f getCameraView() {
       return cam.getDirection();
    }
    
    public Vector3f getCameraLoc() {
        return cam.getLocation();
    }
    
    
    
    @Override
    public void simpleUpdate(float tpf) {
        
    }
}
