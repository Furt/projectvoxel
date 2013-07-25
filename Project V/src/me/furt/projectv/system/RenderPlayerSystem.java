package me.furt.projectv.system;

import me.furt.projectv.block.Cube;
import me.furt.projectv.component.Player;
import me.furt.projectv.component.Position;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.utils.ImmutableBag;

public class RenderPlayerSystem extends EntitySystem {
	@Mapper ComponentMapper<Position> pm;
	@Mapper ComponentMapper<Player> playerm;
	
	private Cube c;
	
	public RenderPlayerSystem() {
		super(Aspect.getAspectForAll(Player.class, Position.class));
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void initialize() {
		c = new Cube();
		System.out.println("Initializing Player Render System");
	}
	
	@Override
	protected boolean checkProcessing() {
		return true;
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		for(int i = 0; entities.size() > i; i++) {
			process(entities.get(i));
		}
		
	}

	private void process(Entity entity) {
		Position p = pm.get(entity);
		c.render(1f, 0.5f, 1f, 1f, 1f);
		
	}

}
