/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.furt.projectv;

import java.util.HashMap;
import me.furt.projectv.player.Player;

/**
 *
 * @author Terry
 */
public class PlayerManager {

    private static HashMap<Long, Player> playerList;

    public PlayerManager() {
        playerList = new HashMap<Long, Player>();
    }

    public HashMap<Long, Player> getPlayerList() {
        return playerList;
    }
    
    public void addPlayer(Long l, Player p) {
        playerList.put(l, p);
    }
    
    public void removePlayer(Long l) {
        playerList.remove(l);
    }
}
