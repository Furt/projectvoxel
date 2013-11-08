package me.furt.projectv;

import com.cubes.Block;
import com.cubes.BlockManager;
import com.cubes.BlockNavigator;
import com.cubes.TerrainControl;
import com.cubes.Vector3Int;
import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapText;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import com.jme3.texture.Texture;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import me.furt.projectv.block.*;

public class TestNoise extends SimpleApplication implements ActionListener {

    public TerrainControl blockTerrain;
    public Node terrainNode;
    public int itemInHand;
    public BitmapText playerLoc;

    public static void main(String[] args) {
        Logger.getLogger("").setLevel(Level.SEVERE);
        TestNoise app = new TestNoise();
        app.start();
    }
    private BitmapText blockLoc;
    private BitmapText chunkLoc;

    public TestNoise() {
        settings = new AppSettings(true);
        settings.setWidth(1280);
        settings.setHeight(720);
        settings.setTitle("ProjectV Demo - Noise");
        settings.setSettingsDialogImage("/Interface/pv-splash.png");
        try {
            settings.setIcons(new BufferedImage[]{
                        ImageIO.read(getClass().getResourceAsStream("/Interface/magex16.png")),
                        ImageIO.read(getClass().getResourceAsStream("/Interface/magex32.png"))
                    });
        } catch (IOException e) {
        }
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
        chunkLoc.setText("Chunk   Location: X= " + cam.getLocation().getX()/4/16 + ", Y= " + cam.getLocation().getY()/4/16  + ", Z= " + cam.getLocation().getZ()/4/16);
        blockLoc.setText("Block   Location: X= " + cam.getLocation().getX()/4 + ", Y= " + cam.getLocation().getY()/4  + ", Z= " + cam.getLocation().getZ()/4);
        playerLoc.setText("Player  Location: X= " + cam.getLocation().getX() + ", Y= " + cam.getLocation().getY()  + ", Z= " + cam.getLocation().getZ());
//        if (this.screen == null) {
//            return;
//        }
//
//        int percentage = Progress.getTotalPercent();
//        String message = Progress.getLastMessage();
//        if ((this.lastPercent != percentage) || (!ObjectUtils.areEqual(message, this.lastMessage))) {
//            this.lastPercent = percentage;
//            this.lastMessage = message;
//            Label progress = (Label) this.screen.findNiftyControl("progress", Label.class);
//
//            if (percentage > 0) {
//                String text = percentage + " %";
//                progress.setText(text);
//                if (percentage == 100) {
//                    startGame();
//                }
//            } else {
//                progress.setText("");
//            }
//
//            Label status = (Label) this.screen.findNiftyControl("status", Label.class);
//            if (message != null) {
//                status.setText(message);
//            } else {
//                status.setText("");
//            }
//        }
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

    /*
     * chunkRadius "the radius of a player that should have chunks loaded"
     * maxChunks "the amount of chunks that can be loaded at once"
     * seed "a predetermined long for noise"
     * 
     * to get chunk x z you must always round up
     * (int)roundUp(cam/blockSize/chunkSize)
     * 
     */
    public void createSelectedCube() {
        /**
         * An unshaded textured cube. Uses texture from jme3-test-data library!
         */
        Box boxshape1 = new Box(Vector3f.ZERO, 1f, 1f, 1f);
        Geometry cube_tex = new Geometry("A Textured Box", boxshape1);
        Material mat_tex = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture tex = assetManager.loadTexture("Interface/Logo/Monkey.jpg");
        mat_tex.setTexture("ColorMap", tex);
        cube_tex.setMaterial(mat_tex);
        guiNode.attachChild(cube_tex);
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
        
        //Camera Location
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        playerLoc = new BitmapText(guiFont, false);
        playerLoc.setSize(guiFont.getCharSet().getRenderedSize());
        playerLoc.setText("Player Location: X= " + cam.getLocation().getX() + ", Y= " + cam.getLocation().getY()  + ", Z= " + cam.getLocation().getZ());
        playerLoc.setLocalTranslation(300, playerLoc.getLineHeight(), 0);
        guiNode.attachChild(playerLoc);
        
        //Block Location
        blockLoc = new BitmapText(guiFont, false);
        blockLoc.setSize(guiFont.getCharSet().getRenderedSize());
        blockLoc.setText("Block   Location: X= " + cam.getLocation().getX()/4 + ", Y= " + cam.getLocation().getY()/4  + ", Z= " + cam.getLocation().getZ()/4);
        blockLoc.setLocalTranslation(300, blockLoc.getLineHeight()*2, 0);
        guiNode.attachChild(blockLoc);
        
        //Chunk Location
        chunkLoc = new BitmapText(guiFont, false);
        chunkLoc.setSize(guiFont.getCharSet().getRenderedSize());
        chunkLoc.setText("Chunk   Location: X= " + cam.getLocation().getX()/4/16 + ", Y= " + cam.getLocation().getY()/4/16  + ", Z= " + cam.getLocation().getZ()/4/16);
        chunkLoc.setLocalTranslation(300, chunkLoc.getLineHeight()*3, 0);
        guiNode.attachChild(chunkLoc);
    }

    @Override
    public void onAction(String action, boolean value, float lastTimePerFrame) {
        if (action.equals("set_block") && value) {
            Vector3Int blockLocation = getCurrentPointedBlockLocation(true);
            if (blockLocation != null) {
                Class<? extends Block> c = getBlockSelected();
                blockTerrain.setBlock(blockLocation, c);
            }
        } else if (action.equals("remove_block") && value) {
            Vector3Int blockLocation = getCurrentPointedBlockLocation(false);
            if ((blockLocation != null) && (blockLocation.getY() > 0)) {
                blockTerrain.removeBlock(blockLocation);
            }
        } else if (action.equals("block_select") && value) {

            int temp = BlockManager.getTypes().length;
            if (itemInHand >= temp) {
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

    public Class<? extends Block> getBlockSelected() {
        switch (itemInHand) {
            case 0:
                return Block_Cobble.class;

            case 1:
                return Block_Dirt.class;

            case 2:
                return Block_Glass.class;

            case 3:
                return Block_Grass.class;

            case 4:
                return Block_Gravel.class;

            case 5:
                return Block_Ice.class;

            case 6:
                return Block_Lava.class;

            case 7:
                return Block_Leaves.class;

            case 8:
                return Block_Log.class;

            case 9:
                return Block_Mud.class;

            case 10:
                return Block_Plank.class;

            case 11:
                return Block_Red_Clay.class;

            case 12:
                return Block_Sand.class;

            case 13:
                return Block_Stone.class;

            case 14:
                return Block_Stone_Slab.class;

            case 15:
                return Block_Water.class;

            default:
                return Block_Grass.class;
        }
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
