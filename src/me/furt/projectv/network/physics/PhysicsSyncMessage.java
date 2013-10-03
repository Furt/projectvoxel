package me.furt.projectv.network.physics;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 * Abstract physics sync message, can be used with PhysicsSyncManager, contains
 * timestamp and id. Override applyData method to apply the data to the object
 * with the specific id when it arrives.
 * @author normenhansen
 */
@Serializable()
public abstract class PhysicsSyncMessage extends AbstractMessage {

    public long syncId = -1;
    public double time;

    public PhysicsSyncMessage() {
        super(true);
    }

    public PhysicsSyncMessage(long id) {
        super(true);
        this.syncId = id;
    }
    
    public abstract void applyData(Object object);
}
