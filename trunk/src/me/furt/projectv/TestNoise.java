package me.furt.projectv;

import com.cubes.*;
import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapText;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.furt.projectv.block.*;

public class TestNoise extends SimpleApplication implements ActionListener {

    public TerrainControl blockTerrain;
    public Node terrainNode;
    public int itemInHand;

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
        WorldSettings.registerBlocks();
        WorldSettings.initializeEnvironment(this);
        initControls();
        initBlockTerrain();
        initGUI();
        initPlayer();
    }

    @Override
    public void simpleUpdate(float tpf) {
    }

    private void initBlockTerrain() {
        blockTerrain = new TerrainControl(WorldSettings.getSettings(this), new Vector3Int(100, 1, 100));
        // setBlocksFromNoise(Vector3Int location, Vector3Int size, float roughness, Block blockClass)
        blockTerrain.setBlocksFromNoise(new Vector3Int(0, 0, 0), new Vector3Int(16, 5, 16), 0.1f, Block_Grass.class);
        blockTerrain.setBlocksFromNoise(new Vector3Int(16, 0, 0), new Vector3Int(16, 5, 16), 0.1f, Block_Grass.class);
        blockTerrain.setBlocksFromNoise(new Vector3Int(16, 0, 16), new Vector3Int(16, 5, 16), 0.1f, Block_Grass.class);
        blockTerrain.setBlocksFromNoise(new Vector3Int(0, 0, 16), new Vector3Int(16, 5, 16), 0.1f, Block_Grass.class);
        terrainNode = new Node("Terrain");
        terrainNode.addControl(blockTerrain);
        terrainNode.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        rootNode.attachChild(terrainNode);
    }

    private void initPlayer() {
        itemInHand = 0;
        cam.setLocation(new Vector3f(0, 187, 0));
        cam.lookAtDirection(new Vector3f(0.64f, 5f, 0.6f), Vector3f.UNIT_Y);
        flyCam.setMoveSpeed(200);
    }

    private void initControls() {
        inputManager.addMapping("set_block", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(this, "set_block");
        inputManager.addMapping("remove_block", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        inputManager.addListener(this, "remove_block");
        inputManager.addMapping("block_select", new MouseButtonTrigger(MouseInput.AXIS_WHEEL));
        inputManager.addListener(this, "block_select");
    }

    private void initGUI() {
        //Crosshair
        BitmapText crosshair = new BitmapText(guiFont);
        crosshair.setText("+");
        crosshair.setSize(guiFont.getCharSet().getRenderedSize() * 2);
        crosshair.setLocalTranslation(
                (settings.getWidth() / 2) - (guiFont.getCharSet().getRenderedSize() / 3 * 2),
                (settings.getHeight() / 2) + (crosshair.getLineHeight() / 2), 0);
        guiNode.attachChild(crosshair);
    }

    @Override
    public void onAction(String action, boolean value, float lastTimePerFrame) {
        if (action.equals("set_block") && value) {
            Vector3Int blockLocation = getCurrentPointedBlockLocation(true);
            if (blockLocation != null) {
                Class<? extends Block> c = getBlockSelected(itemInHand);
                blockTerrain.setBlock(blockLocation, c);
            }
        } else if (action.equals("remove_block") && value) {
            Vector3Int blockLocation = getCurrentPointedBlockLocation(false);
            if ((blockLocation != null) && (blockLocation.getY() > 0)) {
                blockTerrain.removeBlock(blockLocation);
            }
        } else if (action.equals("block_select") && value) {
            if (itemInHand >= 15) {
                itemInHand = 0;
            } else {
                itemInHand++;
            }
        }
    }

    private Vector3Int getCurrentPointedBlockLocation(boolean getNeighborLocation) {
        CollisionResults results = getRayCastingResults(terrainNode);
        if (results.size() > 0) {
            Vector3f collisionContactPoint = results.getClosestCollision().getContactPoint();
            return BlockNavigator.getPointedBlockLocation(blockTerrain, collisionContactPoint, getNeighborLocation);
        }
        return null;
    }

    private CollisionResults getRayCastingResults(Node node) {
        Vector3f origin = cam.getWorldCoordinates(new Vector2f((settings.getWidth() / 2), (settings.getHeight() / 2)), 0.0f);
        Vector3f direction = cam.getWorldCoordinates(new Vector2f((settings.getWidth() / 2), (settings.getHeight() / 2)), 0.3f);
        direction.subtractLocal(origin).normalizeLocal();
        Ray ray = new Ray(origin, direction);
        CollisionResults results = new CollisionResults();
        node.collideWith(ray, results);
        return results;
    }

    // Very quick and dirty method to select a block type
    public Class<? extends Block> getBlockSelected(int id) {
        Class<? extends Block> c;
        if (id == 0) {
            c = Block_Plank.class;
        } else if (id == 1) {
            c = Block_Cobble.class;
        } else if (id == 2) {
            c = Block_Dirt.class;
        } else if (id == 3) {
            c = Block_Glass.class;
        } else if (id == 4) {
            c = Block_Grass.class;
        } else if (id == 5) {
            c = Block_Gravel.class;
        } else if (id == 6) {
            c = Block_Ice.class;
        } else if (id == 7) {
            c = Block_Lava.class;
        } else if (id == 8) {
            c = Block_Log.class;
        } else if (id == 9) {
            c = Block_Mud.class;
        } else if (id == 10) {
            c = Block_Red_Clay.class;
        } else if (id == 11) {
            c = Block_Sand.class;
        } else if (id == 12) {
            c = Block_Stone.class;
        } else if (id == 13) {
            c = Block_Stone_Slab.class;
        } else if (id == 14) {
            c = Block_Water.class;
        } else if (id == 15) {
            c = Block_Leaves.class;
        } else {
            c = Block_Grass.class;
        }

        return c;
    }

    public Vector3f getCameraView() {
        return cam.getDirection();
    }

    public Vector3f getCameraLoc() {
        return cam.getLocation();
    }
}
