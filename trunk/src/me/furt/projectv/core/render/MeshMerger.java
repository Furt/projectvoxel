package me.furt.projectv.core.render;

import me.furt.projectv.core.block.Block;
import me.furt.projectv.core.world.Chunk;
import me.furt.projectv.util.Vector3Int;

/**
 * ProjectV
 *
 * @author Furt
 */
public interface MeshMerger {

    public abstract boolean shouldFaceBeAdded(Chunk chunk, Vector3Int location, Block.Face face);
}
