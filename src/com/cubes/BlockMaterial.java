package com.cubes;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.texture.Texture;

public class BlockMaterial extends Material {

    /**
     *
     * @param assetManager
     * @param blockTextureFilePath
     */
    public BlockMaterial(AssetManager assetManager, String blockTextureFilePath) {
        super(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        Texture texture = assetManager.loadTexture(blockTextureFilePath);
        texture.setMagFilter(Texture.MagFilter.Nearest);
        texture.setMinFilter(Texture.MinFilter.NearestNoMipMaps);
        setTexture("ColorMap", texture);
        getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
    }
}
