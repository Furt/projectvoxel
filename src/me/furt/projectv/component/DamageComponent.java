/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.furt.projectv.component;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import me.furt.projectv.entity.Entity;

/**
 *
 * @author Terry
 */
@Serializable
public class DamageComponent extends AbstractMessage {

    private final float damage;
    private final Entity target;

    public DamageComponent(Entity target, float damage) {
        this.target = target;
        this.damage = damage;
    }

    public float getDamage() {
        return damage;
    }

    public Entity getTarget() {
        return target;
    }
}
