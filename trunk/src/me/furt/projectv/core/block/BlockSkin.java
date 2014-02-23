package me.furt.projectv.core.block;

import me.furt.projectv.core.world.Chunk;
import me.furt.projectv.util.Vector3i;

/**
 * ProjectV
 *
 * @author Furt
 */
public class BlockSkin {

    private TextureLocation[] textureLocations;
    private boolean isTransparent;

    public BlockSkin(TextureLocation textureLocation, boolean isTransparent) {
        this(new TextureLocation[]{textureLocation}, isTransparent);
    }

    public BlockSkin(TextureLocation[] textureLocations, boolean isTransparent) {
        this.textureLocations = textureLocations;
        this.isTransparent = isTransparent;
    }

    public TextureLocation getTextureLocation(Chunk chunk, Vector3i blockLocation, Block.Face face) {
        return textureLocations[getTextureLocationIndex(chunk, blockLocation, face)];
    }

    protected int getTextureLocationIndex(Chunk chunk, Vector3i blockLocation, Block.Face face) {
        if (textureLocations.length == 6) {
            return face.ordinal();
        }
        return 0;
    }

    public boolean isTransparent() {
        return isTransparent;
    }
}
