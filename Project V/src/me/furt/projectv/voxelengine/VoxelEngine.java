package me.furt.projectv.voxelengine;

import me.furt.projectv.voxelengine.camera.FirstPerson;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

import static org.lwjgl.opengl.GL11.*;

public class VoxelEngine {
	static int delta;
	static FirstPerson camera;
	static long time;
	static float dt;
	static long lastTime;
	static long lastFrame;
	static int fps;
	static long lastFPS;

	public static void main(String[] args) throws LWJGLException {
		initContext();
		renderLoop();

	}

	static void initContext() throws LWJGLException {
		int w = 1024;
		int h = 768;

		Display.setDisplayMode(new DisplayMode(w, h));
		Display.setFullscreen(false);
		Display.setTitle("Voxel Engine");
		Display.setVSyncEnabled(true);

		PixelFormat pixelFormat = new PixelFormat();
		ContextAttribs contextAtrributes = new ContextAttribs(3, 0);
		Display.create(pixelFormat, contextAtrributes);
		glViewport(0, 0, Display.getDisplayMode().getWidth(), Display
				.getDisplayMode().getHeight());
		camera = new FirstPerson(0f, 0f, 0f, 0f, 0f, Display.getX()
				/ Display.getY());
		delta = getDelta();

	}

	static void renderLoop() {
		while (!Display.isCloseRequested()
				&& !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			delta = getDelta();
			updateFPS();
			renderCube();
			Display.update();

			Display.sync(60);
		}

		Display.destroy();
	}

	static void renderCube() {
		glBegin(GL_QUADS);

		// Front Face
		glColor3f(1, 0, 0);
		glVertex3f(-0.5f, -0.5f, 0.5f);
		glVertex3f(0.5f, -0.5f, 0.5f);
		glVertex3f(0.5f, 0.5f, 0.5f);
		glVertex3f(-0.5f, 0.5f, 0.5f);

		// Back Face
		glColor3f(0, 1, 0);
		glVertex3f(-0.5f, -0.5f, -0.5f);
		glVertex3f(-0.5f, 0.5f, -0.5f);
		glVertex3f(0.5f, 0.5f, -0.5f);
		glVertex3f(0.5f, -0.5f, -0.5f);

		// Top Face
		glColor3f(0, 0, 1);
		glVertex3f(-0.5f, 0.5f, -0.5f);
		glVertex3f(-0.5f, 0.5f, 0.5f);
		glVertex3f(0.5f, 0.5f, 0.5f);
		glVertex3f(0.5f, 0.5f, -0.5f);

		// Bottom Face
		glColor3f(1, 0, 0);
		glVertex3f(-0.5f, -0.5f, -0.5f);
		glVertex3f(0.5f, -0.5f, -0.5f);
		glVertex3f(0.5f, -0.5f, 0.5f);
		glVertex3f(-0.5f, -0.5f, 0.5f);

		// Right face
		glColor3f(0, 1, 0);
		glVertex3f(0.5f, -0.5f, -0.5f);
		glVertex3f(0.5f, 0.5f, -0.5f);
		glVertex3f(0.5f, 0.5f, 0.5f);
		glVertex3f(0.5f, -0.5f, 0.5f);

		// Left Face
		glColor3f(0, 0, 1);
		glVertex3f(-0.5f, -0.5f, -0.5f);
		glVertex3f(-0.5f, -0.5f, 0.5f);
		glVertex3f(-0.5f, 0.5f, 0.5f);
		glVertex3f(-0.5f, 0.5f, -0.5f);

		glEnd();
	}

	public static long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	public static int getDelta() {
		long time = getTime();
		int delta = (int) (time - lastFrame);
		lastFrame = time;

		return delta;
	}

	public static void updateFPS() {
		if (getTime() - lastFPS > 1000) {
			Display.setTitle("Voxel Engine | FPS: " + fps);
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}

}
