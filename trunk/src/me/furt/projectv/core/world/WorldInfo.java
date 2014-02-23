package me.furt.projectv.core.world;

import me.furt.projectv.core.Seed;
import me.furt.projectv.util.Vector3i;

/**
 * ProjectV
 *
 * @author Furt
 */
public class WorldInfo {

    public int id;
    public String name;
    public boolean globalPVP;
    public Vector3i spawnLocation;
    public Vector3i regionLocation;
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

    public Vector3i getSpawnLocation() {
        return spawnLocation;
    }

    public void setSpawnLocation(Vector3i spawnLocation) {
        this.spawnLocation = spawnLocation;
    }

    public Vector3i getRegionLocation() {
        return regionLocation;
    }

    public void setRegionLocation(Vector3i regionLocation) {
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
