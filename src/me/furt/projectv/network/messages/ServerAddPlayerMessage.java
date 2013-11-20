package me.furt.projectv.network.messages;

import com.jme3.network.serializing.Serializable;
import me.furt.projectv.network.physics.PhysicsSyncMessage;

/**
 * Project V
 *
 * @author Furt
 */
@Serializable()
public class ServerAddPlayerMessage extends PhysicsSyncMessage {

    public long playerId;
    public String name;
    public int group_id;
    public int ai_id;

    public ServerAddPlayerMessage() {
    }

    public ServerAddPlayerMessage(long id, String name, int group_id, int ai_id) {
        this.syncId = -1;
        this.playerId = id;
        this.name = name;
        this.group_id = group_id;
        this.ai_id = ai_id;
    }

    @Override
    public void applyData(Object object) {
    }
}
