package me.furt.projectv.world;

import com.cubes.Vector3Int;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import java.util.ArrayList;

/**
 * ProjectV
 *
 * @author Furt
 */
public class Region extends AbstractControl {
    private Chunk[][] chunkList = new Chunk[80][80];
    private byte[][] noiseData;
    //private ArrayList<Chunk> chunkList = new ArrayList<Chunk>();
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

    public Chunk[][] getChunkList() {
        return chunkList;
    }

    public void setChunkList(Chunk[][] chunkList) {
        this.chunkList = chunkList;
    }
    
    /*
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
    */

    public Chunk getChunkFromBlock(Vector3Int block) {
        int x = block.getX()/16;
        int z = block.getZ()/16;
        return chunkList[x][z];
    }

    @Override
    protected void controlUpdate(float tpf) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Control cloneForSpatial(Spatial spatial) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public boolean loadRegion(Vector3Int location) {
        return false;
    }
    
    public boolean saveRegion() {
        return false;
    }
}
