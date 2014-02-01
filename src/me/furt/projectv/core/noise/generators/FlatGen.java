package me.furt.projectv.core.noise.generators;

import java.util.logging.Level;
import java.util.logging.Logger;
import me.furt.projectv.libnoise.exception.ExceptionInvalidParam;
import me.furt.projectv.libnoise.module.Billow;
import me.furt.projectv.libnoise.module.ModuleBase;
import me.furt.projectv.libnoise.module.ScaleBias;
import me.furt.projectv.core.Seed;

/**
 * ProjectV
 *
 * @author Furt
 */
public class FlatGen implements Generator {

    private Seed seed;
    private ModuleBase biome;

    public FlatGen(Seed seed) {
        try {
            this.seed = seed;
            Billow baseFlatTerrain = new Billow();
            baseFlatTerrain.setSeed(this.seed.returnInteger());
            baseFlatTerrain.setFrequency(2.0);
            ScaleBias flatTerrain = new ScaleBias(baseFlatTerrain);
            flatTerrain.setScale(0.125);
            flatTerrain.setBias(-0.75);
            this.biome = flatTerrain;
        } catch (ExceptionInvalidParam ex) {
            Logger.getLogger(FlatGen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ModuleBase getBase() {
        return biome;
    }

    public void setSeed(Seed seed) {
        this.seed = seed;
    }

    public Seed getSeed() {
        return seed;
    }
}
