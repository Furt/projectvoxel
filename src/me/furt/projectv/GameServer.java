/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.furt.projectv;

import com.jme3.app.SimpleApplication;
import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.system.AppSettings;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.furt.projectv.network.ServerListener;

/**
 *
 * @author Terry
 */
public class GameServer extends SimpleApplication {

    public static GameServer app;
    public static Server server;
    public ServerListener listenerManager;

    public static void main(String[] args) {
        AppSettings settings = new AppSettings(true);
        settings.setFrameRate(Globals.SCENE_FPS);
        settings.setRenderer(null);
        settings.setAudioRenderer(null);
        Util.registerSerializers();
        app = new GameServer();
        app.setShowSettings(false);
        app.setPauseOnLostFocus(false);
        app.setSettings(settings);
        app.start();


    }

    @Override
    public void simpleInitApp() {
        try {
            server = Network.createServer(Globals.NAME, Globals.SERVER_VERSION, Globals.DEFAULT_PORT_TCP, Globals.DEFAULT_PORT_UDP);
            server.start();
        } catch (IOException ex) {
            Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
        }

        listenerManager = new ServerListener(app, server);
    }

    @Override
    public void simpleUpdate(float tpf) {
    }

    @Override
    public void destroy() {
        super.destroy();
        server.close();
    }
}
