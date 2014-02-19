package me.furt.projectv.core.world;

import com.jme3.app.Application;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import java.util.ArrayList;
import java.util.Iterator;
import me.furt.projectv.core.Seed;
import me.furt.projectv.core.block.Block;
import me.furt.projectv.core.block.BlockManager;
import me.furt.projectv.core.render.ChunkListener;
import me.furt.projectv.util.Util;
import me.furt.projectv.util.Vector3Int;

/**
 * ProjectV
 *
 * @author Furt
 */
public class World extends AbstractControl {

    private int id;
    private String name;
    private WorldInfo worldInfo;
    private WorldSettings worldSettings;
    private ArrayList<Region> regions = new ArrayList<Region>();
    private Application app;
    private BlockManager blockManager;
    private ArrayList<ChunkListener> chunkListeners = new ArrayList<ChunkListener>();

    public World(Application app, WorldInfo worldInfo) {
        this.app = app;
        this.worldInfo = worldInfo;
        this.blockManager = new BlockManager();
        this.blockManager.registerDefaults();
    }

    public World(Application app) {
        WorldInfo worldInfo1 = new WorldInfo("world");
        worldInfo1.setCrittersAllowed(false);
        worldInfo1.setMonstersAllowed(false);
        worldInfo1.setSpawnLocation(new Vector3Int(0, 0, 0));
        worldInfo1.setSeed(new Seed("test"));
        this.worldSettings = new WorldSettings(app);
        this.app = app;
        this.worldInfo = worldInfo1;
        this.blockManager = new BlockManager();
        this.blockManager.registerDefaults();
    }

    public BlockManager getBlockManager() {
        return blockManager;
    }

    public Application getApp() {
        return this.app;
    }

    public WorldInfo getWorldInfo() {
        return worldInfo;
    }

    public void setWorldSettings(WorldSettings worldSettings) {
        this.worldSettings = worldSettings;
    }

    public WorldSettings getWorldSettings() {
        return worldSettings;
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

    public void setSeed(Seed seed) {
        this.worldInfo.setSeed(seed);
    }

    public Seed getSeed() {
        return this.worldInfo.getSeed();
    }

    public ArrayList<Region> getRegions() {
        return regions;
    }

    public Region getRegion(Vector3Int regionLoc) {
        for (Region region : regions) {
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
        for (Region region : regions) {
            if (region.getChunkFromBlock(block) != null) {
                return true;
            }
        }
        return false;
    }

    public Chunk getChunkFromBlock(Vector3Int block) {
        if (chunkExists(block)) {
            for (Region region : regions) {
                return region.getChunkFromBlock(block);
            }
        }
        return null;
    }

    @Override
    protected void controlUpdate(float tpf) {
        updateSpatial();
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

    @Override
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
            if (region.getChunkFromBlock(loc) != null) {
                c = region.getChunkFromBlock(loc);
                return c.getBlock(loc);
            }
        }
        return null;
    }

    public void addChunkListener(ChunkListener chunkListener) {
        chunkListeners.add(chunkListener);
    }

    public void removeChunkListener(ChunkListener chunkListener) {
        chunkListeners.remove(chunkListener);
    }

    private boolean updateSpatial() {
        boolean wasUpdatedNeeded = false;
        for (Region region : regions) {
            for (int x = 0; x < region.getChunkList().length; x++) {
                for (int y = 0; y < region.getChunkList()[0].length; y++) {
                    for (int z = 0; z < region.getChunkList()[0][0].length; z++) {
                        Chunk chunk = region.getChunkList()[x][y][z];
                        if (chunk.updateSpatial()) {
                            wasUpdatedNeeded = true;
                            for (int k = 0; k < chunkListeners.size(); k++) {
                                ChunkListener listener = chunkListeners.get(k);
                                listener.onSpatialUpdated(chunk);
                            }
                        }
                    }
                }
            }
        }
        return wasUpdatedNeeded;
    }

    public WorldSettings getSettings() {
        return this.worldSettings;
    }

    public Vector3Int getSpawnLocation() {
        return worldInfo.getSpawnLocation();
    }

    public void setSpawnLocation(Vector3Int location) {
        worldInfo.setSpawnLocation(location);
    }
}
