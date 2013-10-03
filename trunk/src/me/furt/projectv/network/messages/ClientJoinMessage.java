package me.furt.projectv.network.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 * sent from client to join server
 * @author Terry
 */
@Serializable()
public class ClientJoinMessage extends AbstractMessage{
    public String name;
    public String pass;

    public ClientJoinMessage() {
    }

    public ClientJoinMessage(String name, String pass) {
        this.name = name;
        this.pass = pass;
    }

}
