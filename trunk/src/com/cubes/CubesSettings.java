package com.cubes;

import com.jme3.app.Application;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;

public class CubesSettings {

    public CubesSettings(Application application) {
        assetManager = application.getAssetManager();
    }
    private AssetManager assetManager;
    private static float rowCount = 16;
    private float blockSize = 3;
    private int chunkSizeX = 16;
    private int chunkSizeY = 128;
    private int chunkSizeZ = 16;
    private Material blockMaterial;

    public AssetManager getAssetManager() {
        return assetManager;
    }
    
    public static float getRowCount() {
        return rowCount;
    }
    
    public static void setRowCount(float rowCount) {
        CubesSettings.rowCount = rowCount;
    }

    public float getBlockSize() {
        return blockSize;
    }

    public void setBlockSize(float blockSize) {
        this.blockSize = blockSize;
    }

    public int getChunkSizeX() {
        return chunkSizeX;
    }

    public void setChunkSizeX(int chunkSizeX) {
        this.chunkSizeX = chunkSizeX;
    }

    public int getChunkSizeY() {
        return chunkSizeY;
    }

    public void setChunkSizeY(int chunkSizeY) {
        this.chunkSizeY = chunkSizeY;
    }

    public int getChunkSizeZ() {
        return chunkSizeZ;
    }

    public void setChunkSizeZ(int chunkSizeZ) {
        this.chunkSizeZ = chunkSizeZ;
    }

    public Material getBlockMaterial() {
        return blockMaterial;
    }

    public void setDefaultBlockMaterial(String textureFilePath) {
        setBlockMaterial(new BlockMaterial(assetManager, textureFilePath));
    }

    public void setBlockMaterial(Material blockMaterial) {
        this.blockMaterial = blockMaterial;
    }
}
