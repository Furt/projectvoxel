/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.furt.projectv;

import java.util.HashMap;

/**
 *
 * @author Terry
 */
public class PlayerManager {
    
    private static HashMap<Long, PlayerManager> playerList = new HashMap<Long, PlayerManager>();
    private long id;
    
    public PlayerManager(long id) {
        this.id = id;
    }
}
