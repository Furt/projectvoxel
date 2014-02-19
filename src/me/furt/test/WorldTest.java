package me.furt.test;

import com.jme3.app.SimpleApplication;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.system.JmeContext;
import me.furt.projectv.core.Seed;
import me.furt.projectv.core.world.World;
import me.furt.projectv.core.world.WorldInfo;

/**
 * ProjectV
 *
 * @author Furt
 */
public class WorldTest extends SimpleApplication {

    public World world;
    public WorldInfo worldInfo;
    public Seed seed;
    public Node worldNode;
    
    
    public static void main(String[] args) {
        WorldTest app = new WorldTest();
        app.start(JmeContext.Type.Display); // standard display type
    }

    @Override
    public void simpleInitApp() {
        world = new World(this);
        worldNode = new Node("world");
        worldNode.addControl(world);
        worldNode.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        rootNode.attachChild(worldNode);
    }

    @Override
    public void simpleUpdate(float tpf) {
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
