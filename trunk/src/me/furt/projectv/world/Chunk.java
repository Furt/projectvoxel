package me.furt.projectv.world;

import com.cubes.Vector3Int;
import com.jme3.math.Vector3f;
import com.jme3.network.serializing.Serializable;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import me.furt.projectv.WorldSettings;

/**
 * ProjectV
 *
 * @author Furt
 */
@Serializable
public class Chunk extends AbstractControl {

    private int[][][] blocks;
    private Region region;
    // these should not be here
    private int chunkX = 16;
    private int chunkY = 128;
    private int chunkZ = 16;
    //
    private World world;
    private Vector3Int location = new Vector3Int();
    private Vector3Int blockLocation = new Vector3Int();
    private byte[][][] blockTypes;
    private boolean[][][] blocks_IsOnSurface;
    private Node node = new Node();
    private Geometry optimizedGeometry_Opaque;
    private Geometry optimizedGeometry_Transparent;
    private boolean needsMeshUpdate;
    private boolean generated;

    public Chunk() {
    }

    public Chunk(Region region, Vector3Int location) {
        blocks = new int[chunkX][chunkY][chunkZ];
        this.region = region;
        this.location = location;
        world = region.getWorld();
        blockLocation.set(location.mult(chunkX, chunkY, chunkZ));
        // 1 needs to be replaced with blockSize var
        node.setLocalTranslation(new Vector3f(blockLocation.getX(), blockLocation.getY(), blockLocation.getZ()).mult(1));
        blockTypes = new byte[chunkX][chunkY][chunkZ];
        blocks_IsOnSurface = new boolean[chunkX][chunkY][chunkZ];
        generated = false;
    }

    public int[][][] getBlocks() {
        return blocks;
    }

    public Block getBlock(Vector3Int loc) {
        return world.getBlockManager().getBlock(blocks[loc.getX()][loc.getY()][loc.getZ()]);
    }

    public void setBlocks(int[][][] Blocks) {
        this.blocks = Blocks;
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
    public void setSpatial(Spatial spatial) {
        Spatial oldSpatial = this.spatial;
        super.setSpatial(spatial);
        if (spatial instanceof Node) {
            Node parentNode = (Node) spatial;
            parentNode.attachChild(node);
        } else if (oldSpatial instanceof Node) {
            Node oldNode = (Node) oldSpatial;
            oldNode.detachChild(node);
        }
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
