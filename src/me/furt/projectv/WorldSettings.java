package me.furt.projectv;

import com.cubes.Block;
import com.cubes.BlockChunkControl;
import com.cubes.BlockManager;
import com.cubes.BlockSkin;
import com.cubes.TextureLocation;
import com.cubes.CubesSettings;
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
        CubesSettings.setRowCount(8);
        CubesSettings settings = new CubesSettings(application);
        settings.setDefaultBlockMaterial("Textures/texturepack.png");
        settings.setBlockSize(4);
        return settings;
    }

    public static void registerBlocks() {
        BlockManager.register(Block_Grass.class, new BlockSkin(new TextureLocation[]{
                    new TextureLocation(0, 0),
                    new TextureLocation(1, 0),
                    new TextureLocation(2, 0),}, false) {
            @Override
            protected int getTextureLocationIndex(BlockChunkControl chunk, Vector3Int blockLocation, Block.Face face) {
                if (chunk.isBlockOnSurface(blockLocation)) {
                    switch (face) {
                        case Top:
                            return FastMath.nextRandomInt(1, 2);

                        case Bottom:
                            return 0;
                    }
                    return FastMath.nextRandomInt(1, 2);
                }
                return 0;
            }
        });
        BlockManager.register(Block_Leaves.class, new BlockSkin(new TextureLocation(4, 1), false));
        BlockManager.register(Block_Log.class, new BlockSkin(new TextureLocation[]{
                    new TextureLocation(3, 1),
                    new TextureLocation(3, 1),
                    new TextureLocation(2, 1),
                    new TextureLocation(2, 1),
                    new TextureLocation(2, 1),
                    new TextureLocation(2, 1)
                }, false));
        BlockManager.register(Block_Stone.class, new BlockSkin(new TextureLocation(5, 0), false));
        BlockManager.register(Block_Plank.class, new BlockSkin(new TextureLocation(7, 0), false));
        BlockManager.register(Block_Dirt.class, new BlockSkin(new TextureLocation(0, 0), false));
        BlockManager.register(Block_Sand.class, new BlockSkin(new TextureLocation(3, 0), false));
        BlockManager.register(Block_Mud.class, new BlockSkin(new TextureLocation(4, 0), false));
        BlockManager.register(Block_Cobble.class, new BlockSkin(new TextureLocation(6, 0), false));
        BlockManager.register(Block_Ice.class, new BlockSkin(new TextureLocation(1, 1), true));
        BlockManager.register(Block_Water.class, new BlockSkin(new TextureLocation(0, 1), true));
        BlockManager.register(Block_Gravel.class, new BlockSkin(new TextureLocation(6, 1), false));
        BlockManager.register(Block_Glass.class, new BlockSkin(new TextureLocation(5, 1), true));
        BlockManager.register(Block_Lava.class, new BlockSkin(new TextureLocation(7, 1), true));
    }

    public static void initializeEnvironment(SimpleApplication simpleApplication) {
        DirectionalLight directionalLight = new DirectionalLight();
        directionalLight.setDirection(lightDirection);
        directionalLight.setColor(new ColorRGBA(1f, 1f, 1f, 1.0f));
        //simpleApplication.getRootNode().addLight(directionalLight);
        //simpleApplication.getRootNode().attachChild(SkyFactory.createSky(simpleApplication.getAssetManager(), "Textures/cubes/sky.jpg", true));

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
