package me.furt.projectv.worldtest;

import com.cubes.BlockTerrainControl;
import com.cubes.Vector3Int;
import com.cubes.network.CubesSerializer;
import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector3f;
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.network.serializing.Serializer;
import com.jme3.scene.Node;
import com.jme3.system.JmeContext;
import java.io.IOException;
import me.furt.projectv.WorldSettings;
import me.furt.projectv.block.Block_Grass;
import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

/**
 * ProjectV
 *
 * @author Furt
 */
public class ServerMain extends SimpleApplication {

    public static void main(String[] args) {
        ServerMain app = new ServerMain();
        app.start(JmeContext.Type.Headless); // headless type for servers!
    }
    public static final Logger log = Logger.getLogger("[WorldTest]");
    public static Appender appender;
    public Server server;
    public byte[] worldData = new byte[100];

    @Override
    public void simpleInitApp() {
        log.setLevel(Level.ALL);
        // Define Appender     
        appender = new ConsoleAppender(new SimpleLayout());
        //myAppender.setLayout(new SimpleLayout());  
        log.addAppender(appender);
        Serializer.registerClass(TerrainMessage.class);
        try {
            server = Network.createServer(25570);
            server.start();
        } catch (IOException ex) {
            log.error(ex);
        }
        WorldSettings.registerBlocks();
        BlockTerrainControl blockTerrain = new BlockTerrainControl(WorldSettings.getSettings(this), new Vector3Int(4, 1, 4));
        blockTerrain.setBlocksFromNoise(new Vector3Int(0, 0, 0), new Vector3Int(64, 30, 64), 0.3f, Block_Grass.class);
        Node terrainNode = new Node();
        terrainNode.addControl(blockTerrain);
        rootNode.attachChild(terrainNode);
        worldData = CubesSerializer.writeToBytes(blockTerrain);
        try {
            worldData = CompressionUtil.compress(worldData);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(ServerMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }


        cam.setLocation(new Vector3f(-64, 187, -55));
        cam.lookAtDirection(new Vector3f(0.64f, -0.45f, 0.6f), Vector3f.UNIT_Y);
        flyCam.setMoveSpeed(200);
        server.addMessageListener(new ServerTerrainListener());
    }

    public class ServerTerrainListener implements MessageListener<HostedConnection> {

        public void messageReceived(HostedConnection source, Message m) {
            if (m instanceof TerrainMessage) {
                source.send(new TerrainMessage(worldData));
            }
        }
    }

    @Override
    public void destroy() {
        server.close();
        super.destroy();
    }
}
