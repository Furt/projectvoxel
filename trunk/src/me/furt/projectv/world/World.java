package me.furt.projectv.world;

import com.cubes.Vector3Int;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * ProjectV
 *
 * @author Furt
 */
public class World extends AbstractControl {

    private int id;
    private String name;
    private WorldInfo worldInfo;
    private ArrayList<Region> regions = new ArrayList<Region>();

    public World(WorldInfo worldInfo) {
        this.worldInfo = worldInfo;
    }

    public WorldInfo getWorldInfo() {
        return worldInfo;
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

    public void setWorldInfo(WorldInfo worldInfo) {
        this.worldInfo = worldInfo;
    }

    public ArrayList<Region> getRegions() {
        return regions;
    }

    public Region getRegion(Vector3Int regionLoc) {
        Iterator i = regions.iterator();
        while (i.hasNext()) {
            Region region = (Region) i.next();
            if (region.getLocation().equals(regionLoc)) {
                return region;
            }
        }
        return null;
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
        while (i.hasNext()) {
            Region region = (Region) i.next();
            Chunk chunk = region.getChunkFromBlock(block);
            if (chunk != null) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void controlUpdate(float tpf) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Control cloneForSpatial(Spatial spatial) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
