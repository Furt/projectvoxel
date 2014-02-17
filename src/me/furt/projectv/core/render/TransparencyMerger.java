package me.furt.projectv.core.render;

import me.furt.projectv.core.block.Block;
import me.furt.projectv.core.world.Chunk;
import me.furt.projectv.util.Vector3Int;

public class TransparencyMerger implements MeshMerger {

    private TransparencyMerger(boolean isGeometryTransparent) {
        this.isGeometryTransparent = isGeometryTransparent;
    }
    /**
     *
     */
    public static final TransparencyMerger OPAQUE = new TransparencyMerger(false);
    /**
     *
     */
    public static final TransparencyMerger TRANSPARENT = new TransparencyMerger(true);
    private boolean isGeometryTransparent;

    /**
     *
     * @param chunk
     * @param location
     * @param face
     * @return
     */
    @Override
    public boolean shouldFaceBeAdded(Chunk chunk, Vector3Int location, Block.Face face) {
        Block block = chunk.getBlock(location);
        if (block.getTexture().isTransparent() == isGeometryTransparent) {
            Block neighborBlock = chunk.getNeighborBlock_Local(location, face);
            if (neighborBlock != null) {
                if (block.getTexture().isTransparent() != neighborBlock.getTexture().isTransparent()) {
                    return true;
                }
                return false;
            }
            return true;
        }
        return false;
    }
}
