package me.furt.projectv.core.world;

import com.jme3.math.Vector3f;
import com.jme3.network.serializing.Serializable;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import me.furt.projectv.core.block.Block;
import me.furt.projectv.core.render.MeshOptimizer;
import me.furt.projectv.core.render.TransparencyMerger;
import me.furt.projectv.util.Util;
import me.furt.projectv.util.Vector3Int;

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
    public final int chunkX = 16;
    public final int chunkY = 128;
    public final int chunkZ = 16;
    //
    private World world;
    private Vector3Int location = new Vector3Int();
    private Vector3Int blockLocation = new Vector3Int();
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
    
    public void setBlock(Block block, Vector3Int loc) {
        if (isValidBlockLocation(location)) {
            blocks[location.getX()][location.getY()][location.getZ()] = block.getId();
            updateBlockState(location);
            needsMeshUpdate = true;
        }
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
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

    public Control cloneForSpatial(Spatial spatial) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public boolean updateSpatial() {
        if (needsMeshUpdate) {
            if (optimizedGeometry_Opaque == null) {
                optimizedGeometry_Opaque = new Geometry("");
                optimizedGeometry_Opaque.setQueueBucket(Bucket.Opaque);
                node.attachChild(optimizedGeometry_Opaque);
                updateBlockMaterial();
            }
            if (optimizedGeometry_Transparent == null) {
                optimizedGeometry_Transparent = new Geometry("");
                optimizedGeometry_Transparent.setQueueBucket(Bucket.Transparent);
                node.attachChild(optimizedGeometry_Transparent);
                updateBlockMaterial();
            }
            optimizedGeometry_Opaque.setMesh(MeshOptimizer.generateOptimizedMesh(this, TransparencyMerger.OPAQUE));
            optimizedGeometry_Transparent.setMesh(MeshOptimizer.generateOptimizedMesh(this, TransparencyMerger.TRANSPARENT));
            needsMeshUpdate = false;
            return true;
        }
        return false;
    }
    
    private void updateBlockState(Vector3Int location) {
        updateBlockInformation(location);
        for (int i = 0; i < Block.Face.values().length; i++) {
            Vector3Int neighborLocation = getNeighborBlockGlobalLocation(location, Block.Face.values()[i]);
            Chunk chunk = world.getChunk(neighborLocation);
            if (chunk != null) {
                chunk.updateBlockInformation(neighborLocation.subtract(chunk.getBlockLocation()));
            }
        }
    }
    
    private void updateBlockInformation(Vector3Int location) {
        Block neighborBlock_Top = world.getBlock(getNeighborBlockGlobalLocation(location, Block.Face.Top));
        blocks_IsOnSurface[location.getX()][location.getY()][location.getZ()] = (neighborBlock_Top == null);
    }

    private boolean isValidBlockLocation(Vector3Int location) {
        return Util.isValidIndex(blocks, location);
    }
}
