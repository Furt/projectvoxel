package me.furt.projectv.control;

import com.jme3.scene.control.Control;

/**
 * Project V
 *
 * @author Furt
 */
public interface NetworkActionEnabled extends Control {

    public void doPerformAction(int action, boolean activate);
}
