package me.furt.projectv;

import com.cubes.network.BitInputStream;
import com.cubes.network.BitOutputStream;
import com.cubes.network.BitSerializable;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import java.io.IOException;

/**
 * ProjectV
 *
 * @author Furt
 */
public class RegionControl extends AbstractControl implements BitSerializable {

    public enum Climate {

        ARTIC,
        SUB_ARTIC,
        TEMPERATE,
        SUB_TROPICAL,
        TROPICAL;
    }

    public enum Biome {

        WATER,
        SWAMP,
        DESERT,
        PLAINS,
        FOREST,
        HILLS,
        MOUNTAINS;
    }

    public enum TerrainType {

        PRIMARY,
        SECONDARY,
        TERTIARY,
        WILDCARD;
    }

    @Override
    protected void controlUpdate(float tpf) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Control cloneForSpatial(Spatial spatial) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void write(BitOutputStream outputStream) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void read(BitInputStream inputStream) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
