package me.furt.projectv.system;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableBag;

public class HealthGuiSystem extends EntitySystem {

	public HealthGuiSystem(Aspect aspect) {
		super(aspect);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean checkProcessing() {
		// TODO Auto-generated method stub
		return false;
	}

}
