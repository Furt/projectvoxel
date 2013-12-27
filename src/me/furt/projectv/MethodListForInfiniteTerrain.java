package me.furt.projectv;

import com.cubes.ChunkControl;
import com.cubes.Vector3Int;
import com.jme3.math.Vector3f;
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
    int maxChunks = 33; // the amount of chunks that can be loaded at once
    int chunkRadius = 5; // the chunk radius around the player
    
    // Below is a list of stuff that will need to be changed in TerrainControl
    
    // To have the ability to do infinite terrain we should step away from - example below
    private ChunkControl[][][] chunks;
    
    // Instead use hashmap like the below example. This way we can add new chunks without having to hard set a max amount
    // renamed to chunkList to prevent errors in the class
    private HashMap<Vector3Int, ChunkControl> chunkList = new HashMap<Vector3Int, ChunkControl>();
    
    // But if done this way initializeChunks and setSpatial methods will have to be dont differently
    // and im now sure how to do that considering we want a dynamic chunklist and not a static one.

}
