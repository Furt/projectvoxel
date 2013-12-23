package me.furt.projectv.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ProjectV
 *
 * @author Furt
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventHandler {

    /**
     * Define the priority of the event. First priority to the last priority
     * executed:
     *
     * LOWEST LOW NORMAL HIGH HIGHEST MONITOR
     */
    EventPriority priority() default EventPriority.NORMAL;

    boolean ignoreCancelled() default false;
}
