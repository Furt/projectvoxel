package me.furt.projectv.core.render;

import me.furt.projectv.core.world.Chunk;

public interface ChunkListener {

    /**
     *
     * @param blockChunk
     */
    public abstract void onSpatialUpdated(Chunk chunk);
}
