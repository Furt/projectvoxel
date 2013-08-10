package com.cubes.test;

import com.cubes.BlockTerrainControl;
import com.cubes.Vector3Int;
import com.cubes.network.CubesSerializer;
import com.cubes.test.blocks.Block_Grass;
import com.jme3.app.SimpleApplication;
import com.jme3.input.FlyByCamera;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestSerialize extends SimpleApplication
{
  public static void main(String[] args)
  {
    Logger.getLogger("").setLevel(Level.SEVERE);
    TestSerialize app = new TestSerialize();
    app.start();
  }

  public TestSerialize() {
    settings = new AppSettings(true);
    settings.setWidth(1280);
    settings.setHeight(720);
    settings.setTitle("Cubes Demo - Serialize");
  }

  public void simpleInitApp()
  {
    CubesTestAssets.registerBlocks();

    BlockTerrainControl blockTerrain = new BlockTerrainControl(CubesTestAssets.getSettings(this), new Vector3Int(1, 1, 1));
    blockTerrain.setBlocksFromNoise(new Vector3Int(0, 0, 0), new Vector3Int(16, 10, 16), 0.5F, Block_Grass.class);
    Node terrainNode = new Node();
    terrainNode.addControl(blockTerrain);
    terrainNode.setLocalTranslation(40.0F, 0.0F, 0.0F);
    rootNode.attachChild(terrainNode);

    BlockTerrainControl blockTerrainClone = new BlockTerrainControl(CubesTestAssets.getSettings(this), new Vector3Int());

    byte[] serializedBlockTerrain = CubesSerializer.writeToBytes(blockTerrain);
    CubesSerializer.readFromBytes(blockTerrainClone, serializedBlockTerrain);

    Node terrainNodeClone = new Node();
    terrainNodeClone.addControl(blockTerrainClone);
    terrainNodeClone.setLocalTranslation(-40.0F, 0.0F, 0.0F);
    rootNode.attachChild(terrainNodeClone);

    cam.setLocation(new Vector3f(23.5F, 46.0F, -103.0F));
    cam.lookAtDirection(new Vector3f(0.0F, -0.25F, 1.0F), Vector3f.UNIT_Y);
    flyCam.setMoveSpeed(300.0F);
  }
}

/* Location:           C:\Users\Terry\AppData\Roaming\.jmonkeyplatform\3.0RC2\libs\Cubes.jar
 * Qualified Name:     com.cubes.test.TestSerialize
 * JD-Core Version:    0.6.0
 */