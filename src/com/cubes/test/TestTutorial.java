package com.cubes.test;

import com.cubes.BlockTerrainControl;
import com.cubes.Vector3Int;
import com.cubes.test.blocks.Block_Grass;
import com.cubes.test.blocks.Block_Stone;
import com.cubes.test.blocks.Block_Wood;
import com.jme3.app.SimpleApplication;
import com.jme3.input.FlyByCamera;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestTutorial extends SimpleApplication
{
  public static void main(String[] args)
  {
    Logger.getLogger("").setLevel(Level.SEVERE);
    TestTutorial app = new TestTutorial();
    app.start();
  }

  public TestTutorial() {
    settings = new AppSettings(true);
    settings.setWidth(1280);
    settings.setHeight(720);
    settings.setTitle("Cubes Demo - Tutorial");
  }

  public void simpleInitApp()
  {
    CubesTestAssets.registerBlocks();

    BlockTerrainControl blockTerrain = new BlockTerrainControl(CubesTestAssets.getSettings(this), new Vector3Int(1, 1, 1));

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
    rootNode.attachChild(terrainNode);

    cam.setLocation(new Vector3f(-10.0F, 10.0F, 16.0F));
    cam.lookAtDirection(new Vector3f(1.0F, -0.56F, -1.0F), Vector3f.UNIT_Y);
    flyCam.setMoveSpeed(50.0F);
  }
}

/* Location:           C:\Users\Terry\AppData\Roaming\.jmonkeyplatform\3.0RC2\libs\Cubes.jar
 * Qualified Name:     com.cubes.test.TestTutorial
 * JD-Core Version:    0.6.0
 */