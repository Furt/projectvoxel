package me.furt.projectv.world;

import com.cubes.Vector3Int;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;

/**
 * ProjectV
 *
 * @author Furt
 */
public class Chunk extends AbstractControl {

    private byte[][][] Blocks;
    private Region region;
    private Vector3Int location;
    private int chunkX = 16;
    private int chunkY = 128;
    private int chunkZ = 16;

    public Chunk() {
    }

    public Chunk(Region region, Vector3Int location) {
        Blocks = new byte[chunkX][chunkY][chunkZ];
        this.region = region;
        this.location = location;
    }

    public byte[][][] getBlocks() {
        return Blocks;
    }

    public Block getBlock(Vector3Int location) {
        return BlockManager.getBlock(Blocks[location.getX()][location.getY()][location.getZ()]);
    }

    public void setBlocks(byte[][][] Blocks) {
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
}
