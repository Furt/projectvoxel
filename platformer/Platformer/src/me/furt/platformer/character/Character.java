/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.furt.platformer.character;

import com.jme3.math.Vector3f;

/**
 *
 * @author Terry
 */
public class Character {
    public String name;
    public Stats stats;
    public int health;
    public boolean dead;
    public Vector3f position;
    
    public Character(String name, Stats stats, Vector3f position) {
        this.name = name;
        this.stats = stats;
        this.position = position;
        this.dead = false;
        this.health = stats.getStamina()*20;
    }
    
    public int getDefense() {
        // some example logic
        int defense = stats.getStrength()/2 -9;
        if(defense > 1) {
            return defense;
        } else {
            return 0;
        }
    }
    
    public int getHealth() {
        return health;
    }
    
    public boolean isDead() {
        return dead;
    }
    
    public void setDead(boolean b) {
        this.dead = b;
    }
    
}
