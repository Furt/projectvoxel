package me.furt.projectv.worldtest;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 * ProjectV
 *
 * @author Furt
 */
@Serializable
public class TerrainMessage extends AbstractMessage {
    private byte[] worldData;
    
    public TerrainMessage() {}
    
    public TerrainMessage(byte[] worldData) {
        this.worldData = worldData;
    }
    
    public byte[] getWorldData() {
        return this.worldData;
    }

}
