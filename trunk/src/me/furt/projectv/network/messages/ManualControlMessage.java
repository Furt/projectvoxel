package me.furt.projectv.network.messages;

import com.jme3.network.serializing.Serializable;
import com.jme3.scene.Spatial;
import me.furt.projectv.control.NetworkedManualControl;
import me.furt.projectv.network.physics.PhysicsSyncMessage;

/**
 * Project V
 *
 * @author Furt
 */
@Serializable()
public class ManualControlMessage extends PhysicsSyncMessage {

    public float aimX;
    public float aimY;
    public float moveX;
    public float moveY;
    public float moveZ;

    public ManualControlMessage() {
    }

    public ManualControlMessage(ManualControlMessage msg) {
//        setReliable(false);
        this.syncId = msg.syncId;
        this.aimX = msg.aimX;
        this.aimY = msg.aimY;
        this.moveX = msg.moveX;
        this.moveY = msg.moveY;
        this.moveZ = msg.moveZ;
    }

    public ManualControlMessage(long id, float aimX, float aimY, float moveX, float moveY, float moveZ) {
//        setReliable(false);
        this.syncId = id;
        this.aimX = aimX;
        this.aimY = aimY;
        this.moveX = moveX;
        this.moveY = moveY;
        this.moveZ = moveZ;
    }

    @Override
    public void applyData(Object object) {
        NetworkedManualControl netControl = ((Spatial) object).getControl(NetworkedManualControl.class);
        assert (netControl != null);
        netControl.doMoveX(moveX);
        netControl.doMoveY(moveY);
        netControl.doMoveZ(moveZ);
        netControl.doSteerX(aimX);
        netControl.doSteerY(aimY);
    }
}
