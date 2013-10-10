/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.furt.projectv.states;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;
import com.simsilica.es.PersistentComponent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import me.furt.projectv.GameClient;
import me.furt.projectv.component.DamageComponent;
import me.furt.projectv.component.HealthComponent;

/**
 *
 * @author Terry
 */
public class DamageAppState extends AbstractAppState {

    EntityData entityData;
    EntitySet damageSet;
    EntitySet healthSet;
    EntitySet entitySet;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        entityData = ((GameClient) app).getEntityData();

        //method #1
        damageSet = entityData.getEntities(DamageComponent.class);
        healthSet = entityData.getEntities(HealthComponent.class);

        //method #2
        entitySet = entityData.getEntities(DamageComponent.class, HealthComponent.class);
    }

    @Override
    public void update(float tpf) {
        //method #1
        Map<EntityId, Float> damageSummary = new HashMap();

        //method #1
        damageSet.applyChanges();
        healthSet.applyChanges();

        //method #2
        entitySet.applyChanges();

        //method #1 start
        Iterator<Entity> iterator = damageSet.iterator();
        while (iterator.hasNext()) {
            Entity entity = iterator.next();
            DamageComponent dc = entity.get(DamageComponent.class);

            float f = 0;
            if (damageSummary.containsKey(dc.getTarget())) {
                f = damageSummary.get(dc.getTarget());
            }

            f += dc.getDamage();
            damageSummary.put(dc.getTarget(), f);
        }

        Iterator<EntityId> keyIterator = damageSummary.keySet().iterator();
        while (keyIterator.hasNext()) {
            EntityId entityId = keyIterator.next();
            float damage = damageSummary.get(entityId);

            Entity entity = healthSet.getEntity(entityId);
            if (entity != null) {
                HealthComponent hc = entity.get(HealthComponent.class);
                float newLife = hc.getHealth() - damage;

                if (newLife < 0) {
                    entityData.removeComponent(entityId, HealthComponent.class);
                } else {
                    entity.set(new HealthComponent(newLife));
                }
            }
        }
        //end

        //method #2 start
        Iterator<Entity> mainiterator = entitySet.iterator();
        while (iterator.hasNext()) {
            Entity entity = mainiterator.next();
            if (entity != null) {
                EntityComponent[] pc = entity.getComponents();
                
                float health = 0;
                float newLife = 0;
                float damage = 0;
                
                for (EntityComponent c : pc) {
                    if (c instanceof HealthComponent) {
                        health = ((HealthComponent) c).getHealth();
                    } else if (c instanceof DamageComponent) {
                        damage = ((DamageComponent) c).getDamage();
                    }
                }
                //leave more of a gap to add modifiers and such compared to the other method
                newLife = health - damage;

                if (newLife < 0) {
                    entityData.removeComponent(entity.getId(), HealthComponent.class);
                } else {
                    entity.set(new HealthComponent(newLife));
                }
            }
        }
        //end
    }
}
