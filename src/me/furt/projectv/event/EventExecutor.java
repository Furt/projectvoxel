package me.furt.projectv.event;

/**
 * ProjectV
 *
 * @author Furt
 */
public interface EventExecutor {

    public void execute(Listener listener, Event event) throws EventException;
}
