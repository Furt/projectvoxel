package me.furt.projectv.noise;

import java.util.logging.Level;
import java.util.logging.Logger;
import me.furt.projectv.libnoise.exception.ExceptionInvalidParam;
import me.furt.projectv.libnoise.module.ModuleBase;
import me.furt.projectv.libnoise.util.NoiseMap;
import me.furt.projectv.libnoise.util.NoiseMapBuilderPlane;
import me.furt.projectv.world.Seed;

/**
 * ProjectV
 *
 * @author Furt
 */
public class Noise {

    public NoiseMap getChunkNoise(int chunkX, int chunkZ, Seed seed, BiomeType biomeType) {
        try {
            ModuleBase biome = getBiome(seed, biomeType);
            float[] bounds = getBounds(chunkX, chunkZ);
            
            NoiseMap heightMap = new NoiseMap(16, 16);
            NoiseMapBuilderPlane heightMapBuilder = new NoiseMapBuilderPlane();
            heightMapBuilder.setSourceModule(biome);
            heightMapBuilder.setDestNoiseMap(heightMap);
            heightMapBuilder.setDestSize(16, 16);
            
            heightMapBuilder.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
            heightMapBuilder.build();

            return heightMap;
        } catch (ExceptionInvalidParam ex) {
            Logger.getLogger(Noise.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public float[] getBounds(int chunkX, int chunkZ) {
        float[] bounds = new float[4];
        // TODO add code to convert chunk coords to boundries for noise
        bounds[0] = chunkX*16-1;
        bounds[1] = 2f;
        bounds[2] = 3f;
        bounds[3] = 4f;

        return bounds;
    }
    
    public ModuleBase getBiome(Seed seed, BiomeType biomeType) {
        // TODO finish gen classes to finish this method
        ModuleBase mb = null;
        if(biomeType.equals(BiomeType.DESERT)) {
            
        } else if (biomeType.equals(BiomeType.FOREST)) {
            // mb = null;
        } else if (biomeType.equals(BiomeType.HILLS)) {
            // mb = null;
        } else if (biomeType.equals(BiomeType.MOUNTAINS)) {
            // mb = null;
        } else if (biomeType.equals(BiomeType.PLAINS)) {
            // mb = null;
        } else if (biomeType.equals(BiomeType.SWAMP)) {
            // mb = null;
        } else if (biomeType.equals(BiomeType.WATER)) {
            // mb = null;
        } else {
            // failsafe, generate flat terrain
        }
        
        return mb;
    }
}
