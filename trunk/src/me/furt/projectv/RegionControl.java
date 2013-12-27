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

        ARTIC(0),
        SUB_ARTIC(1),
        TEMPERATE(2),
        SUB_TROPICAL(3),
        TROPICAL(4);
        
        private int id = 0;
        
        private Climate(int id) {
            this.id = id;
        }
        public int getId() {
            return this.id;
        }
    }

    public enum Biome {

        WATER(0),
        SWAMP(1),
        DESERT(2),
        PLAINS(3),
        FOREST(4),
        HILLS(5),
        MOUNTAINS(6);
        
        private int id = 0;
        
        private Biome(int id) {
            this.id = id;
        }
        
        public int getId() {
            return this.id;
        }
    }

    public enum TerrainType {

        PRIMARY(0),
        SECONDARY(1),
        TERTIARY(2),
        WILDCARD(3);
        
        private int id = 0;
        
        private TerrainType(int id) {
            this.id = id;
        }
        
        public int getId() {
            return this.id;
        }
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
