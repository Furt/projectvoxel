package me.furt.projectv.world;

import java.util.HashMap;

/**
 * ProjectV
 *
 * @author Furt
 */
public class BlockManager {

    private byte id = 1;
    private static HashMap<Byte, Block> blockList = new HashMap<Byte, Block>();

    public BlockManager() {
        blockList.put(id, new Block(id, "Dirt", 5, 2, true, false));
    }

    static Block getBlock(byte b) {
        return blockList.get(b);
    }
}
