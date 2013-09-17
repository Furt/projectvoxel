/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.furt.projectv.world;

import java.util.ArrayList;
import java.util.List;
import me.furt.projectv.util.Vector3Int;

/**
 *
 * @author Terry
 */
public class World implements BlockAccess {

    public List loadedEntityList = new ArrayList();
    
    /**
     * Entities marked for removal.
     */
    private List entityRemoval = new ArrayList();
    
    /**
     * Array list of players in the world.
     */
    public List playerEntities = new ArrayList();

    public int getBlockId(int x, int y, int z) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getBlockId(Vector3Int v) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isBlockAir(int x, int y, int z) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isBlockAir(Vector3Int v) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
