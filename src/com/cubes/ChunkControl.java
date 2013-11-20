package com.cubes;

import com.cubes.network.*;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import java.io.IOException;

public class ChunkControl extends AbstractControl implements BitSerializable {

    /**
     *
     * @param terrain
     * @param x
     * @param y
     * @param z
     */
    public ChunkControl(TerrainControl terrain, int x, int y, int z) {
        this.terrain = terrain;
        location.set(x, y, z);
        blockLocation.set(location.mult(terrain.getSettings().getChunkSizeX(), terrain.getSettings().getChunkSizeY(), terrain.getSettings().getChunkSizeZ()));
        node.setLocalTranslation(new Vector3f(blockLocation.getX(), blockLocation.getY(), blockLocation.getZ()).mult(terrain.getSettings().getBlockSize()));
        blockTypes = new byte[terrain.getSettings().getChunkSizeX()][terrain.getSettings().getChunkSizeY()][terrain.getSettings().getChunkSizeZ()];
        blocks_IsOnSurface = new boolean[terrain.getSettings().getChunkSizeX()][terrain.getSettings().getChunkSizeY()][terrain.getSettings().getChunkSizeZ()];
        generated = false;
    }
    private TerrainControl terrain;
    private Vector3Int location = new Vector3Int();
    private Vector3Int blockLocation = new Vector3Int();
    private byte[][][] blockTypes;
    private boolean[][][] blocks_IsOnSurface;
    private Node node = new Node();
    private Geometry optimizedGeometry_Opaque;
    private Geometry optimizedGeometry_Transparent;
    private boolean needsMeshUpdate;
    private boolean generated;

    /**
     *
     * @param spatial
     */
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

    /**
     *
     * @param lastTimePerFrame
     */
    @Override
    protected void controlUpdate(float lastTimePerFrame) {
    }

    /**
     *
     * @param renderManager
     * @param viewPort
     */
    @Override
    protected void controlRender(RenderManager renderManager, ViewPort viewPort) {
    }

    /**
     *
     * @param spatial
     * @return
     */
    @Override
    public Control cloneForSpatial(Spatial spatial) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     *
     * @param location
     * @param face
     * @return
     */
    public BlockType getNeighborBlock_Local(Vector3Int location, Block.Face face) {
        Vector3Int neighborLocation = BlockNavigator.getNeighborBlockLocalLocation(location, face);
        return getBlock(neighborLocation);
    }

    /**
     *
     * @param location
     * @param face
     * @return
     */
    public BlockType getNeighborBlock_Global(Vector3Int location, Block.Face face) {
        return terrain.getBlock(getNeighborBlockGlobalLocation(location, face));
    }

    /**
     *
     * @param location
     * @param face
     * @return
     */
    private Vector3Int getNeighborBlockGlobalLocation(Vector3Int location, Block.Face face) {
        Vector3Int neighborLocation = BlockNavigator.getNeighborBlockLocalLocation(location, face);
        neighborLocation.addLocal(blockLocation);
        return neighborLocation;
    }

    /**
     *
     * @param location
     * @return
     */
    public BlockType getBlock(Vector3Int location) {
        if (isValidBlockLocation(location)) {
            byte blockType = blockTypes[location.getX()][location.getY()][location.getZ()];
            return BlockManager.getType(blockType);
        }
        return null;
    }

    /**
     *
     * @param location
     * @param blockClass
     */
    public void setBlock(Vector3Int location, Class<? extends Block> blockClass) {
        if (isValidBlockLocation(location)) {
            BlockType blockType = BlockManager.getType(blockClass);
            blockTypes[location.getX()][location.getY()][location.getZ()] = blockType.getType();
            updateBlockState(location);
            needsMeshUpdate = true;
        }
    }

    /**
     *
     * @param location
     */
    public void removeBlock(Vector3Int location) {
        if (isValidBlockLocation(location)) {
            blockTypes[location.getX()][location.getY()][location.getZ()] = 0;
            updateBlockState(location);
            needsMeshUpdate = true;
        }
    }

    /**
     *
     * @param location
     * @return
     */
    private boolean isValidBlockLocation(Vector3Int location) {
        return Util.isValidIndex(blockTypes, location);
    }

    /**
     *
     * @return
     */
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

    /**
     *
     */
    public void updateBlockMaterial() {
        if (optimizedGeometry_Opaque != null) {
            optimizedGeometry_Opaque.setMaterial(terrain.getSettings().getBlockMaterial());
        }
        if (optimizedGeometry_Transparent != null) {
            optimizedGeometry_Transparent.setMaterial(terrain.getSettings().getBlockMaterial());
        }
    }

    /**
     *
     * @param location
     */
    private void updateBlockState(Vector3Int location) {
        updateBlockInformation(location);
        for (int i = 0; i < Block.Face.values().length; i++) {
            Vector3Int neighborLocation = getNeighborBlockGlobalLocation(location, Block.Face.values()[i]);
            ChunkControl chunk = terrain.getChunk(neighborLocation);
            if (chunk != null) {
                chunk.updateBlockInformation(neighborLocation.subtract(chunk.getBlockLocation()));
            }
        }
    }

    /**
     *
     * @param location
     */
    private void updateBlockInformation(Vector3Int location) {
        BlockType neighborBlock_Top = terrain.getBlock(getNeighborBlockGlobalLocation(location, Block.Face.Top));
        blocks_IsOnSurface[location.getX()][location.getY()][location.getZ()] = (neighborBlock_Top == null);
    }

    /**
     *
     * @param location
     * @return
     */
    public boolean isBlockOnSurface(Vector3Int location) {
        return blocks_IsOnSurface[location.getX()][location.getY()][location.getZ()];
    }

    /**
     *
     * @return
     */
    public TerrainControl getTerrain() {
        return terrain;
    }

    /**
     *
     * @return
     */
    public Vector3Int getLocation() {
        return location;
    }

    /**
     *
     * @return
     */
    public Vector3Int getBlockLocation() {
        return blockLocation;
    }

    /**
     *
     * @return
     */
    public Node getNode() {
        return node;
    }

    /**
     *
     * @return
     */
    public Geometry getOptimizedGeometry_Opaque() {
        return optimizedGeometry_Opaque;
    }

    /**
     *
     * @return
     */
    public Geometry getOptimizedGeometry_Transparent() {
        return optimizedGeometry_Transparent;
    }

    /**
     *
     * @param outputStream
     */
    @Override
    public void write(BitOutputStream outputStream) {
        for (int x = 0; x < blockTypes.length; x++) {
            for (int y = 0; y < blockTypes[0].length; y++) {
                for (int z = 0; z < blockTypes[0][0].length; z++) {
                    outputStream.writeBits(blockTypes[x][y][z], 8);
                }
            }
        }
    }

    /**
     *
     * @param inputStream
     * @throws IOException
     */
    @Override
    public void read(BitInputStream inputStream) throws IOException {
        for (int x = 0; x < blockTypes.length; x++) {
            for (int y = 0; y < blockTypes[0].length; y++) {
                for (int z = 0; z < blockTypes[0][0].length; z++) {
                    blockTypes[x][y][z] = (byte) inputStream.readBits(8);
                }
            }
        }
        Vector3Int tmpLocation = new Vector3Int();
        for (int x = 0; x < blockTypes.length; x++) {
            for (int y = 0; y < blockTypes[0].length; y++) {
                for (int z = 0; z < blockTypes[0][0].length; z++) {
                    tmpLocation.set(x, y, z);
                    updateBlockInformation(tmpLocation);
                }
            }
        }
        needsMeshUpdate = true;
    }

    /**
     *
     * @param blocksCount
     * @return
     */
    private Vector3Int getNeededBlockChunks(Vector3Int blocksCount) {
        int chunksCountX = (int) Math.ceil(((float) blocksCount.getX()) / terrain.getSettings().getChunkSizeX());
        int chunksCountY = (int) Math.ceil(((float) blocksCount.getY()) / terrain.getSettings().getChunkSizeY());
        int chunksCountZ = (int) Math.ceil(((float) blocksCount.getZ()) / terrain.getSettings().getChunkSizeZ());
        return new Vector3Int(chunksCountX, chunksCountY, chunksCountZ);
    }

    public boolean isGenerated() {
        return generated;
    }

    public void setGenerated(boolean b) {
        this.generated = b;
    }
}
