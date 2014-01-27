package me.furt.projectv.world;

import java.util.HashMap;

/**
 * ProjectV
 *
 * @author Furt
 */
public class BlockManager {

    private HashMap<Integer, Block> blockList;

    public BlockManager() {
        blockList = new HashMap<Integer, Block>();
    }

    public void registerDefaults() {
        // New method for registering blocks, reference Block for values
        blockList.put(1, new Block(1, "Dirt", 5, 2, true, true));
    }

    public void addBlock(Block block) {
        if(!blockList.containsKey(block.getId())) {
            blockList.put(block.getId(), block);
        } else {
            // Block id already registered
        }
    }

    public void removeBlock(Block block) {
        if (blockList.containsKey(block.getId())) {
            blockList.remove(block.getId());
        } else {
            // Block id does not exsist
        }
    }

    public Block getBlock(int i) {
        return blockList.get(i);
    }
}
