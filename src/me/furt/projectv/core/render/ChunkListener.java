package me.furt.projectv.core.render;

import me.furt.projectv.core.world.Chunk;

/**
 * ProjectV
 *
 * @author Furt
 */
public interface ChunkListener {

    public abstract void onSpatialUpdated(Chunk chunk);
}
