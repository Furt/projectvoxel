/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.furt.projectv.component;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import com.simsilica.es.EntityComponent;
import com.simsilica.es.PersistentComponent;

/**
 *
 * @author Terry
 */
@Serializable
public class NameComponent extends AbstractMessage implements EntityComponent, PersistentComponent {
    private String name;
    
    public NameComponent(String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
}
