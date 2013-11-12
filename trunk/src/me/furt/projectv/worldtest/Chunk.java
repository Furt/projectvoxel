package me.furt.projectv.worldtest;

import me.furt.projectv.worldtest.Block.BlockType;

/**
 * ProjectV
 *
 * @author Furt
 */
public class Chunk {

    static final int CHUNK_SIZE = 16;
    private Block[][][] Blocks;

    public void Render() {
    }

    public void Update() {
    }

    public Chunk() {
        Blocks = new Block[CHUNK_SIZE][CHUNK_SIZE][CHUNK_SIZE];
        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int y = 0; y < CHUNK_SIZE; y++) {
                for (int z = 0; z < CHUNK_SIZE; z++) {
                    Blocks[x][y][z] = new Block(BlockType.Dirt);
                }
            }
        }
    }
}
