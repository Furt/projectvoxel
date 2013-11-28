package me.furt.projectv;

import com.cubes.Block;
import com.cubes.BlockNavigator;
import com.cubes.ChunkControl;
import com.cubes.ChunkListener;
import com.cubes.CubesSettings;
import com.cubes.TerrainControl;
import com.cubes.Vector3Int;
import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.MeshCollisionShape;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapText;
import com.jme3.input.ChaseCamera;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.DirectionalLight;
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
import com.jme3.scene.shape.Sphere;
import com.jme3.system.AppSettings;
import com.jme3.texture.Texture;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import me.furt.projectv.block.*;
import org.lwjgl.input.Keyboard;
import tonegod.gui.controls.extras.ChatBoxExt;
import tonegod.gui.core.Screen;
import tonegod.skydome.FogFilter;

public class CubesNoise extends SimpleApplication implements ActionListener, AnimEventListener {

    private BulletAppState bulletAppState;
    private CharacterControl character;
    private Node model;
    private Vector3f walkDirection = new Vector3f();
    private AnimChannel animationChannel;
    private AnimControl animationControl;
    private float airTime = 0;
    private boolean left = false, right = false, up = false, down = false;
    private ChaseCamera chaseCam;
    private Sphere bullet;
    private SphereCollisionShape bulletCollisionShape;
    private TerrainControl blockTerrain;
    private Node terrainNode;
    private int itemInHand;
    private BitmapText playerLoc;
    private BitmapText blockLoc;
    private BitmapText chunkLoc;
    private BitmapText blockSelected;
    private CubesSettings cubeSettings;
    private BitmapText chunkStatus;
    private BitmapText playerDir;
    private Screen screen;
    private ChatBoxExt chatbox;
    private int seed;

    public static void main(String[] args) {
        Logger.getLogger("").setLevel(Level.SEVERE);
        CubesNoise app = new CubesNoise();
        app.start();
    }

    public CubesNoise() {
        settings = new AppSettings(true);
        settings.setWidth(1280);
        settings.setHeight(720);
        settings.setTitle("ProjectV Demo - LibNoise");
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
        bulletAppState = new BulletAppState();
        bulletAppState.setThreadingType(BulletAppState.ThreadingType.PARALLEL);
        stateManager.attach(bulletAppState);
        //stateManager.detach( stateManager.getState(FlyCamAppState.class));

        setupControls();
        setupChatBox();
        //setupFog();
        createLight();

        seed = new Random().nextInt();
        WorldSettings.registerBlocks();
        WorldSettings.initializeEnvironment(this);
        cubeSettings = WorldSettings.getSettings(this);
        setupBlockTerrain();

        setupPlayer();
        setupChaseCamera();
        setupAnimationController();
        setupGUI();
    }

     @Override
    public void simpleUpdate(float tpf) {
        if(character.getPhysicsLocation().getY() < -10.0f) {
            character.setPhysicsLocation(new Vector3f(151f, 67f, 51f));
        }
        // Player updates
        Vector3f camDir = cam.getDirection().clone().multLocal(0.1f);
        Vector3f camLeft = cam.getLeft().clone().multLocal(0.1f);
        camDir.y = 0;
        camLeft.y = 0;
        walkDirection.set(0, 0, 0);
        if (left) {
            walkDirection.addLocal(camLeft);
        }
        if (right) {
            walkDirection.addLocal(camLeft.negate());
        }
        if (up) {
            walkDirection.addLocal(camDir);
        }
        if (down) {
            walkDirection.addLocal(camDir.negate());
        }
        if (!character.onGround()) {
            airTime = airTime + tpf;
        } else {
            airTime = 0;
        }
        if (walkDirection.length() == 0) {
            if (!"stand".equals(animationChannel.getAnimationName())) {
                animationChannel.setAnim("stand", 1f);
            }
        } else {
            character.setViewDirection(walkDirection);
            if (airTime > .3f) {
                if (!"stand".equals(animationChannel.getAnimationName())) {
                    animationChannel.setAnim("stand");
                }
            } else if (!"Walk".equals(animationChannel.getAnimationName())) {
                animationChannel.setSpeed(5f);
                animationChannel.setAnim("Walk", 0.7f);
                
            }
        }
        character.setWalkDirection(walkDirection);

        // GUI updates
        Vector3f loc = cam.getLocation();
        Vector3f dir = cam.getDirection();

        Vector3Int block = getBlockLoc(loc);
        Vector3Int chunk = getChunkLoc(block);

        chunkLoc.setText("Chunk     : X= " + chunk.getX() + ", Y= " + chunk.getY() + ", Z= " + chunk.getZ());
        blockLoc.setText("Block      : X= " + block.getX() + ", Y= " + block.getY() + ", Z= " + block.getZ());
        playerLoc.setText("Player    : X= " + String.format("%.3f", loc.getX()) + ", Y= " + String.format("%.3f", loc.getY()) + ", Z= " + String.format("%.3f", loc.getZ()));
        playerDir.setText("Direction : X= " + String.format("%.4f", dir.getX()) + ", Y= " + String.format("%.4f", dir.getY()) + ", Z= " + String.format("%.4f", dir.getZ()));

        blockSelected.setText("Selected Block: " + getBlockName());
    }

    private Vector3Int getChunkLoc(Vector3Int v) {
        return new Vector3Int(v.getX() / cubeSettings.getChunkSizeX(), 0, v.getZ() / cubeSettings.getChunkSizeZ());
    }

    private Vector3Int getBlockLoc(Vector3f vec) {
        return new Vector3Int((int) Math.ceil(vec.getX()) / (int) cubeSettings.getBlockSize(),
                (int) Math.ceil(vec.getY()) / (int) cubeSettings.getBlockSize(),
                (int) Math.ceil(vec.getZ()) / (int) cubeSettings.getBlockSize());
    }

    private void setupChatBox() {
        screen = new Screen(this, "tonegod/gui/style/def/style_map.xml");
        chatbox = new ChatBoxExt(screen, new Vector2f(30, 30)) {
            @Override
            public void onSendMsg(Object command, String msg) {
                chatbox.receiveMsg(command, "<player> " + msg);
            }
        };
        chatbox.setIsMovable(true);
        chatbox.addChatChannel("general", "General", "general", "", ColorRGBA.White, true);
        chatbox.addChatChannel("trade", "Trade", "trade", "", ColorRGBA.Yellow, true);

        chatbox.setSendKey(Keyboard.KEY_RETURN);
        chatbox.showSendButton(true);

        screen.addElement(chatbox);
        guiNode.addControl(screen);

        chatbox.setIsVisible(false);
    }

    private void generateChunk(Vector3Int loc, boolean fromDist) {
        int x = 0;
        int z = 0;
        int los = 6;
        int pass = 5;
        int maxBlockHeight = 5;
        float roughness = 0.3f;

        if (loc != null) {
            loc = getBlockFromChunk(loc);
        } else {
            loc = getBlockFromCam(cam.getLocation());
        }
        x = Math.round(loc.getX() / cubeSettings.getChunkSizeX());
        z = Math.round(loc.getZ() / cubeSettings.getChunkSizeZ());
        if (!fromDist) {
            x = x * cubeSettings.getChunkSizeX();
            z = z * cubeSettings.getChunkSizeZ();
        } else {
            // this needs alot more work, which is the reason for no auto gen yet.
            x = (x * cubeSettings.getChunkSizeX() + los);
            z = (z * cubeSettings.getChunkSizeZ()) + los;
        }

        for (int i = 0; i < pass; i++) {
            blockTerrain.setBlocksFromNoise(new Vector3Int(x, 0, z), new Vector3Int(cubeSettings.getChunkSizeX(), maxBlockHeight, cubeSettings.getChunkSizeZ()), roughness, Block_Grass.class);
        }
    }

    private Vector3Int getBlockFromCam(Vector3f cam) {
        int x = (int) Math.ceil(cam.getX()) / (int) cubeSettings.getBlockSize();
        int z = (int) Math.ceil(cam.getZ()) / (int) cubeSettings.getBlockSize();

        return new Vector3Int(x, 0, z);
    }

    private Vector3Int getBlockFromChunk(Vector3Int chunk) {
        int x = chunk.getX() * (int) cubeSettings.getChunkSizeX();
        int z = chunk.getZ() * (int) cubeSettings.getChunkSizeZ();
        return new Vector3Int(x, 0, z);
    }

    private void setupBlockTerrain() {
        blockTerrain = new TerrainControl(cubeSettings, new Vector3Int(25, 1, 25));
        blockTerrain.setBlocksFromLibNoise(new Vector3Int(0, 0, 0));
        blockTerrain.addChunkListener(new ChunkListener() {
            @Override
            public void onSpatialUpdated(ChunkControl blockChunk) {
                Geometry optimizedGeometry = blockChunk.getOptimizedGeometry_Opaque();
                RigidBodyControl rigidBodyControl = optimizedGeometry.getControl(RigidBodyControl.class);
                if (rigidBodyControl == null) {
                    rigidBodyControl = new RigidBodyControl(0);
                    optimizedGeometry.addControl(rigidBodyControl);
                    bulletAppState.getPhysicsSpace().add(rigidBodyControl);
                }
                rigidBodyControl.setCollisionShape(new MeshCollisionShape(optimizedGeometry.getMesh()));
            }
        });
        terrainNode = new Node("Terrain");
        terrainNode.addControl(blockTerrain);
        terrainNode.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        rootNode.attachChild(terrainNode);
    }

    private void createLight() {
        Vector3f direction = new Vector3f(-0.1f, -0.7f, -1).normalizeLocal();
        DirectionalLight dl = new DirectionalLight();
        dl.setDirection(direction);
        dl.setColor(new ColorRGBA(1f, 1f, 1f, 1.0f));
        rootNode.addLight(dl);
    }

    // TODO
    private void setupPlayer() {
        itemInHand = 0;
        CapsuleCollisionShape capsule = new CapsuleCollisionShape(0.5f, 1f);
        character = new CharacterControl(capsule, 0.01f);
        character.setGravity(20f);
        character.setJumpSpeed(8f);
        character.setFallSpeed(14f);
        model = (Node) assetManager.loadModel("Textures/Oto/Oto.mesh.xml");
        model.setLocalScale(0.20f);
        model.addControl(character);
        character.setPhysicsLocation(new Vector3f(151f, 67f, 51f));
        rootNode.attachChild(model);
        getPhysicsSpace().add(character);

        //cam.setLocation(new Vector3f(67.638f, 349.542f, 145.545f));
        //cam.lookAtDirection(new Vector3f(0.8006f, -0.4007f, 0.4455f), Vector3f.UNIT_Y);
        //flyCam.setMoveSpeed(75);
    }

    private void setupChaseCamera() {
        flyCam.setEnabled(false);
        chaseCam = new ChaseCamera(cam, model, inputManager);
        chaseCam.setSmoothMotion(true);
        chaseCam.setDragToRotate(false);
    }

    private void setupAnimationController() {
        animationControl = model.getControl(AnimControl.class);
        animationControl.addListener(this);
        animationChannel = animationControl.createChannel();
    }

    private void setupFog() {
        /**
         * Add fog to a scene
         */
        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        FogFilter fog = new FogFilter();
        fog.setFogColor(new ColorRGBA(0.9f, 0.9f, 0.9f, 1.0f));
        fog.setFogStartDistance(550);
        fog.setFogDensity(0.1f);
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

    private PhysicsSpace getPhysicsSpace() {
        return bulletAppState.getPhysicsSpace();
    }

    private void setupControls() {
        //Player movement
        inputManager.addMapping("player_jump", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(this, "player_jump");
        inputManager.addMapping("player_strafe_left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addListener(this, "player_strafe_left");
        inputManager.addMapping("player_strafe_right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addListener(this, "player_strafe_right");
        inputManager.addMapping("player_walk_forward", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addListener(this, "player_walk_forward");
        inputManager.addMapping("player_walk_backward", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addListener(this, "player_walk_backward");

        //Player actions
        inputManager.addMapping("set_block", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(this, "set_block");
        inputManager.addMapping("remove_block", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        inputManager.addListener(this, "remove_block");
        inputManager.addMapping("block_plus", new MouseAxisTrigger(MouseInput.AXIS_WHEEL, false));
        inputManager.addListener(this, "block_plus");
        inputManager.addMapping("block_minus", new MouseAxisTrigger(MouseInput.AXIS_WHEEL, true));
        inputManager.addListener(this, "block_minus");
        inputManager.addMapping("toggle_chat", new KeyTrigger(KeyInput.KEY_T));
        inputManager.addListener(this, "toggle_chat");
        inputManager.addMapping("chunk_fill", new KeyTrigger(KeyInput.KEY_F));
        inputManager.addListener(this, "chunk_fill");
    }

    private void setupGUI() {
        Vector3f loc = cam.getLocation();
        Vector3f dir = cam.getDirection();

        Vector3Int block = getBlockLoc(loc);
        Vector3Int chunk = getChunkLoc(block);

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
        /*
         chunkStatus = new BitmapText(guiFont, false);
         chunkStatus.setSize(guiFont.getCharSet().getRenderedSize());
         if (!chunkExists(block)) {
         chunkStatus.setText("Chunk is unloaded!");
         } else {
         chunkStatus.setText("Chunk is loaded!");
         }
         chunkStatus.setLocalTranslation(800, chunkStatus.getLineHeight() * 2, 0);
         guiNode.attachChild(chunkStatus);
         */
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
        } else if (action.equals("toggle_chat") && value) {
            toggleChat();
        } else if (action.equals("chunk_fill") && value) {
            generateChunk(null, false);
        } else if (action.equals("player_strafe_left")) {
            if (value) {
                left = true;
            } else {
                left = false;
            }
        } else if (action.equals("player_strafe_right")) {
            if (value) {
                right = true;
            } else {
                right = false;
            }
        } else if (action.equals("player_walk_forward")) {
            if (value) {
                up = true;
            } else {
                up = false;
            }
        } else if (action.equals("player_walk_backward")) {
            if (value) {
                down = true;
            } else {
                down = false;
            }
        } else if (action.equals("player_jump")) {
            character.jump();
        }

    }

    private void toggleChat() {
        if (chatbox.getIsVisible()) {
            chatbox.setIsVisible(false);
            inputManager.setCursorVisible(false);
        } else {
            chatbox.setIsVisible(true);
            inputManager.setCursorVisible(true);
        }
    }

    private Vector3Int getCurrentPointedBlockLocation(boolean getNeighborLocation) {
        CollisionResults results = getRayCastingResults(terrainNode);
        if (results.size() > 0) {
            Vector3f collisionContactPoint = results.getClosestCollision().getContactPoint();
            Vector3Int vec = BlockNavigator.getPointedBlockLocation(blockTerrain, collisionContactPoint, getNeighborLocation);
            System.out.println("Placed block at: " + vec.toString());
            return vec;
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

    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
    }

    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
    }
}
