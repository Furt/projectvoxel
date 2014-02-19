package me.furt.projectv.core.world;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import me.furt.projectv.util.Vector3Int;

/**
 * ProjectV
 *
 * @author Furt
 */
public class Region extends AbstractControl {

    private Chunk[][][] chunkList;
    private byte[][] noiseData;
    private Vector3Int location;
    private World world;
    private WorldSettings worldSettings;

    public Region() {
    }

    public Region(World world, Vector3Int location) {
        this.world = world;
        this.worldSettings = world.getSettings();
        this.location = location;
        this.chunkList = new Chunk[worldSettings.getRegionSize().getX()][worldSettings.getRegionSize().getY()][worldSettings.getRegionSize().getZ()];
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

    public Chunk[][][] getChunkList() {
        return chunkList;
    }

    public void setChunkList(Chunk[][][] chunkList) {
        this.chunkList = chunkList;
    }

    public Chunk getChunkFromBlock(Vector3Int block) {
        int x = block.getX() / worldSettings.getChunkSizeX();
        int y = block.getY() / worldSettings.getChunkSizeY();
        int z = block.getZ() / worldSettings.getChunkSizeZ();
        return chunkList[x][y][z];
    }

    @Override
    protected void controlUpdate(float tpf) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
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
