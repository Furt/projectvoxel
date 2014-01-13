package me.furt.projectv.noise.generators;

import me.furt.projectv.libnoise.module.ModuleBase;
import me.furt.projectv.world.Seed;

/**
 * ProjectV
 *
 * @author Furt
 */
public class FlatGen implements Generator {
    private Seed seed;
    
    public FlatGen(Seed seed) {
        this.seed = seed;
    }

    public ModuleBase getBase() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setSeed(Seed seed) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Seed getSeed() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
