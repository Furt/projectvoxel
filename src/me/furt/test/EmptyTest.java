package me.furt.test;

import me.furt.projectv.core.*;
import com.jme3.app.SimpleApplication;
import com.jme3.system.JmeContext;

/**
 * ProjectV
 *
 * @author Furt
 */
public class EmptyTest extends SimpleApplication {

    public static void main(String[] args) {
        EmptyTest app = new EmptyTest();
        app.start(JmeContext.Type.Display); // standard display type
    }

    @Override
    public void simpleInitApp() {
    }

    @Override
    public void simpleUpdate(float tpf) {
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
