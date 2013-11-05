package com.cubes;

public interface MeshMerger {

    /**
     *
     * @param chunk
     * @param location
     * @param face
     * @return
     */
    public abstract boolean shouldFaceBeAdded(ChunkControl chunk, Vector3Int location, Block.Face face);
}
