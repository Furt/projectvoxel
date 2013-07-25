package me.furt.projectv;

import me.furt.projectv.component.Health;
import me.furt.projectv.component.Name;
import me.furt.projectv.component.Player;
import me.furt.projectv.component.Position;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;

public class EntityCreate {
	public static Entity createPlayer(World world, int id, String rawName, String displayName, int health, int maxHealth, float x, float y, float z) {
		Entity e = world.createEntity();
		
		// player
		Player p = new Player();
		p.id = id;
		e.addComponent(p);
		
		// player name
		Name n = new Name();
		n.rawName = rawName;
		n.displayName = displayName;
		e.addComponent(n);
		
		// player health
		Health h = new Health();
		h.health = health;
		h.maxHealth = maxHealth;
		e.addComponent(h);
		
		// camera position
		Position c = new Position();
		c.x = x;
		c.y = y;
		c.z = z;
		e.addComponent(c);
		
		world.getManager(GroupManager.class).add(e, Constants.Groups.PLAYERS);
		
		return e;
		
	}
}
