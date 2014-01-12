package me.furt.projectv.noise;

import java.util.logging.Level;
import java.util.logging.Logger;
import me.furt.projectv.libnoise.exception.ExceptionInvalidParam;
import me.furt.projectv.libnoise.module.ModuleBase;
import me.furt.projectv.libnoise.module.ScaleBias;
import me.furt.projectv.libnoise.util.NoiseMap;
import me.furt.projectv.libnoise.util.NoiseMapBuilderPlane;

/**
 * ProjectV
 *
 * @author Furt
 */
public class Noise {

    public NoiseMap getChunkNoise(int regionX, int regionY, int regionZ, ModuleBase biome) {
        try {
            ScaleBias scaleTerrain = new ScaleBias(biome);
            scaleTerrain.setScale(regionY);
            scaleTerrain.setBias(regionY);

            NoiseMap heightMap = new NoiseMap(regionX, regionZ);
            NoiseMapBuilderPlane heightMapBuilder = new NoiseMapBuilderPlane();
            heightMapBuilder.setSourceModule(scaleTerrain);
            heightMapBuilder.setDestNoiseMap(heightMap);
            heightMapBuilder.setDestSize(regionX, regionZ);
            float[] bounds = getBounds(regionX, regionZ);
            heightMapBuilder.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
            heightMapBuilder.build();

            return heightMap;
        } catch (ExceptionInvalidParam ex) {
            Logger.getLogger(Noise.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public float[] getBounds(int x, int z) {
        float[] bounds = new float[4];
        // TODO add code to convert chunk coords to boundries for noise
        bounds[0] = 1f;
        bounds[1] = 2f;
        bounds[2] = 3f;
        bounds[3] = 4f;

        return bounds;
    }
}
