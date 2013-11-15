package me.furt.projectv.worldtest;

import com.cubes.Vector3Int;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * ProjectV
 *
 * @author Furt
 */
public class World {

    private WorldInfo worldInfo;
    private ArrayList<Region> regions = new ArrayList<Region>();

    public World(WorldInfo worldInfo) {
        this.worldInfo = worldInfo;
    }

    public WorldInfo getWorldInfo() {
        return worldInfo;
    }

    public void setWorldInfo(WorldInfo worldInfo) {
        this.worldInfo = worldInfo;
    }

    public ArrayList<Region> getRegions() {
        return regions;
    }

    public void setRegions(ArrayList<Region> regions) {
        this.regions = regions;
    }

    public void addRegion(Region region) {
        this.regions.add(region);
    }

    public void removeRegion(Region region) {
        this.regions.remove(region);
    }
    
    public boolean chunkExists(Vector3Int block) {
        Iterator i = regions.iterator();
        while(i.hasNext()) {
            Region region = (Region)i.next();
            Chunk chunk = region.getChunkFromBlock(block);
            if (chunk != null) {
                return true;
            }
        }
        return false;
    }
}
