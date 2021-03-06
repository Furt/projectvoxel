package com.cubes;

import com.jme3.math.Vector3f;

public class BlockNavigator {

    /**
     *
     * @param location
     * @param face
     * @return
     */
    public static Vector3Int getNeighborBlockLocalLocation(Vector3Int location, Block.Face face) {
        Vector3Int neighborLocation = getNeighborBlockLocation_Relative(face);
        neighborLocation.addLocal(location);
        return neighborLocation;
    }

    /**
     *
     * @param face
     * @return
     */
    public static Vector3Int getNeighborBlockLocation_Relative(Block.Face face) {
        Vector3Int neighborLocation = new Vector3Int();
        switch (face) {
            case Top:
                neighborLocation.set(0, 1, 0);
                break;

            case Bottom:
                neighborLocation.set(0, -1, 0);
                break;

            case Left:
                neighborLocation.set(-1, 0, 0);
                break;

            case Right:
                neighborLocation.set(1, 0, 0);
                break;

            case Front:
                neighborLocation.set(0, 0, 1);
                break;

            case Back:
                neighborLocation.set(0, 0, -1);
                break;
        }
        return neighborLocation;
    }

    /**
     *
     * @param blockTerrain
     * @param collisionContactPoint
     * @param getNeighborLocation
     * @return
     */
    public static Vector3Int getPointedBlockLocation(TerrainControl blockTerrain, Vector3f collisionContactPoint, boolean getNeighborLocation) {
        Vector3f collisionLocation = Util.compensateFloatRoundingErrors(collisionContactPoint);
        Vector3Int blockLocation = new Vector3Int(
                (int) (collisionLocation.getX() / blockTerrain.getSettings().getBlockSize()),
                (int) (collisionLocation.getY() / blockTerrain.getSettings().getBlockSize()),
                (int) (collisionLocation.getZ() / blockTerrain.getSettings().getBlockSize()));
        if ((blockTerrain.getBlock(blockLocation) != null) == getNeighborLocation) {
            if ((collisionLocation.getX() % blockTerrain.getSettings().getBlockSize()) == 0) {
                blockLocation.subtractLocal(1, 0, 0);
            } else if ((collisionLocation.getY() % blockTerrain.getSettings().getBlockSize()) == 0) {
                blockLocation.subtractLocal(0, 1, 0);
            } else if ((collisionLocation.getZ() % blockTerrain.getSettings().getBlockSize()) == 0) {
                blockLocation.subtractLocal(0, 0, 1);
            }
        }
        return blockLocation;
    }
}
