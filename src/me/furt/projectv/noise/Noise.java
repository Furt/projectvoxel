package me.furt.projectv.noise;

import com.cubes.CubesSettings;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.furt.projectv.libnoise.exception.ExceptionInvalidParam;
import me.furt.projectv.libnoise.module.ModuleBase;
import me.furt.projectv.libnoise.util.NoiseMap;
import me.furt.projectv.libnoise.util.NoiseMapBuilderPlane;
import me.furt.projectv.noise.generators.DesertGen;
import me.furt.projectv.noise.generators.FlatGen;
import me.furt.projectv.noise.generators.ForestGen;
import me.furt.projectv.noise.generators.HillGen;
import me.furt.projectv.noise.generators.MountainGen;
import me.furt.projectv.noise.generators.PlainGen;
import me.furt.projectv.noise.generators.SwampGen;
import me.furt.projectv.noise.generators.WaterGen;
import me.furt.projectv.world.Seed;

/**
 * ProjectV
 *
 * @author Furt
 */
public class Noise {

    private int chunkSizeX;
    private int chunkSizeY;
    private int chunkSizeZ;

    public Noise(CubesSettings settings) {
        this.chunkSizeX = settings.getChunkSizeX();
        this.chunkSizeY = settings.getChunkSizeY();
        this.chunkSizeZ = settings.getChunkSizeZ();
    }

    public NoiseMap getChunkNoise(int chunkLocX, int chunkLocZ, Seed seed, BiomeType biomeType) {
        try {
            ModuleBase biome = getBiome(chunkSizeY, seed, biomeType);
            float[] bounds = getBounds(chunkLocX, chunkLocZ);

            NoiseMap heightMap = new NoiseMap(chunkSizeX, chunkSizeZ);
            NoiseMapBuilderPlane heightMapBuilder = new NoiseMapBuilderPlane();
            heightMapBuilder.setSourceModule(biome);
            heightMapBuilder.setDestNoiseMap(heightMap);
            heightMapBuilder.setDestSize(chunkSizeX, chunkSizeZ);

            heightMapBuilder.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
            heightMapBuilder.build();

            return heightMap;
        } catch (ExceptionInvalidParam ex) {
            Logger.getLogger(Noise.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public float[] getBounds(int chunkLocX, int chunkLocZ) {
        float[] bounds = new float[4];
        bounds[0] = chunkLocX * chunkSizeX;
        bounds[1] = ((chunkLocX * chunkSizeX) + chunkSizeX);
        bounds[2] = chunkLocZ * chunkSizeZ;
        bounds[3] = ((chunkLocZ * chunkSizeZ) + chunkSizeZ);

        return bounds;
    }

    public ModuleBase getBiome(int chunkSizeY, Seed seed, BiomeType biomeType) {
        // TODO finish gen classes to finish this method
        ModuleBase mb = null;
        if (biomeType.equals(BiomeType.DESERT)) {
            DesertGen gen = new DesertGen(seed);
            mb = gen.getBase();
        } else if (biomeType.equals(BiomeType.FOREST)) {
           ForestGen gen = new ForestGen(seed);
           mb = gen.getBase();
        } else if (biomeType.equals(BiomeType.HILLS)) {
            HillGen gen = new HillGen(seed);
            mb = gen.getBase();
        } else if (biomeType.equals(BiomeType.MOUNTAINS)) {
            MountainGen gen = new MountainGen(seed);
            mb = gen.getBase();
        } else if (biomeType.equals(BiomeType.PLAINS)) {
            PlainGen gen = new PlainGen(seed);
            mb = gen.getBase();
        } else if (biomeType.equals(BiomeType.SWAMP)) {
            SwampGen gen = new SwampGen(seed);
            mb = gen.getBase();
        } else if (biomeType.equals(BiomeType.WATER)) {
            WaterGen gen = new WaterGen(seed);
            mb = gen.getBase();
        } else {
            // failsafe, generate flat terrain
            FlatGen gen = new FlatGen(seed);
            mb = gen.getBase();
        }

        return mb;
    }
    
    // How im gonna currently do the scale this might not even be needed.
    public ModuleBase setScale(ModuleBase biome, BiomeType biomeType) {
        return null;
    }
}
