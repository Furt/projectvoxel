/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.furt.projectv.core.noise.generators;

import me.furt.projectv.libnoise.module.ModuleBase;
import me.furt.projectv.core.Seed;

/**
 *
 * @author Furt
 */
public interface Generator {
    public ModuleBase getBase();
    public void setSeed(Seed seed);
    public Seed getSeed();
}
