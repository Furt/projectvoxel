package me.furt.projectv.voxelengine;

import me.furt.projectv.voxelengine.camera.Camera;
import me.furt.projectv.voxelengine.camera.FirstPerson;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

import static org.lwjgl.opengl.GL11.*;

public class VoxelEngine {
	public int delta;
	static FirstPerson camera;

	public static void main(String[] args) throws LWJGLException {
		initContext();
		renderLoop();

	}

	static void initContext() throws LWJGLException {
		int w = 1024;
		int h = 768;

		Display.setDisplayMode(new DisplayMode(w, h));
		Display.setFullscreen(false);
		Display.setTitle("Voxel Engine v0.1");
		Display.setVSyncEnabled(true);
		
		PixelFormat pixelFormat = new PixelFormat();
		ContextAttribs contextAtrributes = new ContextAttribs(3, 0);
		Display.create(pixelFormat, contextAtrributes);
		glViewport(0, 0, Display.getDisplayMode().getWidth(), Display
				.getDisplayMode().getHeight());
		camera = new FirstPerson(0f, 0f, 0f, 0f, 0f);
		
	}

	static void renderLoop() {
		while (!Display.isCloseRequested()) {
			preRender();

			render();
			camera.orthographicMatrix();
			Display.update();

			Display.sync(60);
		}

		Display.destroy();
	}

	static void preRender() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT
				| GL_STENCIL_BUFFER_BIT);

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();

		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
	}

	static void render() {
		glBegin(GL_QUADS);

		// glColor3f(1, 0, 0);
		// glVertex3f(-0.5f, -0.5f, 0.0f);
		//
		// glColor3f(0, 1, 0);
		// glVertex3f(+0.5f, -0.5f, 0.0f);
		//
		// glColor3f(0, 0, 1);
		// glVertex3f(+0.5f, +0.5f, 0.0f);

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

}
