package com.cubes;

public class LocalBlockState {

    /**
     *
     * @param chunk
     * @param localBlockLocation
     */
    public LocalBlockState(ChunkControl chunk, Vector3Int localBlockLocation) {
        this.chunk = chunk;
        this.localBlockLocation = localBlockLocation;
    }
    private ChunkControl chunk;
    private Vector3Int localBlockLocation;

    /**
     *
     * @return
     */
    public ChunkControl getChunk() {
        return chunk;
    }

    /**
     *
     * @return
     */
    public Vector3Int getLocalBlockLocation() {
        return localBlockLocation;
    }

    /**
     *
     * @return
     */
    public BlockType getBlock() {
        return chunk.getBlock(localBlockLocation);
    }

    /**
     *
     * @param blockClass
     */
    public void setBlock(Class<? extends Block> blockClass) {
        chunk.setBlock(localBlockLocation, blockClass);
    }

    /**
     *
     */
    public void removeBlock() {
        chunk.removeBlock(localBlockLocation);
    }
}
