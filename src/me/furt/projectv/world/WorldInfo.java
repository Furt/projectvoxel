package me.furt.projectv.world;

import com.cubes.Vector3Int;

/**
 * ProjectV
 *
 * @author Furt
 */
public class WorldInfo {

    public int id;
    public String name;
    public boolean globalPVP;
    public Vector3Int spawnLocation;
    public boolean monstersAllowed;
    public boolean crittersAllowed;
    public Seed seed;

    public WorldInfo(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public WorldInfo(String config) {
        // For yml world settings
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean globalPvpEnabled() {
        return globalPVP;
    }

    public void setGlobalPVP(boolean pvp) {
        this.globalPVP = pvp;
    }

    public Vector3Int getSpawnLocation() {
        return spawnLocation;
    }

    public void setSpawnLocation(Vector3Int spawnLocation) {
        this.spawnLocation = spawnLocation;
    }

    public boolean isMonstersAllowed() {
        return monstersAllowed;
    }

    public void setMonstersAllowed(boolean monstersAllowed) {
        this.monstersAllowed = monstersAllowed;
    }

    public boolean isCrittersAllowed() {
        return crittersAllowed;
    }

    public void setCrittersAllowed(boolean crittersAllowed) {
        this.crittersAllowed = crittersAllowed;
    }
    
    public Seed getSeed() {
        return seed;
    }
    
    public void setSeed(Seed seed) {
        this.seed = seed;
    }
}
