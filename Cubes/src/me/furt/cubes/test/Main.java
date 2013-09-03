package me.furt.cubes.test;

import com.cubes.BlockTerrainControl;
import com.cubes.CubesSettings;
import com.cubes.Vector3Int;
import com.cubes.test.CubesTestAssets;
import com.cubes.test.blocks.Block_Grass;
import com.cubes.test.blocks.Block_Stone;
import com.cubes.test.blocks.Block_Wood;
import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;

/**
 * test
 *
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    BlockTerrainControl blockTerrain;
    CubesSettings cubeSettings;

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        CubesTestAssets.registerBlocks();
        cubeSettings = CubesTestAssets.getSettings(this);
        blockTerrain = new BlockTerrainControl(cubeSettings, new Vector3Int(1, 1, 1));

        blockTerrain.setBlock(new Vector3Int(0, 0, 0), Block_Wood.class);
        blockTerrain.setBlock(new Vector3Int(0, 0, 1), Block_Wood.class);
        blockTerrain.setBlock(new Vector3Int(1, 0, 0), Block_Wood.class);
        blockTerrain.setBlock(new Vector3Int(1, 0, 1), Block_Stone.class);
        blockTerrain.setBlock(0, 0, 0, Block_Grass.class);

        blockTerrain.setBlockArea(new Vector3Int(1, 1, 1), new Vector3Int(1, 3, 1), Block_Stone.class);

        blockTerrain.removeBlock(new Vector3Int(1, 2, 1));
        blockTerrain.removeBlock(new Vector3Int(1, 3, 1));

        Node terrainNode = new Node();
        terrainNode.addControl(blockTerrain);
        this.rootNode.attachChild(terrainNode);

        this.cam.setLocation(new Vector3f(-10.0F, 10.0F, 16.0F));
        this.cam.lookAtDirection(new Vector3f(1.0F, -0.56F, -1.0F), Vector3f.UNIT_Y);
        this.flyCam.setMoveSpeed(50.0F);
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    public void updatePhysics() {
        blockTerrain.updateSpatial();
        //blockTerrain.removeControl(RigidBodyControl.class);
        //blockTerrain.addControl(new RigidBodyControl(0));

    }

    public int getSafeHeight(int x, int z) {
        int height = cubeSettings.getChunkSizeY();
        for (int i = cubeSettings.getChunkSizeY(); i > 0; i--) {
            if (blockTerrain.getBlock(new Vector3Int(x, i, z)) == null) {
                height--;
            }
        }
        return height;
    }
}
