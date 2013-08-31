/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.furt.platformer;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import javax.vecmath.*;

/**
 *
 * @author Terry
 */
@Serializable
public class Player extends AbstractMessage {
    private int health;
    private String rawName;
    private Vector3f position;
    
    public int getHealth() {
        return health;
    }
    
    public void setHealth(int i) {
        this.health = i;
    }
    
    public String getRawName() {
        return rawName;
    }
    
    public void setRawName(String s) {
        this.rawName = s;
    }
    
    public Vector3f getPosition() {
        return position;
    }
    
    public void setPosition(Vector3f v) {
        this.position = v;
    }
    
    public void setPosition(float x, float y, float z) {
        this.position = new Vector3f(x, y, z);
    }
}
