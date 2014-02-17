package me.furt.projectv.core.block;

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
        blockList.put(1, new Block(1, "Dirt", new BlockSkin(new TextureLocation(1, 3), false)));
    }

    public void addBlock(Block block) {
        if (block.getId() > 100) {
            if (!blockList.containsKey(block.getId())) {
                blockList.put(block.getId(), block);
            } else {
                // Block id already registered by a custom block
            }
        } else {
            // Block id is reserved for defaults must use 100 offset
        }
    }

    public void removeBlock(Block block) {
        if (blockList.containsKey(block.getId())) {
            blockList.remove(block.getId());
        } else {
            // Block id does not exsist
        }
    }

    public void removeBlock(int i) {
        if (blockList.containsKey(i)) {
            blockList.remove(i);
        } else {
            // Block id does not exsist
        }
    }

    public Block getBlock(int i) {
        if (blockList.containsKey(i)) {
            return blockList.get(i);
        } else {
            // Block id does not exsist
            return blockList.get(1);
        }
    }
}
