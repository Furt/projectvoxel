package com.cubes;

import com.jme3.math.FastMath;

public class Block {

    // example of how a block could be registered
    public static final Block cobblestone = new Block(0, "CobbleStone", new BlockSkin(new TextureLocation[]{
                new TextureLocation(1, 3),
                new TextureLocation(3, 3),
                new TextureLocation(4, 3)
            }, false) {
        @Override
        protected int getTextureLocationIndex(ChunkControl chunk, Vector3Int blockLocation, Block.Face face) {
            return FastMath.nextRandomInt(0, 2);
        }
    }, 1, true);
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
