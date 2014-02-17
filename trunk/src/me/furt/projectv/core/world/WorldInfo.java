package me.furt.projectv.core.world;

import me.furt.projectv.core.Seed;
import me.furt.projectv.util.Vector3Int;

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
    public Vector3Int regionLocation;
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

    public Vector3Int getRegionLocation() {
        return regionLocation;
    }

    public void setRegionLocation(Vector3Int regionLocation) {
        this.regionLocation = regionLocation;
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
