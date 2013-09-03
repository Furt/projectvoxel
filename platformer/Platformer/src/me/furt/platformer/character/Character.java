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
    private String name;
    private Stats stats;
    private int health;
    private boolean dead;
    private Vector3f position;
    
    public Character(String name, Stats stats, Vector3f position) {
        this.name = name;
        this.stats = stats;
        this.position = position;
        this.dead = false;
        this.health = stats.getStamina()*20;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Stats getStats() {
        return this.stats;
    }
    
    public Vector3f getPosition() {
        return this.position;
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
