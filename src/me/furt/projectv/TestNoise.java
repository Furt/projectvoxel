package me.furt.projectv;

import com.cubes.Block;
import com.cubes.BlockNavigator;
import com.cubes.CubesSettings;
import com.cubes.TerrainControl;
import com.cubes.Vector3Int;
import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapText;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
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
import tonegod.skydome.FogFilter;

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
    private BitmapText blockSelected;
    private CubesSettings cubeSettings;
    private BitmapText chunkStatus;
    private BitmapText playerDir;

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
        cubeSettings = WorldSettings.getSettings(this);
        initBlockTerrain();
        initControls();
        initPlayer();
        initGUI();
    }

    @Override
    public void simpleUpdate(float tpf) {
        Vector3f loc = cam.getLocation();
        Vector3f dir = cam.getDirection();
        
        Vector3Int block = new Vector3Int(getBlockLoc(loc.getX()), getBlockLoc(loc.getY()), getBlockLoc(loc.getZ()));
        Vector3Int chunk = new Vector3Int(block.getX()/16, block.getY()/128, block.getZ()/16);
        
        chunkLoc.setText("Chunk     : X= " + chunk.getX() + ", Y= " + chunk.getY() + ", Z= " + chunk.getZ());
        blockLoc.setText("Block      : X= " + block.getX() + ", Y= " + block.getY() + ", Z= " + block.getZ());
        playerLoc.setText("Player    : X= " + String.format("%.3f", loc.getX()) + ", Y= " + String.format("%.3f", loc.getY()) + ", Z= " + String.format("%.3f", loc.getZ()));
        playerDir.setText("Direction : X= " + String.format("%.4f", dir.getX()) + ", Y= " + String.format("%.4f", dir.getY()) + ", Z= " + String.format("%.4f", dir.getZ()));
        
        blockSelected.setText("Selected Block: " + getBlockName());
        if(!chunkExists(startLocForChunk(block))) {
            chunkStatus.setText("Chunk is unloaded!");
        } else {
            chunkStatus.setText("Chunk is loaded!");
        }
        
        //blockTerrain.setBlocksFromNoise(new Vector3Int(0, 0, 0), new Vector3Int(16, 5, 16), 0.1f, Block_Grass.class);
    }

    public boolean chunkExists(Vector3Int block) {
        if (blockTerrain.getBlock(block) != null) {
            return true;
        }
        return false;
    }

    public Vector3Int startLocForChunk(Vector3Int chunk) {
        return new Vector3Int(chunk.getX()*4, 0, chunk.getZ()*4);
    }

    public int getBlockLoc(float f) {
        return (int) Math.ceil(f / cubeSettings.getBlockSize());
    }

    private void initBlockTerrain() {
        blockTerrain = new TerrainControl(cubeSettings, new Vector3Int(100, 1, 100));
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
        cam.setLocation(new Vector3f(40.6f, 79.66f, 30.64f));
        cam.lookAtDirection(new Vector3f(0.26f, -0.83f, 0.49f), Vector3f.UNIT_Y);
        flyCam.setMoveSpeed(80);
    }

    private void initFog() {
        /**
         * Add fog to a scene
         */
        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        FogFilter fog = new FogFilter();
        fog.setFogColor(new ColorRGBA(0.9f, 0.9f, 0.9f, 1.0f));
        fog.setFogStartDistance(450);
        fog.setFogDensity(0.5f);
        fpp.addFilter(fog);
        viewPort.addProcessor(fpp);

    }

    /*
     * chunkRadius "the radius of a player that should have chunks loaded"
     * maxChunks "the amount of chunks that can be loaded at once"
     * seed "a predetermined long for noise"
     * 
     * to get chunk x z you must always round up
     * (int)math ceil(cam/blockSize/chunkSize)
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
        inputManager.addMapping("block_plus", new MouseAxisTrigger(MouseInput.AXIS_WHEEL, false));
        inputManager.addListener(this, "block_plus");
        inputManager.addMapping("block_minus", new MouseAxisTrigger(MouseInput.AXIS_WHEEL, true));
        inputManager.addListener(this, "block_minus");
    }

    private void initGUI() {
        Vector3f loc = cam.getLocation();
        Vector3f dir = cam.getDirection();
        
        Vector3Int block = new Vector3Int(getBlockLoc(loc.getX()), getBlockLoc(loc.getY()), getBlockLoc(loc.getZ()));
        Vector3Int chunk = new Vector3Int(block.getX()/cubeSettings.getChunkSizeX(), block.getY()/cubeSettings.getChunkSizeY(), block.getZ()/cubeSettings.getChunkSizeZ());

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
        playerLoc.setText("Player : X= " + String.format("%.3f", loc.getX()) + ", Y= " + String.format("%.3f", loc.getY()) + ", Z= " + String.format("%.3f", loc.getZ()));
        playerLoc.setLocalTranslation(300, playerLoc.getLineHeight() * 2, 0);
        guiNode.attachChild(playerLoc);
        
        //Camera Direction
        playerDir = new BitmapText(guiFont, false);
        playerDir.setSize(guiFont.getCharSet().getRenderedSize());
        playerDir.setText("Direction : X= " + String.format("%.4f", dir.getX()) + ", Y= " + String.format("%.4f", dir.getY()) + ", Z= " + String.format("%.4f", dir.getZ()));
        playerDir.setLocalTranslation(300, playerDir.getLineHeight(), 0);
        guiNode.attachChild(playerDir);
        
        //Block Location
        blockLoc = new BitmapText(guiFont, false);
        blockLoc.setSize(guiFont.getCharSet().getRenderedSize());
        blockLoc.setText("Block   : X= " + block.getX() + ", Y= " + block.getY() + ", Z= " + block.getZ());
        blockLoc.setLocalTranslation(300, blockLoc.getLineHeight() * 3, 0);
        guiNode.attachChild(blockLoc);

        //Chunk Location
        chunkLoc = new BitmapText(guiFont, false);
        chunkLoc.setSize(guiFont.getCharSet().getRenderedSize());
        chunkLoc.setText("Chunk  : X= " + chunk.getX() + ", Y= " + chunk.getY() + ", Z= " + chunk.getZ());
        chunkLoc.setLocalTranslation(300, chunkLoc.getLineHeight() * 4, 0);
        guiNode.attachChild(chunkLoc);
        
        chunkStatus = new BitmapText(guiFont, false);
        chunkStatus.setSize(guiFont.getCharSet().getRenderedSize());
        if(!chunkExists(startLocForChunk(block))) {
            chunkStatus.setText("Chunk is unloaded!");
        } else {
            chunkStatus.setText("Chunk is loaded!");
        }
        chunkStatus.setLocalTranslation(800, chunkStatus.getLineHeight() * 2, 0);
        guiNode.attachChild(chunkStatus);

        //Block Selected
        blockSelected = new BitmapText(guiFont, false);
        blockSelected.setSize(guiFont.getCharSet().getRenderedSize());
        blockSelected.setText("Selected Block: " + getBlockName());
        blockSelected.setLocalTranslation(800, blockSelected.getLineHeight(), 0);
        guiNode.attachChild(blockSelected);
    }

    @Override
    public void onAction(String action, boolean value, float lastTimePerFrame) {
        int temp = 15;
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
        } else if (action.equals("block_plus") && value) {
            if (itemInHand == temp) {
                itemInHand = 0;
            } else {
                itemInHand++;
            }
        } else if (action.equals("block_minus") && value) {
            if (itemInHand == 0) {
                itemInHand = temp;
            } else {
                itemInHand--;
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

    public String getBlockName() {
        switch (itemInHand) {
            case 0:
                return "CobbleStone";

            case 1:
                return "Dirt";

            case 2:
                return "Glass";

            case 3:
                return "Grass";

            case 4:
                return "Gravel";

            case 5:
                return "Ice";

            case 6:
                return "Lava";

            case 7:
                return "Leaves";

            case 8:
                return "Log";

            case 9:
                return "Mud";

            case 10:
                return "Plank";

            case 11:
                return "Red Clay";

            case 12:
                return "Sand";

            case 13:
                return "Stone";

            case 14:
                return "Stone Slab";

            case 15:
                return "Water";

            default:
                return "Grass";
        }
    }

    public Vector3f getCameraView() {
        return cam.getDirection();
    }

    public Vector3f getCameraLoc() {
        return cam.getLocation();
    }
}
