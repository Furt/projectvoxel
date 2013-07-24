package me.furt.projectv.camera;

import me.furt.projectv.utils.Frustum;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

public class FirstPersonCamera {

	public Frustum frustum;

	// 3d vector to store the camera's position in
	private Vector3f position = null;
	// the rotation around the Y axis of the camera
	private float yaw = 0.0f;
	// the rotation around the X axis of the camera
	private float pitch = 0.0f;

	private float walkbias = 0.0f;
	private float walkbiasangle = 0.0f;

	private float piover180 = 0.01745329F;

	public float getWalkBias() {
		return walkbias;
	}

	private static FirstPersonCamera instance = null;

	public synchronized static FirstPersonCamera getInstance() {
		if (instance == null) {
			instance = new FirstPersonCamera(0, 4, 0, 0, 0);
		}
		return instance;
	}

	// Constructor that takes the starting x, y, z location of the camera
	public FirstPersonCamera(float x, float y, float z, float pitch, float yaw) {
		frustum = new Frustum();
		position = new Vector3f(x, y, z);
		this.yaw(yaw);
		this.pitch(pitch);
	}

	public float getCameraYaw() {
		return yaw;
	}

	public float getCameraPitch() {
		return pitch;
	}

	public Vector3f getCameraPosition() {
		return position;
	}

	public void setPosition(Vector3f pos) {
		this.position.x = pos.x;
		this.position.y = pos.y;
		this.position.z = pos.z;
	}

	// increment the camera's current yaw rotation (Y)
	public void yaw(float amount) {
		yaw += amount;

		if (yaw > 180f)
			yaw = -180;
		else if (yaw < -180f)
			yaw = 180f;

	}

	// increment the camera's current pitch rotation (X)
	public void pitch(float amount) {
		pitch += amount;

		if (pitch > 80f)
			pitch = 80f;
		else if (pitch < -80f)
			pitch = -80f;

	}

	public void roll(float amount) {
		// not used
	}

	// X is sin(angle)
	public float getXPos(float angle) {
		return (float) Math.sin(Math.toRadians(angle));
	}

	// Z is cos(angle) * r
	public float getZPos(float angle) {
		return (float) Math.cos(Math.toRadians(angle));
	}

	// moves the camera forward relative to its current rotation (yaw)
	public void walkForward(Vector3f velocity) {

		position.x += velocity.x * getXPos(yaw);
		position.z -= velocity.z * getZPos(yaw);

		if (walkbiasangle >= 359.0f) {
			walkbiasangle = 0.0f;
		} else {
			walkbiasangle += 10;
		}
		walkbias = (float) Math.sin(walkbiasangle * piover180) / 15.0f;

	}

	// moves the camera backward relative to its current rotation (yaw)
	public void walkBackwards(Vector3f velocity)// float distance)
	{
		position.x -= velocity.x * getXPos(yaw);
		position.z += velocity.z * getZPos(yaw);

		if (walkbiasangle <= 1.0f) // Is walkbiasangle>=359?
		{
			walkbiasangle = 359.0f; // Make walkbiasangle Equal 0
		} else // Otherwise
		{
			walkbiasangle -= 10; // If walkbiasangle < 359 Increase It By 10
		}
		walkbias = (float) Math.sin(walkbiasangle * piover180) / 15.0f;
	}

	// strafes the camera left relative to its current rotation (yaw)
	public void strafeLeft(Vector3f velocity) {
		// player position is x=sin(yaw), z=cos(yaw)
		// x is distance * sin(yaw)
		// z is distance * cos(yaw)
		// we subtract 90 to the angle so to strafe
		position.x += velocity.x * getXPos(yaw - 90); // .5PI
		position.z -= velocity.z * getZPos(yaw - 90); // .5PI

		if (walkbiasangle <= 1.0f) // Is walkbiasangle>=359?
		{
			walkbiasangle = 359.0f; // Make walkbiasangle Equal 0
		} else // Otherwise
		{
			walkbiasangle -= 10; // If walkbiasangle < 359 Increase It By 10
		}
		walkbias = (float) Math.sin(walkbiasangle * piover180) / 15.0f;
	}

	// strafes the camera right relative to its current rotation (yaw)
	public void strafeRight(Vector3f velocity) {
		// x is distance * sin(yaw)
		// z is distance * cos(yaw)
		// we add 90 to the angle so to strafe
		position.x += velocity.x * getXPos(yaw + 90);
		position.z -= velocity.z * getZPos(yaw + 90);

		if (walkbiasangle <= 1.0f) // Is walkbiasangle>=359?
		{
			walkbiasangle = 359.0f; // Make walkbiasangle Equal 0
		} else // Otherwise
		{
			walkbiasangle += 10; // If walkbiasangle < 359 Increase It By 10
		}
		walkbias = (float) Math.sin(walkbiasangle * piover180) / 15.0f;
	}

	public void jump(float distance) {
		position.y += distance;
	}

	public void down(float distance) {
		position.y -= distance;
	}

	public void resetPosition(float x, float y, float z, float pitch, float yaw) {
		position.x = x;
		position.y = y;
		position.z = z;
		this.pitch = pitch;
		this.yaw = yaw;
	}

	// translates and rotate the matrix so that it looks through the camera
	// this does basically what gluLookAt() does
	public void lookThrough() {
		// rotate the pitch around the X axis
		GL11.glRotatef(pitch, 1.0f, 0.0f, 0.0f);
		// rotate the yaw around the Y axis
		// remove line below for third person
		float ytrans = position.y - (-walkbias - 0.25f);
		GL11.glRotatef(yaw, 0.0f, 1.0f, 0.0f);
		// translate to the position vector's location
		GL11.glTranslatef(-position.x, -ytrans, -position.z);

		frustum.calculateFrustum();

	}

	public void setPosition(float x, float y, float z) {
		position.x = x;
		position.y = y;
		position.z = z;
		
	}
}
