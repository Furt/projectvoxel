package me.furt.projectv;

import com.cubes.Block;
import com.cubes.ChunkControl;
import com.cubes.TerrainControl;
import com.cubes.Vector3Int;
import com.jme3.math.Vector3f;
import java.io.BufferedOutputStream;
import java.util.HashMap;

/**
 * ProjectV
 *
 * @author Furt
 */
public class MethodListForInfiniteTerrain {
    // all methods and variables are just examples and will prob not be accurate
    
    // Variables that might be needed
    Vector3f camCoords = new Vector3f(0f,0f,0f);
    Vector3Int blockCoords = new Vector3Int(0,0,0);
    int maxChunks = 50; // the amount of chunks that can be loaded at once
    int chunkRadius = 5; // the radius for checking if a chunk is loaded
    
    // Below is a list of stuff that will need to be changed in TerrainControl
    
    // To have the ability to do infinite terrain we should step away from - example below
    private ChunkControl[][][] chunks;
    
    // Instead use hashmap like the below example. This way we can add new chunks without having to hard set a max amount
    // renamed to chunkList to prevent errors in the class
    private HashMap<Vector3Int, ChunkControl> chunkList = new HashMap<Vector3Int, ChunkControl>();
    
    // But if done this way initializeChunks and setSpatial methods will have to be dont differently
    // and im now sure how to do that considering we want a dynamic chunklist and not a static one.
    
    
    // Maybe it can be done something like this
    // original - added TerrainControl to method to silence the error 
    public void setBlockArea(TerrainControl tc, Vector3Int location, Vector3Int size, Class<? extends Block> blockClass) {
        Vector3Int tmpLocation = new Vector3Int();
        for (int x = 0; x < size.getX(); x++) {
            for (int y = 0; y < size.getY(); y++) {
                for (int z = 0; z < size.getZ(); z++) {
                    tmpLocation.set(location.getX() + x, location.getY() + y, location.getZ() + z);
                    tc.setBlock(tmpLocation, blockClass);
                }
            }
        }
    }
    
    // revised using hashmap - methods slightly renamed for the sake of errors
    public void setBlockAreas(TerrainControl tc, Vector3Int location, Vector3Int size, Class<? extends Block> blockClass) {
        Vector3Int tmpLocation = new Vector3Int();
        for (int x = 0; x < size.getX(); x++) {
            for (int y = 0; y < size.getY(); y++) {
                for (int z = 0; z < size.getZ(); z++) {
                    tmpLocation.set(location.getX() + x, location.getY() + y, location.getZ() + z);
                    ChunkControl tmpChunk = chunkList.get(tmpLocation);
                    if(tmpChunk == null) {
                        tmpChunk = new ChunkControl(tc, tmpLocation.getX(), tmpLocation.getY(), tmpLocation.getZ());
                        chunkList.put(tmpLocation, tmpChunk);
                    }
                    tc.setBlock(tmpLocation, blockClass);
                }
            }
        }
    }
    
    // Not sure how this would effect performance.

    
    
    // server <-> client world data stream
   // new DataOutputStream( new BufferedOutputStream( new GZIPOutputStream( /* compressed chunk data */ ) ) );
}
