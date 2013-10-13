package com.cubes;

public interface MeshMerger {

    public abstract boolean shouldFaceBeAdded(BlockChunkControl chunk, Vector3Int location, Block.Face face);
}
