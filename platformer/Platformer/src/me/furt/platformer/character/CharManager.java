/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.furt.platformer.character;

import com.jme3.math.Vector3f;
import java.util.HashMap;
import me.furt.platformer.character.Character;

/**
 *
 * @author Terry
 */
public class CharManager {
    
    public HashMap<String, Character> characters = new HashMap<String, Character>();
    
    public CharManager() {
        
    }
    
    public void initialize() {
        // could be use to load from saves
    }
    
    public void addCharacter(String name, Stats stats, Vector3f position) {
        Character c = new Character(name, stats, position);
        characters.put(name, c);
    }
    
    public Character getCharacter(String name) {
        Character c = characters.get(name);
        if(c != null) {
            return c;
        }
        return null;
    }
    
    public void updateCharacter(Character c) {
        if(characters.get(c.getName()) != null) {
            characters.remove(c.getName());
            characters.put(c.getName(), c);
        }
    }
    
    public String[] characterList() {
        return null;   
    }
    
}
