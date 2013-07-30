package me.furt.projectv.voxelengine.block;
import static org.lwjgl.opengl.GL11.*;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;

import me.furt.projectv.voxelengine.assets.ResourceManager;
import me.furt.projectv.voxelengine.assets.Texture;
import me.furt.projectv.voxelengine.camera.Camera;
import me.furt.projectv.voxelengine.world.WorldManager;

import org.lwjgl.util.Renderable;
public class BlockList {

	private int displayList;
	private int selected;
	private Texture frontTexture;
	private Texture backTexture;
	private Texture topTexture;
	private Texture bottomTexture;
	private Texture leftTexture;
	private Texture rightTexture;
	private Camera gameCamera;
	private ArrayList<Block> transpBlocks = new ArrayList<Block>();
	private boolean cullBack;

	void getDisplayList() {
		displayList = glGenLists(1);
		glNewList(displayList, GL_COMPILE);
		{
			/* 6 faces */

			ResourceManager.getManager().getTexture(frontTexture).bind();
			glBegin(GL_QUADS);
			{
				// front face
				glNormal3f(0f, 0f, 1f);
				glTexCoord2f(0f, 1f);
				glVertex3f(1f, 0f, 0f); // 1
				glTexCoord2f(1f, 1f);
				glVertex3f(0f, 0f, 0f); // 2
				glTexCoord2f(1f, 0f);
				glVertex3f(0f, 1f, 0f); // 3
				glTexCoord2f(0f, 0f);
				glVertex3f(1f, 1f, 0f); // 4
			}
			glEnd();
			ResourceManager.getManager().getTexture(backTexture).bind();
			glBegin(GL_QUADS);
			{
				// right face
				glNormal3f(0f, 0f, -1f);
				glTexCoord2f(0f, 1f);
				glVertex3f(0f, 0f, 1f); // 1
				glTexCoord2f(1f, 1f);
				glVertex3f(1f, 0f, 1f); // 2
				glTexCoord2f(1f, 0f);
				glVertex3f(1f, 1f, 1f); // 3
				glTexCoord2f(0f, 0f);
				glVertex3f(0f, 1f, 1f); // 4
			}
			glEnd();
			ResourceManager.getManager().getTexture(topTexture).bind();
			glBegin(GL_QUADS);
			{
				// top face
				glNormal3f(0f, -1f, 0f);
				glTexCoord2f(0f, 1f);
				glVertex3f(1f, 1f, 0f); // 1
				glTexCoord2f(1f, 1f);
				glVertex3f(0f, 1f, 0f); // 2
				glTexCoord2f(1f, 0f);
				glVertex3f(0f, 1f, 1f); // 3
				glTexCoord2f(0f, 0f);
				glVertex3f(1f, 1f, 1f); // 4
			}
			glEnd();
			ResourceManager.getManager().getTexture(bottomTexture).bind();
			glBegin(GL_QUADS);
			{
				// bottom face
				glNormal3f(0f, 1f, 0f);
				glTexCoord2f(0f, 1f);
				glVertex3f(1f, 0f, 1f); // 1
				glTexCoord2f(1f, 1f);
				glVertex3f(0f, 0f, 1f); // 2
				glTexCoord2f(1f, 0f);
				glVertex3f(0f, 0f, 0f); // 3
				glTexCoord2f(0f, 0f);
				glVertex3f(1f, 0f, 0f); // 4
			}
			glEnd();
			ResourceManager.getManager().getTexture(leftTexture).bind();
			glBegin(GL_QUADS);
			{
				// left face
				glNormal3f(-1f, 0f, 0f);
				glTexCoord2f(0f, 1f);
				glVertex3f(1f, 0f, 1f); // 1
				glTexCoord2f(1f, 1f);
				glVertex3f(1f, 0f, 0f); // 2
				glTexCoord2f(1f, 0f);
				glVertex3f(1f, 1f, 0f); // 3
				glTexCoord2f(0f, 0f);
				glVertex3f(1f, 1f, 1f); // 4
			}
			glEnd();
			ResourceManager.getManager().getTexture(rightTexture).bind();
			glBegin(GL_QUADS);
			{
				// right face
				glNormal3f(1f, 0f, 0f);
				glTexCoord2f(0f, 1f);
				glVertex3f(0f, 0f, 0f); // 1
				glTexCoord2f(1f, 1f);
				glVertex3f(0f, 0f, 1f); // 2
				glTexCoord2f(1f, 0f);
				glVertex3f(0f, 1f, 1f); // 3
				glTexCoord2f(0f, 0f);
				glVertex3f(0f, 1f, 0f); // 4
			}
			glEnd();
		}
		glEndList();
	}
	
	public void render() {
        if (selected == 1) {
            glColor3f(1f, 0f, 0f);
            draw();
            selected = -1;
            return;
        }
        glColor3f(1f, 1f, 1f);
        draw();
    }

    public void draw() {
		glCallList(displayList);
    }
    
    public void renderBlocks() {

        for (Block b : WorldManager.getWorld().
                getRegionForCoordinates(
                (int) gameCamera.position.x,
                (int) gameCamera.position.y,
                (int) gameCamera.position.z).getBlocks()) {
            if (b.isTransparent()) {
                transpBlocks.add(b);
                continue;
            }
            glPushMatrix();
            {
                glTranslatef(b.position.x, b.position.y, b.position.z);
                ((Renderable) b).render();
            }
            glPopMatrix();
        }

        //glDisable(GL_DEPTH_TEST);
        glDisable(GL_CULL_FACE);
        for (Block b : transpBlocks) {
            glPushMatrix();
            {
                glTranslatef(b.position.x, b.position.y, b.position.z);
                ((Renderable) b).render();
            }
            glPopMatrix();
        }
        //glEnable(GL_DEPTH_TEST);
        if (cullBack && !glIsEnabled(GL_CULL_FACE)) {
            glEnable(GL_CULL_FACE);
        }

        transpBlocks.clear();
    }
}
