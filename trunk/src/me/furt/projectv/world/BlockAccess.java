/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.furt.projectv.world;

import me.furt.projectv.util.Vector3Int;

/**
 * BlockAccess Gathers Block data from world calls
 *
 * @author Terry
 */
public interface BlockAccess {

    /**
     * Returns block id at location
     */
    int getBlockId(int x, int y, int z);

    int getBlockId(Vector3Int v);

    /**
     * Returns true if not block is at location
     */
    boolean isBlockAir(int x, int y, int z);

    boolean isBlockAir(Vector3Int v);
}
