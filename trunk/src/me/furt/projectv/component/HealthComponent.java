/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.furt.projectv.component;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Terry
 */
@Serializable
public class HealthComponent extends AbstractMessage {

    public float health;

    public HealthComponent(float health) {
        this.health = health;
    }

    public float getHealth() {
        return health;
    }
}
