package com.cubes.test;

import com.cubes.BlockTerrainControl;
import com.cubes.Vector3Int;
import com.jme3.app.SimpleApplication;
import com.jme3.input.FlyByCamera;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestBlockModel extends SimpleApplication
{
  public static void main(String[] args)
  {
    Logger.getLogger("").setLevel(Level.SEVERE);
    TestBlockModel app = new TestBlockModel();
    app.start();
  }

  public TestBlockModel() {
    this.settings = new AppSettings(true);
    this.settings.setWidth(1280);
    this.settings.setHeight(720);
    this.settings.setTitle("Cubes Demo - BlockModel");
  }

  public void simpleInitApp()
  {
    CubesTestAssets.registerBlocks();

    BlockTerrainControl blockTerrain = new BlockTerrainControl(CubesTestAssets.getSettings(this), new Vector3Int(11, 1, 10));

    Node terrainNode = new Node();
    terrainNode.addControl(blockTerrain);
    this.rootNode.attachChild(terrainNode);

    this.cam.setLocation(new Vector3f(-3.0F, 88.0F, 100.0F));
    this.cam.lookAtDirection(new Vector3f(0.44F, -0.35F, -0.83F), Vector3f.UNIT_Y);
    this.flyCam.setMoveSpeed(300.0F);
  }
}