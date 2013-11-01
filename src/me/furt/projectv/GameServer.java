package me.furt.projectv;

import com.cubes.BlockTerrainControl;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.furt.projectv.network.ServerNetManager;
import me.furt.projectv.network.messages.ManualControlMessage;
import me.furt.projectv.network.physics.PhysicsSyncManager;

/**
 * Project V
 *
 * @author Furt
 */
public class GameServer extends SimpleApplication {

    public static GameServer app;
    public static Server server;
    public BlockTerrainControl blockTerrain;
    public Node worldNode;
    private ServerNetManager listenerManager;
    private BulletAppState bulletState;
    private PhysicsSyncManager syncManager;
    private byte[] worldData;

    public static void main(String[] args) {
        AppSettings settings = new AppSettings(true);
        settings.setFrameRate(Globals.SCENE_FPS);
        settings.setRenderer(null);
        settings.setAudioRenderer(null);
        for (int i = 0; i < args.length; i++) {
            String string = args[i];
            if ("-display".equals(string)) {
                settings.setRenderer(AppSettings.LWJGL_OPENGL2);
            }
        }
        Util.registerSerializers();
        app = new GameServer();
        app.setShowSettings(false);
        app.setPauseOnLostFocus(false);
        app.setSettings(settings);
        app.start();


    }

    @Override
    public void simpleInitApp() {
        /**
         * Setup server network
         */
        try {
            server = Network.createServer(Globals.NAME, Globals.SERVER_VERSION, Globals.DEFAULT_PORT_TCP, Globals.DEFAULT_PORT_UDP);
            server.start();
        } catch (IOException ex) {
            Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
        }

        /**
         * Setup physics
         */
        bulletState = new BulletAppState();
        getStateManager().attach(bulletState);
        bulletState.getPhysicsSpace().setAccuracy(Globals.PHYSICS_FPS);

        /**
         * Setup sync manager
         */
        syncManager = new PhysicsSyncManager(app, server);
        syncManager.setSyncFrequency(Globals.NETWORK_SYNC_FREQUENCY);
        syncManager.setMessageTypes(ManualControlMessage.class);
        stateManager.attach(syncManager);

        /**
         * Generate world
         */
        World world = new World(app, bulletState);
        blockTerrain = world.setupControl();
        worldNode = world.generateTestWorld(blockTerrain);

        /**
         * Encode world to send to players
         */
        worldData = world.encodeWorld(blockTerrain);

        /**
         * Setup server listener
         */
        listenerManager = new ServerNetManager(app, server);
    }

    @Override
    public void simpleUpdate(float tpf) {
    }

    @Override
    public void destroy() {
        super.destroy();
        server.close();
    }

    public byte[] getWorldData() {
        return worldData;
    }
}
