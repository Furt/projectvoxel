package me.furt.projectv.worldtest;

import com.jme3.app.SimpleApplication;
import com.jme3.system.JmeContext;

/**
 * ProjectV
 *
 * @author Furt
 */
public class ClientMain extends SimpleApplication {

    public static void main(String[] args) {
        ClientMain app = new ClientMain();
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
