package me.furt.projectv.worldtest;

import com.cubes.Vector3Int;

/**
 * ProjectV
 *
 * @author Furt
 */
public class Chunk {

    private Block[][][] Blocks = new Block[16][128][16];
    private Region region;
    private Vector3Int location;
    
    public Chunk() {
        
    }

    public Chunk(Region region, Vector3Int location) {
        this.region = region;
        this.location = location;
    }

    public Block[][][] getBlocks() {
        return Blocks;
    }

    public void setBlocks(Block[][][] Blocks) {
        this.Blocks = Blocks;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Vector3Int getLocation() {
        return location;
    }

    public void setLocation(Vector3Int location) {
        this.location = location;
    }
    
    public Block getBlockAtLoc(Vector3Int location) {
        return this.Blocks[location.getX()][location.getY()][location.getZ()];
    }
    
    
}
