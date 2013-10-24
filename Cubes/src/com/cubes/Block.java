package com.cubes;

public class Block {

    private BlockType type;

    public Block() {
        type = BlockManager.getType(getClass());
    }

    public static enum Face {

        Top, Bottom, Left, Right, Front, Back
    };

    public BlockType getType() {
        return type;
    }
}