package me.furt.projectv.test;

import com.cubes.BlockNavigator;
import com.cubes.BlockTerrainControl;
import com.cubes.Vector3Int;
import me.furt.projectv.test.blocks.Block_Grass;
import me.furt.projectv.test.blocks.Block_Stone;
import me.furt.projectv.test.blocks.Block_Wood;
import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapCharacterSet;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.controls.Trigger;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestPicking extends SimpleApplication
  implements ActionListener
{
  private Node terrainNode;
  private BlockTerrainControl blockTerrain;

  public static void main(String[] args)
  {
    Logger.getLogger("").setLevel(Level.SEVERE);
    TestPicking app = new TestPicking();
    app.start();
  }

  public TestPicking() {
    settings = new AppSettings(true);
    settings.setWidth(1280);
    settings.setHeight(720);
    settings.setTitle("Cubes Demo - Picking");
  }

  public void simpleInitApp()
  {
    CubesTestAssets.registerBlocks();
    initControls();
    initBlockTerrain();
    initGUI();
    cam.setLocation(new Vector3f(-16.6F, 46.0F, 97.599998F));
    cam.lookAtDirection(new Vector3f(0.68F, -0.47F, -0.56F), Vector3f.UNIT_Y);
    flyCam.setMoveSpeed(250.0F);
  }

  private void initControls() {
    inputManager.addMapping("set_block", new Trigger[] { new MouseButtonTrigger(0) });
    inputManager.addListener(this, new String[] { "set_block" });
    inputManager.addMapping("remove_block", new Trigger[] { new MouseButtonTrigger(1) });
    inputManager.addListener(this, new String[] { "remove_block" });
  }

  private void initBlockTerrain() {
    blockTerrain = new BlockTerrainControl(CubesTestAssets.getSettings(this), new Vector3Int(2, 1, 2));
    blockTerrain.setBlockArea(new Vector3Int(0, 0, 0), new Vector3Int(32, 1, 32), Block_Stone.class);
    blockTerrain.setBlocksFromNoise(new Vector3Int(0, 1, 0), new Vector3Int(32, 5, 32), 0.5F, Block_Grass.class);
    terrainNode = new Node();
    terrainNode.addControl(blockTerrain);
    rootNode.attachChild(terrainNode);
  }

  private void initGUI()
  {
    BitmapText crosshair = new BitmapText(guiFont);
    crosshair.setText("+");
    crosshair.setSize(guiFont.getCharSet().getRenderedSize() * 2);
    crosshair.setLocalTranslation(settings.getWidth() / 2 - guiFont.getCharSet().getRenderedSize() / 3 * 2, settings.getHeight() / 2 + crosshair.getLineHeight() / 2.0F, 0.0F);

    guiNode.attachChild(crosshair);

    BitmapText instructionsText1 = new BitmapText(guiFont);
    instructionsText1.setText("Left Click: Set");
    instructionsText1.setLocalTranslation(0.0F, settings.getHeight(), 0.0F);
    guiNode.attachChild(instructionsText1);
    BitmapText instructionsText2 = new BitmapText(guiFont);
    instructionsText2.setText("Right Click: Remove");
    instructionsText2.setLocalTranslation(0.0F, settings.getHeight() - instructionsText2.getLineHeight(), 0.0F);
    guiNode.attachChild(instructionsText2);
    BitmapText instructionsText3 = new BitmapText(guiFont);
    instructionsText3.setText("(Bottom layer is marked as indestructible)");
    instructionsText3.setLocalTranslation(0.0F, settings.getHeight() - 2.0F * instructionsText3.getLineHeight(), 0.0F);
    guiNode.attachChild(instructionsText3);
  }

  public void onAction(String action, boolean value, float lastTimePerFrame)
  {
    if ((action.equals("set_block")) && (value)) {
      Vector3Int blockLocation = getCurrentPointedBlockLocation(true);
      if (blockLocation != null) {
        blockTerrain.setBlock(blockLocation, Block_Wood.class);
      }
    }
    else if ((action.equals("remove_block")) && (value)) {
      Vector3Int blockLocation = getCurrentPointedBlockLocation(false);
      if ((blockLocation != null) && (blockLocation.getY() > 0))
        blockTerrain.removeBlock(blockLocation);
    }
  }

  private Vector3Int getCurrentPointedBlockLocation(boolean getNeighborLocation)
  {
    CollisionResults results = getRayCastingResults(terrainNode);
    if (results.size() > 0) {
      Vector3f collisionContactPoint = results.getClosestCollision().getContactPoint();
      return BlockNavigator.getPointedBlockLocation(blockTerrain, collisionContactPoint, getNeighborLocation);
    }
    return null;
  }

  private CollisionResults getRayCastingResults(Node node) {
    Vector3f origin = cam.getWorldCoordinates(new Vector2f(settings.getWidth() / 2, settings.getHeight() / 2), 0.0F);
    Vector3f direction = cam.getWorldCoordinates(new Vector2f(settings.getWidth() / 2, settings.getHeight() / 2), 0.3F);
    direction.subtractLocal(origin).normalizeLocal();
    Ray ray = new Ray(origin, direction);
    CollisionResults results = new CollisionResults();
    node.collideWith(ray, results);
    return results;
  }
}

/* Location:           C:\Users\Terry\AppData\Roaming\.jmonkeyplatform\3.0RC2\libs\Cubes.jar
 * Qualified Name:     com.cubes.test.TestPicking
 * JD-Core Version:    0.6.0
 */