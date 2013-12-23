package me.furt.projectv.event;

/**
 * ProjectV
 *
 * @author Furt
 */
public interface Cancellable {
    
    public boolean isCancelled();
    
    public void setCancelled(boolean cancel);
}
