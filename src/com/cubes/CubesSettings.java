package com.cubes;

import com.jme3.app.Application;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;

public class CubesSettings {

    /**
     *
     * @param application
     */
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

    /**
     *
     * @return
     */
    public AssetManager getAssetManager() {
        return assetManager;
    }
    
    /**
     *
     * @return
     */
    public static float getRowCount() {
        return rowCount;
    }
    
    /**
     *
     * @param rowCount
     */
    public static void setRowCount(float rowCount) {
        CubesSettings.rowCount = rowCount;
    }

    /**
     *
     * @return
     */
    public float getBlockSize() {
        return blockSize;
    }

    /**
     *
     * @param blockSize
     */
    public void setBlockSize(float blockSize) {
        this.blockSize = blockSize;
    }

    /**
     *
     * @return
     */
    public int getChunkSizeX() {
        return chunkSizeX;
    }

    /**
     *
     * @param chunkSizeX
     */
    public void setChunkSizeX(int chunkSizeX) {
        this.chunkSizeX = chunkSizeX;
    }

    /**
     *
     * @return
     */
    public int getChunkSizeY() {
        return chunkSizeY;
    }

    /**
     *
     * @param chunkSizeY
     */
    public void setChunkSizeY(int chunkSizeY) {
        this.chunkSizeY = chunkSizeY;
    }

    /**
     *
     * @return
     */
    public int getChunkSizeZ() {
        return chunkSizeZ;
    }

    /**
     *
     * @param chunkSizeZ
     */
    public void setChunkSizeZ(int chunkSizeZ) {
        this.chunkSizeZ = chunkSizeZ;
    }

    /**
     *
     * @return
     */
    public Material getBlockMaterial() {
        return blockMaterial;
    }

    /**
     *
     * @param textureFilePath
     */
    public void setDefaultBlockMaterial(String textureFilePath) {
        setBlockMaterial(new BlockMaterial(assetManager, textureFilePath));
    }

    /**
     *
     * @param blockMaterial
     */
    public void setBlockMaterial(Material blockMaterial) {
        this.blockMaterial = blockMaterial;
    }
}
