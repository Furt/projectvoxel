package me.furt.projectv.network.physics;

/**
 *
 * @author normenhansen
 */
public interface SyncMessageValidator {

    public boolean checkMessage(PhysicsSyncMessage message);
}
