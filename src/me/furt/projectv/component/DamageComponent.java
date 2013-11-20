/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.furt.projectv.component;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import com.simsilica.es.EntityId;
import com.simsilica.es.PersistentComponent;

/**
 *
 * @author Terry
 */
@Serializable
public class DamageComponent extends AbstractMessage implements PersistentComponent {

    private final float damage;
    private final EntityId target;

    public DamageComponent(EntityId target, float damage) {
        this.target = target;
        this.damage = damage;
    }

    public float getDamage() {
        return damage;
    }

    public EntityId getTarget() {
        return target;
    }
}
