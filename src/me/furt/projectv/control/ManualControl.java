package me.furt.projectv.control;

import com.jme3.math.Vector3f;

/**
 * Project V
 *
 * @author Furt
 */
public interface ManualControl extends MovementControl {

    public void steerX(float value);

    public void steerY(float value);

    public Vector3f getAimDirection();

    public Vector3f getLocation();

    public void moveX(float value);

    public void moveY(float value);

    public void moveZ(float value);

    public void performAction(int button, boolean pressed);
}
