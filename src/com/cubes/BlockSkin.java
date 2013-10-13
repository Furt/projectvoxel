package com.cubes;

public class BlockSkin {

    public BlockSkin(TextureLocation textureLocation, boolean isTransparent) {
        this(new TextureLocation[]{textureLocation}, isTransparent);
    }

    public BlockSkin(TextureLocation[] textureLocations, boolean isTransparent) {
        this.textureLocations = textureLocations;
        this.isTransparent = isTransparent;
    }
    private TextureLocation[] textureLocations;
    private boolean isTransparent;

    public TextureLocation getTextureLocation(BlockChunkControl chunk, Vector3Int blockLocation, Block.Face face) {
        return textureLocations[getTextureLocationIndex(chunk, blockLocation, face)];
    }

    protected int getTextureLocationIndex(BlockChunkControl chunk, Vector3Int blockLocation, Block.Face face) {
        if (textureLocations.length == 6) {
            return face.ordinal();
        }
        return 0;
    }

    public boolean isTransparent() {
        return isTransparent;
    }
}
