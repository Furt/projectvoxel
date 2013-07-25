package me.furt.projectv.block;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL11;

public class Cube {

	public void render(float col, float myVar, float x, float y, float z) {

		glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
		glDisable(GL11.GL_TEXTURE_2D);
		glTranslatef(x, y, z);
		GL11.glLineWidth(2);
		glBegin(GL_QUADS);

		glColor3f(col, col, col);

		glVertex3f(myVar, myVar, myVar);
		glVertex3f(-myVar, myVar, myVar);
		glVertex3f(-myVar, -myVar, myVar);
		glVertex3f(myVar, -myVar, myVar);

		glVertex3f(myVar, myVar, -myVar);
		glVertex3f(-myVar, myVar, -myVar);
		glVertex3f(-myVar, -myVar, -myVar);
		glVertex3f(myVar, -myVar, -myVar);

		glVertex3f(myVar, myVar, myVar);
		glVertex3f(myVar, -myVar, myVar);
		glVertex3f(myVar, -myVar, -myVar);
		glVertex3f(myVar, myVar, -myVar);

		glVertex3f(-myVar, myVar, myVar);
		glVertex3f(-myVar, -myVar, myVar);
		glVertex3f(-myVar, -myVar, -myVar);
		glVertex3f(-myVar, myVar, -myVar);

		glVertex3f(myVar, myVar, myVar);
		glVertex3f(-myVar, myVar, myVar);
		glVertex3f(-myVar, myVar, -myVar);
		glVertex3f(myVar, myVar, -myVar);

		glVertex3f(myVar, -myVar, myVar);
		glVertex3f(-myVar, -myVar, myVar);
		glVertex3f(-myVar, -myVar, -myVar);
		glVertex3f(myVar, -myVar, -myVar);

		glColor3f(1, 1, 1);
		glEnd();
		glEnable(GL11.GL_TEXTURE_2D);
		glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
	}
}
