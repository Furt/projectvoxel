package me.furt.projectv.voxelengine.region;

import java.util.HashMap;

import me.furt.projectv.voxelengine.block.Block;

import org.lwjgl.util.vector.Vector3f;

public class Region {

    private HashMap<Vector3f, Block> blocks = new HashMap<Vector3f, Block>();
    private int regionX, regionY, regionZ;

    public Region(int regionX, int regionY, int regionZ) {
        this.regionX = regionX;
        this.regionY = regionY;
        this.regionZ = regionZ;
    }

    public void addBlock(int x, int y, int z, Block b) {
        if (x >= 128 || y >= 128 || z >= 128
                || x < 0 || y < 0 || z < 0) {
            return;
        }
        removeBlock(x, y, z);
        blocks.put(new Vector3f(x, y, z), b);
    }

    public boolean hasBlockAt(int x, int y, int z) {
        for (Object oKey : blocks.keySet().toArray()) {
            if (((Vector3f) oKey).x == x
                    && ((Vector3f) oKey).y == y
                    && ((Vector3f) oKey).z == z) {
                return true;
            }
        }
        return false;
    }

    public void removeBlock(int x, int y, int z) {
        if (x >= 128 || y >= 128 || z >= 128
                || x < 0 || y < 0 || z < 0) {
            return;
        }
        for (Object oKey : blocks.keySet().toArray()) {
            if (((Vector3f) oKey).x == x
                    && ((Vector3f) oKey).y == y
                    && ((Vector3f) oKey).z == z) {
                blocks.remove(oKey);
                return;
            }
        }
    }

    public Block getBlock(int x, int y, int z) {
        if (x >= 128 || y >= 128 || z >= 128
                || x < 0 || y < 0 || z < 0) {
            return null;
        }
        for (Object oKey : blocks.keySet().toArray()) {
            if (((Vector3f) oKey).x == x
                    && ((Vector3f) oKey).y == y
                    && ((Vector3f) oKey).z == z) {
                return blocks.get(oKey);
            }
        }
        return null;
    }

    public Block[] getBlocks() {
        Block[] b = new Block[getTotalBlocks()];
        return blocks.values().toArray(b);
    }

    public int getTotalBlocks() {
        return blocks.size();
    }
}
