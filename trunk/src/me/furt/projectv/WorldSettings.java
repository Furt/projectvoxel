package me.furt.projectv;

import com.cubes.Block;
import com.cubes.BlockChunkControl;
import com.cubes.BlockManager;
import com.cubes.BlockSkin;
import com.cubes.CubesSettings;
import com.cubes.TextureLocation;
import com.cubes.Vector3Int;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.SceneProcessor;
import com.jme3.shadow.PssmShadowRenderer;
import com.jme3.water.WaterFilter;
import java.util.List;
import me.furt.projectv.block.*;

public class WorldSettings {

    private static final Vector3f lightDirection = new Vector3f(-0.8f, -1, -0.8f).normalizeLocal();

    public static CubesSettings getSettings(Application application) {
        CubesSettings.setRowCount(10);
        CubesSettings settings = new CubesSettings(application);
        settings.setDefaultBlockMaterial("Textures/Blocks/blocktextures2.png");
        settings.setBlockSize(4);
        return settings;
    }

    public static void registerBlocks() {

        BlockManager.register(Block_Grass.class, new BlockSkin(new TextureLocation[]{
                    new TextureLocation(0, 0),
                    new TextureLocation(1, 0),
                    new TextureLocation(2, 0),
                    new TextureLocation(3, 0),
                    new TextureLocation(4, 0),
                    new TextureLocation(0, 1),}, false) {
            @Override
            protected int getTextureLocationIndex(BlockChunkControl chunk, Vector3Int blockLocation, Block.Face face) {
                if (chunk.isBlockOnSurface(blockLocation)) {
                    switch (face) {
                        case Top:
                            return FastMath.nextRandomInt(0, 3);

                        case Bottom:
                            return 5;
                    }
                    return 4;
                }
                return 5;
            }
        });

        BlockManager.register(Block_Leaves.class, new BlockSkin(new TextureLocation(2, 4), false));

        BlockManager.register(Block_Log.class, new BlockSkin(new TextureLocation[]{
                    new TextureLocation(1, 4),
                    new TextureLocation(1, 4),
                    new TextureLocation(0, 4),
                    new TextureLocation(0, 4),
                    new TextureLocation(0, 4),
                    new TextureLocation(0, 4)
                }, false));

        BlockManager.register(Block_Stone.class, new BlockSkin(new TextureLocation(0, 2), false));

        BlockManager.register(Block_Plank.class, new BlockSkin(new TextureLocation(0, 3), false));

        //BlockManager.register(Block_Dirt.class, new BlockSkin(new TextureLocation(0, 1), false));
        BlockManager.register(Block_Dirt.class, new BlockSkin(new TextureLocation[]{
                    new TextureLocation(0, 1),
                    new TextureLocation(5, 1),}, false) {
            @Override
            protected int getTextureLocationIndex(BlockChunkControl chunk, Vector3Int blockLocation, Block.Face face) {
                return FastMath.nextRandomInt(0, 1);
            }
        });

        BlockManager.register(Block_Sand.class, new BlockSkin(new TextureLocation(4, 1), false));

        BlockManager.register(Block_Mud.class, new BlockSkin(new TextureLocation(1, 1), false));

        BlockManager.register(Block_Cobble.class, new BlockSkin(new TextureLocation(1, 3), false));

        BlockManager.register(Block_Ice.class, new BlockSkin(new TextureLocation(3, 5), true));

        BlockManager.register(Block_Water.class, new BlockSkin(new TextureLocation(0, 5), true));

        BlockManager.register(Block_Gravel.class, new BlockSkin(new TextureLocation(1, 2), false));

        BlockManager.register(Block_Glass.class, new BlockSkin(new TextureLocation(2, 3), true));

        BlockManager.register(Block_Lava.class, new BlockSkin(new TextureLocation(0, 6), true));

        // For creating and texturing a new block first you must create a new block class view Test_Block.java for more info.
        // Next you must determine how many textures you are gonna use.
        // The follow example is for a block that uses only 1 texture for all sides.
        //BlockManager.register(Test_Block.class, new BlockSkin(new TextureLocation(0,0), false));


    }

    public static void initializeEnvironment(SimpleApplication simpleApplication) {
        DirectionalLight directionalLight = new DirectionalLight();
        directionalLight.setDirection(lightDirection);
        directionalLight.setColor(new ColorRGBA(1f, 1f, 1f, 1.0f));
        simpleApplication.getRootNode().addLight(directionalLight);

        PssmShadowRenderer pssmShadowRenderer = new PssmShadowRenderer(simpleApplication.getAssetManager(), 2048, 3);
        pssmShadowRenderer.setDirection(lightDirection);
        pssmShadowRenderer.setShadowIntensity(0.3f);
        simpleApplication.getViewPort().addProcessor(pssmShadowRenderer);
    }

    public static void initializeWater(SimpleApplication simpleApplication) {
        WaterFilter waterFilter = new WaterFilter(simpleApplication.getRootNode(), lightDirection);
        getFilterPostProcessor(simpleApplication).addFilter(waterFilter);
    }

    private static FilterPostProcessor getFilterPostProcessor(SimpleApplication simpleApplication) {
        List<SceneProcessor> sceneProcessors = simpleApplication.getViewPort().getProcessors();
        for (int i = 0; i < sceneProcessors.size(); i++) {
            SceneProcessor sceneProcessor = sceneProcessors.get(i);
            if (sceneProcessor instanceof FilterPostProcessor) {
                return (FilterPostProcessor) sceneProcessor;
            }
        }
        FilterPostProcessor filterPostProcessor = new FilterPostProcessor(simpleApplication.getAssetManager());
        simpleApplication.getViewPort().addProcessor(filterPostProcessor);
        return filterPostProcessor;
    }
}
