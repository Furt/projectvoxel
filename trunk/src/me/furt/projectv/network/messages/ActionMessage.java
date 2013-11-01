package me.furt.projectv.network.messages;

import com.jme3.network.serializing.Serializable;
import com.jme3.scene.Spatial;
import me.furt.projectv.control.NetworkActionEnabled;
import me.furt.projectv.network.physics.PhysicsSyncMessage;

/**
 * Project V
 *
 * @author Furt
 */
@Serializable()
public class ActionMessage extends PhysicsSyncMessage {

    public final static int NULL_ACTION = 0;
    public final static int JUMP_ACTION = 1;
    public final static int ENTER_ACTION = 2;
    public final static int SHOOT_ACTION = 3;
    public int action;
    public boolean pressed;

    public ActionMessage() {
    }

    public ActionMessage(long id, int action, boolean pressed) {
        this.syncId = id;
        this.action = action;
        this.pressed = pressed;
    }

    @Override
    public void applyData(Object object) {
        ((Spatial) object).getControl(NetworkActionEnabled.class).doPerformAction(action, pressed);
    }
}
