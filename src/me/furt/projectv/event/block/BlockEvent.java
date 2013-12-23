package me.furt.projectv.event.block;

import com.cubes.Block;
import me.furt.projectv.event.Event;

/**
 * ProjectV
 *
 * @author Furt
 */
public abstract class BlockEvent extends Event {
    protected Block block;

    public BlockEvent(final Block theBlock) {
        block = theBlock;
    }
    
    public final Block getBlock() {
        return block;
    }
}
