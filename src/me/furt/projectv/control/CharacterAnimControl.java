package me.furt.projectv.control;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import java.io.IOException;

/**
 * Handles animation of character
 *
 * @author normenhansen
 */
public class CharacterAnimControl implements Control {

    protected boolean enabled = true;
    protected Spatial spatial;
    protected AnimControl animControl;
    protected CharacterControl characterControl;
    protected AnimChannel torsoChannel;
    protected AnimChannel feetChannel;

    public CharacterAnimControl() {
    }

    public void setSpatial(Spatial spatial) {
        if (spatial == null) {
            return;
        }
        animControl = spatial.getControl(AnimControl.class);
        characterControl = spatial.getControl(CharacterControl.class);
        if (animControl != null && characterControl != null) {
            enabled = true;
            torsoChannel = animControl.createChannel();
            feetChannel = animControl.createChannel();
        }
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void update(float tpf) {
        if (!enabled) {
            return;
        }
        if (!characterControl.onGround()) {
            if (!"JumpLoop".equals(torsoChannel.getAnimationName())) {
                torsoChannel.setAnim("JumpLoop");
            }
            if (!"JumpLoop".equals(feetChannel.getAnimationName())) {
                feetChannel.setAnim("JumpLoop");
            }
            return;
        }
        if (characterControl.getWalkDirection().length() > 0) {
            if (!"RunTop".equals(torsoChannel.getAnimationName())) {
                torsoChannel.setAnim("RunTop");
            }
            if (!"RunBase".equals(feetChannel.getAnimationName())) {
                feetChannel.setAnim("RunBase");
            }
        } else {
            if (!"IdleTop".equals(torsoChannel.getAnimationName())) {
                torsoChannel.setAnim("IdleTop");
            }
            if (!"IdleBase".equals(feetChannel.getAnimationName())) {
                feetChannel.setAnim("IdleBase");
            }
        }
    }

    public void render(RenderManager rm, ViewPort vp) {
    }

    public Control cloneForSpatial(Spatial spatial) {
        throw new UnsupportedOperationException("Not supported.");
    }

    public void write(JmeExporter ex) throws IOException {
        throw new UnsupportedOperationException("Not supported.");
    }

    public void read(JmeImporter im) throws IOException {
        throw new UnsupportedOperationException("Not supported.");
    }
}
