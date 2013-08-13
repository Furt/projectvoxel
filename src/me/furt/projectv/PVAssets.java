/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.furt.projectv;

import com.cubes.Block;
import com.cubes.BlockChunkControl;
import com.cubes.BlockManager;
import com.cubes.BlockSkin;
import com.cubes.BlockSkin_TextureLocation;
import com.cubes.CubesSettings;
import com.cubes.Vector3Int;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.SceneProcessor;
import com.jme3.shadow.PssmShadowRenderer;
import com.jme3.util.SkyFactory;
import com.jme3.water.WaterFilter;
import java.util.List;
import me.furt.projectv.blocks.ClayBlock;
import me.furt.projectv.blocks.CobbleBlock;
import me.furt.projectv.blocks.DirtBlock;
import me.furt.projectv.blocks.GrassBlock;
import me.furt.projectv.blocks.PlankBlock;
import me.furt.projectv.blocks.StoneBlock;
import me.furt.projectv.blocks.WaterBlock;

/**
 *
 * @author Terry
 */
public class PVAssets {

    private static final Vector3f lightDirection = new Vector3f(-0.8F, -1.0F, -0.8F).normalizeLocal();

    public static CubesSettings getSettings(Application application) {
        CubesSettings settings = new CubesSettings(application);
        settings.setDefaultBlockMaterial("Textures/test1_1.png");
        settings.setChunkSizeX(16);
        settings.setChunkSizeY(256);
        settings.setChunkSizeZ(16);
        return settings;
    }

    public static void registerBlocks() {
        BlockManager.register(GrassBlock.class, new BlockSkin(new BlockSkin_TextureLocation[]{new BlockSkin_TextureLocation(0, 0), new BlockSkin_TextureLocation(1, 0)}, false) {
            protected int getTextureLocationIndex(BlockChunkControl chunk, Vector3Int blockLocation, Block.Face face) {
                if (chunk.isBlockOnSurface(blockLocation)) {
                    switch (face.ordinal()) {
                        case 1:
                            return 1;
                    }
                    return 0;
                }
                return 1;
            }
        });

        //BlockManager.register(GrassBlock.class, new BlockSkin(new BlockSkin_TextureLocation(0, 0), false));
        //BlockManager.register(DirtBlock.class, new BlockSkin(new BlockSkin_TextureLocation(1, 0), false));
        BlockManager.register(ClayBlock.class, new BlockSkin(new BlockSkin_TextureLocation(2, 0), false));
        BlockManager.register(CobbleBlock.class, new BlockSkin(new BlockSkin_TextureLocation(3, 0), false));
        BlockManager.register(StoneBlock.class, new BlockSkin(new BlockSkin_TextureLocation(4, 0), false));
        BlockManager.register(WaterBlock.class, new BlockSkin(new BlockSkin_TextureLocation(6, 0), true));
        BlockManager.register(PlankBlock.class, new BlockSkin(new BlockSkin_TextureLocation(7, 0), false));
    }

    public static void initializeEnvironment(SimpleApplication simpleApplication) {
        DirectionalLight directionalLight = new DirectionalLight();
        directionalLight.setDirection(lightDirection);
        directionalLight.setColor(new ColorRGBA(1.0F, 1.0F, 1.0F, 1.0F));
        simpleApplication.getRootNode().addLight(directionalLight);
        simpleApplication.getRootNode().attachChild(SkyFactory.createSky(simpleApplication.getAssetManager(), "Textures/cubes/sky.jpg", true));

        PssmShadowRenderer pssmShadowRenderer = new PssmShadowRenderer(simpleApplication.getAssetManager(), 2048, 3);
        pssmShadowRenderer.setDirection(lightDirection);
        pssmShadowRenderer.setShadowIntensity(0.3F);
        simpleApplication.getViewPort().addProcessor(pssmShadowRenderer);
    }

    public static void initializeWater(SimpleApplication simpleApplication) {
        WaterFilter waterFilter = new WaterFilter(simpleApplication.getRootNode(), lightDirection);
        getFilterPostProcessor(simpleApplication).addFilter(waterFilter);
    }

    private static FilterPostProcessor getFilterPostProcessor(SimpleApplication simpleApplication) {
        List sceneProcessors = simpleApplication.getViewPort().getProcessors();
        for (int i = 0; i < sceneProcessors.size(); i++) {
            SceneProcessor sceneProcessor = (SceneProcessor) sceneProcessors.get(i);
            if ((sceneProcessor instanceof FilterPostProcessor)) {
                return (FilterPostProcessor) sceneProcessor;
            }
        }
        FilterPostProcessor filterPostProcessor = new FilterPostProcessor(simpleApplication.getAssetManager());
        simpleApplication.getViewPort().addProcessor(filterPostProcessor);
        return filterPostProcessor;
    }
}
