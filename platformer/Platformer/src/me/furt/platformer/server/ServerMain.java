/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.furt.platformer.server;

import com.jme3.app.SimpleApplication;
import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.system.JmeContext;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.furt.platformer.Globals;
import me.furt.platformer.PlatformerNetwork;
import me.furt.platformer.PlatformerNetwork.PlayerMessage;

/**
 * Platformer Server
 * @author furt
 */
public class ServerMain extends SimpleApplication {
    private Server server;
    
    public static void main(String[] args) {
        PlatformerNetwork.initializeSerializables();
        ServerMain app = new ServerMain();
        app.start(JmeContext.Type.Headless);
    }

    @Override
    public void simpleInitApp() {
        try {
            server = Network.createServer(Globals.GAME_NAME, Globals.GAME_VERSION, Globals.GAME_TCP_PORT, Globals.GAME_UCP_PORT);
            server.start();
        } catch (IOException ex) {
            Logger.getLogger(ServerMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void simpleUpdate(float tpf) {
        float f = tpf + 0.003f;
        if(f >= 0.020f) {
            server.broadcast(new PlayerMessage("furt", "Hello Newbies!" + f));
        }
        
        server.getConnection(1).send(null);
    }
    
    @Override
    public void destroy() {
        server.close();
        super.destroy();
    }
}
