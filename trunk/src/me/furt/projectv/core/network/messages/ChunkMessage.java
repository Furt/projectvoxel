package me.furt.projectv.core.network.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Terry
 */
@Serializable()
public class ChunkMessage extends AbstractMessage {
    
    private Byte[] bytes;
    
    public ChunkMessage() {}
    
    public ChunkMessage(Byte[] bytes) {
        this.bytes = bytes;
    }
    
    public Byte[] getData() {
        return this.bytes;
    }
}
