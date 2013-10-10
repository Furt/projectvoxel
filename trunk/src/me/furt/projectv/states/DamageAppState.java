/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.furt.projectv.states;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;
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

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        entityData = ((GameClient) app).getEntityData();
        damageSet = entityData.getEntities(DamageComponent.class);
        healthSet = entityData.getEntities(HealthComponent.class);
    }

    @Override
    public void update(float tpf) {

        Map<EntityId, Float> damageSummary = new HashMap();

        damageSet.applyChanges();
        healthSet.applyChanges();

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
    }
}
