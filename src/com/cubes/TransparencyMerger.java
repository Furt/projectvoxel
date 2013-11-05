package com.cubes;

import com.cubes.Block.Face;

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
    public boolean shouldFaceBeAdded(ChunkControl chunk, Vector3Int location, Face face) {
        BlockType block = chunk.getBlock(location);
        if (block.getSkin().isTransparent() == isGeometryTransparent) {
            BlockType neighborBlock = chunk.getNeighborBlock_Local(location, face);
            if (neighborBlock != null) {
                if (block.getSkin().isTransparent() != neighborBlock.getSkin().isTransparent()) {
                    return true;
                }
                return false;
            }
            return true;
        }
        return false;
    }
}
