package com.cubes;

public class BlockType {

    private byte type;
    private BlockSkin skin;

    public BlockType(byte type, BlockSkin skin) {
        this.type = type;
        this.skin = skin;
    }

    public byte getType() {
        return type;
    }

    public BlockSkin getSkin() {
        return skin;
    }
}