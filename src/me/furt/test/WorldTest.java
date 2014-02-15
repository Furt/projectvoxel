package me.furt.test;

import com.jme3.app.SimpleApplication;
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
        this.seed = new Seed("test");
        this.worldInfo = new WorldInfo("world");
        worldInfo.setCrittersAllowed(false);
        worldInfo.setMonstersAllowed(false);
        world = new World(this, worldInfo, seed);
        worldNode.addControl(world);
    }

    @Override
    public void simpleUpdate(float tpf) {
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
