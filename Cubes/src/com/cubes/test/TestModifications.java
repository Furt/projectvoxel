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
    this.settings = new AppSettings(true);
    this.settings.setWidth(1280);
    this.settings.setHeight(720);
    this.settings.setTitle("Cubes Demo - Modifications");
  }

  public void simpleInitApp()
  {
    CubesTestAssets.registerBlocks();

    this.blockTerrain = new BlockTerrainControl(CubesTestAssets.getSettings(this), new Vector3Int(2, 1, 2));
    for (int x = 0; x < 32; x++) {
      for (int z = 0; z < 16; z++) {
        int groundHeight = (int)(Math.random() * 4.0D + 8.0D);
        for (int y = 0; y < groundHeight; y++) {
          if ((z != 15) || (y != 4)) {
            this.blockTerrain.setBlock(x, y, z, Block_Stone.class);
          }
        }
        int additionalHeight = (int)(Math.random() * 4.0D);
        for (int y = 0; y < additionalHeight; y++) {
          Class blockClass = y > 0 ? Block_Grass.class : Block_Wood.class;
          this.blockTerrain.setBlock(x, groundHeight + y, z, blockClass);
        }
      }
    }
    Node terrainNode = new Node();
    terrainNode.addControl(this.blockTerrain);
    this.rootNode.attachChild(terrainNode);

    this.cam.setLocation(new Vector3f(-29.0F, 32.0F, 96.0F));
    this.cam.lookAtDirection(new Vector3f(0.68F, -0.175F, -0.71F), Vector3f.UNIT_Y);
    this.flyCam.setMoveSpeed(250.0F);
  }

  public void simpleUpdate(float lastTimePerFrame)
  {
    if (System.currentTimeMillis() - this.lastModificationTimestamp > 50L) {
      this.blockTerrain.removeBlock(this.lastModificationLocation);
      this.lastModificationLocation.addLocal(1, 0, 0);
      if (this.lastModificationLocation.getX() > 31) {
        this.lastModificationLocation.setX(0);
      }
      this.blockTerrain.setBlock(this.lastModificationLocation, Block_Grass.class);
      this.lastModificationTimestamp = System.currentTimeMillis();
    }
  }
}