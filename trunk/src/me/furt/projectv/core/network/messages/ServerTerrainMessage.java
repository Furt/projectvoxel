package me.furt.projectv.core.network.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 * Project V
 *
 * @author Furt
 */
@Serializable()
public class ServerTerrainMessage extends AbstractMessage {

    private byte[] data;

    public ServerTerrainMessage() {
    }

    public ServerTerrainMessage(byte[] data) {
        this.data = data;
    }

    public byte[] getTerrainData() {
        return data;
    }
}
