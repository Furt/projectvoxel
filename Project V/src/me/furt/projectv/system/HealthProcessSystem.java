package me.furt.projectv.system;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableBag;

public class HealthProcessSystem extends EntitySystem {

	public HealthProcessSystem(Aspect aspect) {
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
