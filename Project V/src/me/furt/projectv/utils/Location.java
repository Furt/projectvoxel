package me.furt.projectv.utils;

import com.artemis.World;

public class Location {
	World world;
	float x;
	float y;
	float z;

	public Location(World w, float x, float y, float z) {
		this.world = w;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Location(Location loc) {
		
	}
	
	public void setWorld(World world) {
		this.world = world;
	}
	
	public World getWorld() {
		return world;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public float getX() {
		return x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public float getY() {
		return y;
	}
	
	public void setZ(float z) {
		this.z = z;
	}
	
	public float getZ() {
		return z;
	}
}
