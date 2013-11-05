package com.cubes;

public class Block {

    public int id;
    public String name;
    public int hardness;
    public boolean breakable;
    private BlockType type;
    private BlockSkin blockSkin;

    /**
     *
     */
    public Block() {
        type = BlockManager.getType(getClass());
    }
    
    public Block(int id, String name, BlockSkin blockSkin, int hardness, boolean breakable) {
        
    }

    /**
     *
     */
    public static enum Face {

        /**
         * Top Face
         */
        Top,
        /**
         * Bottom Face
         */
        Bottom,
        /**
         * Left Face
         */
        Left,
        /**
         * Right Face
         */
        Right,
        /**
         * Front Face
         */
        Front,
        /**
         * Back Face
         */
        Back
    };

    /**
     *
     * @return
     */
    public BlockType getType() {
        return type;
    }
}
