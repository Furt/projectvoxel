package me.furt.projectv;

import java.util.HashMap;

/**
 * Project V
 *
 * @author Furt
 */
public class PlayerManager {
    
    private static HashMap<Long, PlayerManager> playerList = new HashMap<Long, PlayerManager>();
    private long id;
    
    public PlayerManager(long id) {
        this.id = id;
    }
    
    public HashMap<Long, PlayerManager> getPlayerList() {
        return playerList;
    }
}
