package com.cubes;

import java.util.HashMap;

public class BlockManager {

    private static HashMap<Class<? extends Block>, BlockType> BLOCK_TYPES = new HashMap<Class<? extends Block>, BlockType>();
    private static BlockType[] TYPES_BLOCKS = new BlockType[256];
    private static byte nextBlockType = 1;

    /**
     *
     * @param blockClass
     * @param skin
     */
    public static void register(Class<? extends Block> blockClass, BlockSkin skin) {
        BlockType blockType = new BlockType(nextBlockType, skin);
        BLOCK_TYPES.put(blockClass, blockType);
        TYPES_BLOCKS[nextBlockType] = blockType;
        nextBlockType++;
    }

    /**
     *
     * @param blockClass
     * @return
     */
    public static BlockType getType(Class<? extends Block> blockClass) {
        return BLOCK_TYPES.get(blockClass);
    }

    /**
     *
     * @param type
     * @return
     */
    public static Class<? extends Block> getClass(byte type) {
        return Util.getHashKeyByValue(BLOCK_TYPES, getType(type));
    }

    /**
     *
     * @param type
     * @return
     */
    public static BlockType getType(byte type) {
        return TYPES_BLOCKS[type];
    }
    
    public static BlockType[] getTypes() {
        return TYPES_BLOCKS;
    }
}
