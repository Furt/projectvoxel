package me.furt.projectv.network.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 * Project V
 *
 * @author Furt
 */
@Serializable()
public class ServerJoinMessage extends AbstractMessage {

    public boolean rejected;
    public long id;
    public int group_id;
    public String name;

    public ServerJoinMessage() {
    }

    public ServerJoinMessage(long id, int group_id, String name, boolean rejected) {
        this.rejected = rejected;
        this.id = id;
        this.group_id = group_id;
        this.name = name;
    }
}
