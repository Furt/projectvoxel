package com.cubes.test;

import com.cubes.BlockTerrainControl;
import com.cubes.Vector3Int;
import com.cubes.test.blocks.Block_Grass;
import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestAustralia extends SimpleApplication
{
  public static void main(String[] args)
  {
    Logger.getLogger("").setLevel(Level.SEVERE);
    TestAustralia app = new TestAustralia();
    app.start();
  }

  public TestAustralia() {
    settings = new AppSettings(true);
    settings.setWidth(1280);
    settings.setHeight(720);
    settings.setTitle("Cubes Demo - Heightmap (Australia)");
  }

  public void simpleInitApp()
  {
    CubesTestAssets.registerBlocks();
    CubesTestAssets.initializeEnvironment(this);
    CubesTestAssets.initializeWater(this);

    BlockTerrainControl blockTerrain = new BlockTerrainControl(CubesTestAssets.getSettings(this), new Vector3Int(7, 1, 7));
    blockTerrain.setBlocksFromHeightmap(new Vector3Int(0, 1, 0), "Textures/cubes/heightmap_australia.jpg", 10, Block_Grass.class);
    Node terrainNode = new Node();
    terrainNode.addControl(blockTerrain);
    terrainNode.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
    rootNode.attachChild(terrainNode);

    cam.setLocation(new Vector3f(32.799999F, 111.0F, 379.5F));
    cam.lookAtDirection(new Vector3f(0.44F, -0.47F, -0.77F), Vector3f.UNIT_Y);
    cam.setFrustumFar(4000.0F);
    flyCam.setMoveSpeed(300.0F);
  }
}

/* Location:           C:\Users\Terry\AppData\Roaming\.jmonkeyplatform\3.0RC2\libs\Cubes.jar
 * Qualified Name:     com.cubes.test.TestAustralia
 * JD-Core Version:    0.6.0
 */