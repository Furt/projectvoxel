package me.furt.projectv.core.world;

import com.cubes.Util;
import com.cubes.Vector3Int;
import com.jme3.app.Application;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import java.util.ArrayList;
import java.util.Iterator;
import me.furt.projectv.core.block.Block;
import me.furt.projectv.core.block.BlockManager;

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
    private Application app;
    private BlockManager bm;
    private Node worldNode;

    public World(Application app) {
        this.worldInfo = new WorldInfo("name");
        this.worldNode = new Node(worldInfo.getName());
        this.app = app;
        this.bm = new BlockManager();
        // this might need to be elsewhere
        bm.registerDefaults();
    }
    
    public BlockManager getBlockManager() {
        return bm;
    }
    
    public Application getApp() {
        return this.app;
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
    
    public Vector3Int getPointedBlockLocation(Vector3f collisionContactPoint, boolean getNeighborLocation) {
        Vector3f collisionLocation = Util.compensateFloatRoundingErrors(collisionContactPoint);
        Vector3Int blockLocation = new Vector3Int(
                (int) collisionLocation.getX(),
                (int) collisionLocation.getY(),
                (int) collisionLocation.getZ());
        if ((getBlock(blockLocation) != null) == getNeighborLocation) {
            if ((collisionLocation.getX() % 1) == 0) {
                blockLocation.subtractLocal(1, 0, 0);
            } else if ((collisionLocation.getY() % 1) == 0) {
                blockLocation.subtractLocal(0, 1, 0);
            } else if ((collisionLocation.getZ() % 1) == 0) {
                blockLocation.subtractLocal(0, 0, 1);
            }
        }
        return blockLocation;
    }
    
    public Block getBlock(Vector3Int loc) {
        Region region;
        Chunk c;
        Iterator i = regions.iterator();
        while (i.hasNext()) {
            region = (Region) i.next();
            if(region.getChunkFromBlock(loc) != null) {
                c = region.getChunkFromBlock(loc);
                return c.getBlock(loc);
            }
        }
        return null;
    }
}
