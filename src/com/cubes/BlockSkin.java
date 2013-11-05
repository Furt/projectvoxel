package com.cubes;

public class BlockSkin {

    /**
     *
     * @param textureLocation
     * @param isTransparent
     */
    public BlockSkin(TextureLocation textureLocation, boolean isTransparent) {
        this(new TextureLocation[]{textureLocation}, isTransparent);
    }

    /**
     *
     * @param textureLocations
     * @param isTransparent
     */
    public BlockSkin(TextureLocation[] textureLocations, boolean isTransparent) {
        this.textureLocations = textureLocations;
        this.isTransparent = isTransparent;
    }
    private TextureLocation[] textureLocations;
    private boolean isTransparent;

    /**
     *
     * @param chunk
     * @param blockLocation
     * @param face
     * @return
     */
    public TextureLocation getTextureLocation(ChunkControl chunk, Vector3Int blockLocation, Block.Face face) {
        return textureLocations[getTextureLocationIndex(chunk, blockLocation, face)];
    }

    /**
     *
     * @param chunk
     * @param blockLocation
     * @param face
     * @return
     */
    protected int getTextureLocationIndex(ChunkControl chunk, Vector3Int blockLocation, Block.Face face) {
        if (textureLocations.length == 6) {
            return face.ordinal();
        }
        return 0;
    }

    /**
     *
     * @return
     */
    public boolean isTransparent() {
        return isTransparent;
    }
}
