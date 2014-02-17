package me.furt.projectv.core.render;

import me.furt.projectv.core.block.Block;
import me.furt.projectv.core.world.Chunk;
import me.furt.projectv.util.Vector3Int;

public interface MeshMerger {

    /**
     *
     * @param chunk
     * @param location
     * @param face
     * @return
     */
    public abstract boolean shouldFaceBeAdded(Chunk chunk, Vector3Int location, Block.Face face);
}
