package me.furt.projectv.event.player;

import me.furt.projectv.entity.Player;
import me.furt.projectv.event.Event;

/**
 * ProjectV
 *
 * @author Furt
 */
public abstract class PlayerEvent extends Event {
    protected Player player;

    public PlayerEvent(final Player who) {
        player = who;
    }

    PlayerEvent(final Player who, boolean async) {
        super(async);
        player = who;

    }
    
    public final Player getPlayer() {
        return player;
    }
}
