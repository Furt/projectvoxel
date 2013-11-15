package me.furt.projectv.worldtest;

import com.cubes.Vector3Int;
import java.util.ArrayList;

/**
 * ProjectV
 *
 * @author Furt
 */
public class Region {
    private ArrayList<Chunk> chunkList = new ArrayList<Chunk>();
    private Vector3Int location;
    private World world;
    
    public Region() {
        
    }
    
    public Region(World world, Vector3Int location) {
        this.world = world;
        this.location = location;
    }

    public Vector3Int getLocation() {
        return location;
    }

    public void setLocation(Vector3Int location) {
        this.location = location;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }
    
    public ArrayList<Chunk> getChunkList() {
        return chunkList;
    }

    public void setChunkList(ArrayList<Chunk> chunkList) {
        this.chunkList = chunkList;
    }
    
    public void addChunk(Chunk chunk) {
        this.chunkList.add(chunk);
    }
    
    public void removeChunk(Chunk chunk) {
        this.chunkList.remove(chunk);
    }

    Chunk getChunkFromBlock(Vector3Int block) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
