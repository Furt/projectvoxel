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
    public WorldType worldType;

    public WorldInfo(int id, String name, WorldType worldType) {
        this.id = id;
        this.name = name;
        this.worldType = worldType;
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

    public WorldType getWorldType() {
        return worldType;
    }

    public void setWorldType(WorldType worldType) {
        this.worldType = worldType;
    }
}
