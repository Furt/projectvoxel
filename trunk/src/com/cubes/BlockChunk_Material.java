package com.cubes;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.texture.Texture;

public class BlockChunk_Material extends Material {

    public BlockChunk_Material(AssetManager assetManager, String blockTextureFilePath) {
        super(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture texture = assetManager.loadTexture(blockTextureFilePath);
        texture.setMagFilter(Texture.MagFilter.Nearest);
        texture.setMinFilter(Texture.MinFilter.NearestNoMipMaps);
        setTexture("ColorMap", texture);
        getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
    }
}