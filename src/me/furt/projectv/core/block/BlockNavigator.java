package me.furt.projectv.core.block;

import com.jme3.math.Vector3f;
import me.furt.projectv.core.world.World;
import me.furt.projectv.util.Util;
import me.furt.projectv.util.Vector3i;

/**
 * ProjectV
 *
 * @author Furt
 */
public class BlockNavigator {

    public static Vector3i getNeighborBlockLocalLocation(Vector3i location, Block.Face face) {
        Vector3i neighborLocation = getNeighborBlockLocation_Relative(face);
        neighborLocation.addLocal(location);
        return neighborLocation;
    }

    public static Vector3i getNeighborBlockLocation_Relative(Block.Face face) {
        Vector3i neighborLocation = new Vector3i();
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

    public static Vector3i getPointedBlockLocation(World world, Vector3f collisionContactPoint, boolean getNeighborLocation) {
        Vector3f collisionLocation = Util.compensateFloatRoundingErrors(collisionContactPoint);
        Vector3i blockLocation = new Vector3i(
                (int) (collisionLocation.getX() / world.getSettings().getBlockSize()),
                (int) (collisionLocation.getY() / world.getSettings().getBlockSize()),
                (int) (collisionLocation.getZ() / world.getSettings().getBlockSize()));
        if ((world.getBlock(blockLocation) != null) == getNeighborLocation) {
            if ((collisionLocation.getX() % world.getSettings().getBlockSize()) == 0) {
                blockLocation.subtractLocal(1, 0, 0);
            } else if ((collisionLocation.getY() % world.getSettings().getBlockSize()) == 0) {
                blockLocation.subtractLocal(0, 1, 0);
            } else if ((collisionLocation.getZ() % world.getSettings().getBlockSize()) == 0) {
                blockLocation.subtractLocal(0, 0, 1);
            }
        }
        return blockLocation;
    }
}
