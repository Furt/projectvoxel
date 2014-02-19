package me.furt.projectv.core.world;

import com.jme3.app.Application;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.Vector2f;
import me.furt.projectv.core.block.BlockMaterial;
import me.furt.projectv.util.Vector3Int;

/**
 * ProjectV
 *
 * @author Furt
 */
public class WorldSettings {

    // Primary asset manager for the application //
    private AssetManager assetManager;
    // Amount of textures in a row //
    private float rowCount = 10;
    // The multipler for block size //
    private float blockSize = 1;
    // Amount of block on X axis //
    private int chunkSizeX = 16;
    // Amount of block on Y axis //
    private int chunkSizeY = 256;
    // Amount of block on Z axis //
    private int chunkSizeZ = 16;
    // Amount of chunk on x & z axis //
    private Vector3Int regionSize = new Vector3Int(50, 0, 50);
    // Material use for rendering the block //
    private Material blockMaterial;

    public WorldSettings(Application app) {
        this.assetManager = app.getAssetManager();
    }

    public AssetManager getAssetManager() {
        return this.assetManager;
    }

    public float getRowCount() {
        return this.rowCount;
    }

    public void setRowCount(float rowCount) {
        this.rowCount = rowCount;
    }

    public float getBlockSize() {
        return this.blockSize;
    }

    public void setBlockSize(float blockSize) {
        this.blockSize = blockSize;
    }

    public int getChunkSizeX() {
        return this.chunkSizeX;
    }

    public void setChunkSizeX(int chunkSizeX) {
        this.chunkSizeX = chunkSizeX;
    }

    public int getChunkSizeY() {
        return this.chunkSizeY;
    }

    public void setChunkSizeY(int chunkSizeY) {
        this.chunkSizeY = chunkSizeY;
    }

    public int getChunkSizeZ() {
        return this.chunkSizeZ;
    }

    public void setChunkSizeZ(int chunkSizeZ) {
        this.chunkSizeZ = chunkSizeZ;
    }

    public Vector3Int getRegionSize() {
        return regionSize;
    }

    public void setRegionSize(Vector3Int regionSize) {
        this.regionSize = regionSize;
    }

    public Material getBlockMaterial() {
        return this.blockMaterial;
    }

    public void setDefaultBlockMaterial(String textureFilePath) {
        setBlockMaterial(new BlockMaterial(assetManager, textureFilePath));
    }

    public void setBlockMaterial(Material blockMaterial) {
        this.blockMaterial = blockMaterial;
    }
}
