/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.furt.projectv.entity;

/**
 *
 * @author Terry
 */
public abstract class Entity {

    /* Entity id  */
    public int id;
    /* If entity is allawed to spawn at current location this will return true */
    public boolean canSpawn;
    /* Use for if dead or not visible in world */
    public boolean inactive;
    /* Previous Location */
    public double prevPosX;
    public double prevPosY;
    public double prevPosZ;
    /* Current Location */
    public double posX;
    public double posY;
    public double posZ;
    /* The width and height of the entity  */
    public float width;
    public float height;

    @Override
    public boolean equals(Object object) {
        return object instanceof Entity ? ((Entity) object).id == this.id : false;
    }

    @Override
    public int hashCode() {
        return this.id;
    }
}
