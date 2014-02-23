package me.furt.projectv.core.render;

import me.furt.projectv.core.block.Block;
import me.furt.projectv.core.world.Chunk;
import me.furt.projectv.util.Vector3i;

/**
 * ProjectV
 *
 * @author Furt
 */
public interface MeshMerger {

    public abstract boolean shouldFaceBeAdded(Chunk chunk, Vector3i location, Block.Face face);
}
