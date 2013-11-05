package com.cubes;

public interface ChunkListener {

    /**
     *
     * @param blockChunk
     */
    public abstract void onSpatialUpdated(ChunkControl blockChunk);
}
