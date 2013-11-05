package com.cubes;

public class BlockType {

    /**
     *
     * @param type
     * @param skin
     */
    public BlockType(byte type, BlockSkin skin) {
        this.type = type;
        this.skin = skin;
    }
    private byte type;
    private BlockSkin skin;

    /**
     *
     * @return
     */
    public byte getType() {
        return type;
    }

    /**
     *
     * @return
     */
    public BlockSkin getSkin() {
        return skin;
    }
}
