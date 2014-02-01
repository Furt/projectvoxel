package me.furt.projectv.core.noise.generators;

import me.furt.projectv.libnoise.module.ModuleBase;
import me.furt.projectv.core.Seed;

/**
 * ProjectV
 *
 * @author Furt
 */
public class SwampGen implements Generator {
    private Seed seed;
    
    public SwampGen(Seed seed) {
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
