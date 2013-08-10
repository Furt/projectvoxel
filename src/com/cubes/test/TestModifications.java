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

public class TestModifications extends SimpleApplication
{
  private BlockTerrainControl blockTerrain;
  private long lastModificationTimestamp;
  private Vector3Int lastModificationLocation = new Vector3Int(0, 4, 15);

  public static void main(String[] args)
  {
    Logger.getLogger("").setLevel(Level.SEVERE);
    TestModifications app = new TestModifications();
    app.start();
  }

  public TestModifications() {
    settings = new AppSettings(true);
    settings.setWidth(1280);
    settings.setHeight(720);
    settings.setTitle("Cubes Demo - Modifications");
  }

  public void simpleInitApp()
  {
    CubesTestAssets.registerBlocks();

    blockTerrain = new BlockTerrainControl(CubesTestAssets.getSettings(this), new Vector3Int(2, 1, 2));
    for (int x = 0; x < 32; x++) {
      for (int z = 0; z < 16; z++) {
        int groundHeight = (int)(Math.random() * 4.0D + 8.0D);
        for (int y = 0; y < groundHeight; y++) {
          if ((z != 15) || (y != 4)) {
            blockTerrain.setBlock(x, y, z, Block_Stone.class);
          }
        }
        int additionalHeight = (int)(Math.random() * 4.0D);
        for (int y = 0; y < additionalHeight; y++) {
          Class blockClass = y > 0 ? Block_Grass.class : Block_Wood.class;
          blockTerrain.setBlock(x, groundHeight + y, z, blockClass);
        }
      }
    }
    Node terrainNode = new Node();
    terrainNode.addControl(blockTerrain);
    rootNode.attachChild(terrainNode);

    cam.setLocation(new Vector3f(-29.0F, 32.0F, 96.0F));
    cam.lookAtDirection(new Vector3f(0.68F, -0.175F, -0.71F), Vector3f.UNIT_Y);
    flyCam.setMoveSpeed(250.0F);
  }

  public void simpleUpdate(float lastTimePerFrame)
  {
    if (System.currentTimeMillis() - lastModificationTimestamp > 50L) {
      blockTerrain.removeBlock(lastModificationLocation);
      lastModificationLocation.addLocal(1, 0, 0);
      if (lastModificationLocation.getX() > 31) {
        lastModificationLocation.setX(0);
      }
      blockTerrain.setBlock(lastModificationLocation, Block_Grass.class);
      lastModificationTimestamp = System.currentTimeMillis();
    }
  }
}

/* Location:           C:\Users\Terry\AppData\Roaming\.jmonkeyplatform\3.0RC2\libs\Cubes.jar
 * Qualified Name:     com.cubes.test.TestModifications
 * JD-Core Version:    0.6.0
 */