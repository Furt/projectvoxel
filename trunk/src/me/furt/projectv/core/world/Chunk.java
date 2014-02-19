package me.furt.projectv.core.world;

import com.jme3.math.Vector3f;
import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import java.io.IOException;
import me.furt.projectv.core.block.Block;
import me.furt.projectv.core.block.BlockNavigator;
import me.furt.projectv.core.network.BitInputStream;
import me.furt.projectv.core.network.BitOutputStream;
import me.furt.projectv.core.network.BitSerializable;
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
public class Chunk extends AbstractControl implements BitSerializable {

    private int[][][] blocks;
    private Region region;
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
        this.blocks = new int[world.getSettings().getChunkSizeX()][world.getSettings().getChunkSizeY()][world.getSettings().getChunkSizeZ()];
        this.region = region;
        this.location = location;
        this.world = region.getWorld();
        this.blockLocation.set(location.mult(world.getSettings().getChunkSizeX(), world.getSettings().getChunkSizeY(), world.getSettings().getChunkSizeZ()));
        this.node.setLocalTranslation(new Vector3f(blockLocation.getX(), blockLocation.getY(), blockLocation.getZ()).mult(world.getSettings().getBlockSize()));
        this.blocks_IsOnSurface = new boolean[world.getSettings().getChunkSizeX()][world.getSettings().getChunkSizeY()][world.getSettings().getChunkSizeZ()];
        this.generated = false;
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

    public Vector3Int getBlockLocation() {
        return blockLocation;
    }

    public Vector3Int getLocation() {
        return location;
    }

    public void setLocation(Vector3Int location) {
        this.location = location;
    }

    public Node getNode() {
        return this.node;
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

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void updateBlockMaterial() {
        if (optimizedGeometry_Opaque != null) {
            optimizedGeometry_Opaque.setMaterial(world.getSettings().getBlockMaterial());
        }
        if (optimizedGeometry_Transparent != null) {
            optimizedGeometry_Transparent.setMaterial(world.getSettings().getBlockMaterial());
        }
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
            MeshOptimizer opt = new MeshOptimizer(world);
            optimizedGeometry_Opaque.setMesh(opt.generateOptimizedMesh(this, TransparencyMerger.OPAQUE));
            optimizedGeometry_Transparent.setMesh(opt.generateOptimizedMesh(this, TransparencyMerger.TRANSPARENT));
            needsMeshUpdate = false;
            return true;
        }
        return false;
    }

    private void updateBlockState(Vector3Int location) {
        updateBlockInformation(location);
        for (int i = 0; i < Block.Face.values().length; i++) {
            Vector3Int neighborLocation = getNeighborBlockGlobalLocation(location, Block.Face.values()[i]);
            Chunk chunk = world.getChunkFromBlock(neighborLocation);
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

    private Vector3Int getNeighborBlockGlobalLocation(Vector3Int location, Block.Face face) {
        Vector3Int neighborLocation = BlockNavigator.getNeighborBlockLocalLocation(location, face);
        neighborLocation.addLocal(blockLocation);
        return neighborLocation;
    }

    public Block getNeighborBlock_Local(Vector3Int location, Block.Face face) {
        Vector3Int neighborLocation = BlockNavigator.getNeighborBlockLocalLocation(location, face);
        return getBlock(neighborLocation);
    }

    public void write(BitOutputStream outputStream) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void read(BitInputStream inputStream) throws IOException {
        for (int x = 0; x < blocks.length; x++) {
            for (int y = 0; y < blocks[0].length; y++) {
                for (int z = 0; z < blocks[0][0].length; z++) {
                    blocks[x][y][z] = (int) inputStream.readBits(8);
                }
            }
        }
        Vector3Int tmpLocation = new Vector3Int();
        for (int x = 0; x < blocks.length; x++) {
            for (int y = 0; y < blocks[0].length; y++) {
                for (int z = 0; z < blocks[0][0].length; z++) {
                    tmpLocation.set(x, y, z);
                    updateBlockInformation(tmpLocation);
                }
            }
        }
        needsMeshUpdate = true;
    }
}
