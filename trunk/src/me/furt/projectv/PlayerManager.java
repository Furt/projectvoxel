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
    
    private static HashMap<Long, PlayerData> playerList;
    
    public PlayerManager() {
        playerList = new HashMap<Long, PlayerData>();
    }
    
    public HashMap<Long, PlayerData> getPlayerList() {
        return playerList;
    }
}
