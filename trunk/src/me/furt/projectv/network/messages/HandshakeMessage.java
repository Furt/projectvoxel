package me.furt.projectv.network.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 * Project V
 *
 * @author Furt
 */
@Serializable()
public class HandshakeMessage extends AbstractMessage {

    public int protocol_version;
    public int client_version;
    public int server_version;

    public HandshakeMessage() {
    }

    public HandshakeMessage(int protocol_version, int client_version, int server_version) {
        this.protocol_version = protocol_version;
        this.client_version = client_version;
        this.server_version = server_version;
    }
}
