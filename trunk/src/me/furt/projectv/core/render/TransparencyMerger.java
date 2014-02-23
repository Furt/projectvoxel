package me.furt.projectv.core.render;

import me.furt.projectv.core.block.Block;
import me.furt.projectv.core.world.Chunk;
import me.furt.projectv.util.Vector3i;

/**
 * ProjectV
 *
 * @author Furt
 */
public class TransparencyMerger implements MeshMerger {

    public static final TransparencyMerger OPAQUE = new TransparencyMerger(false);
    public static final TransparencyMerger TRANSPARENT = new TransparencyMerger(true);
    private boolean isGeometryTransparent;

    private TransparencyMerger(boolean isGeometryTransparent) {
        this.isGeometryTransparent = isGeometryTransparent;
    }

    @Override
    public boolean shouldFaceBeAdded(Chunk chunk, Vector3i location, Block.Face face) {
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
