package me.furt.projectv.worldtest;

import com.cubes.BlockTerrainControl;
import com.cubes.Vector3Int;
import com.cubes.network.CubesSerializer;
import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector3f;
import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Network;
import com.jme3.network.serializing.Serializer;
import com.jme3.scene.Node;
import com.jme3.system.JmeContext;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.furt.projectv.WorldSettings;

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
    public Client client;
    public BlockTerrainControl blockTerrain;
    private ConcurrentLinkedQueue<byte[]> terrainMessageQueue;

    @Override
    public void simpleInitApp() {
        Serializer.registerClass(TerrainMessage.class);
        try {
            client = Network.connectToServer("localhost", 25570);
            client.start();
        } catch (IOException ex) {
            Logger.getLogger(ClientMain.class.getName()).log(Level.SEVERE, null, ex);
        }

        WorldSettings.registerBlocks();
        blockTerrain = new BlockTerrainControl(WorldSettings.getSettings(this), new Vector3Int());
        Node terrainNode = new Node();
        terrainNode.addControl(blockTerrain);
        rootNode.attachChild(terrainNode);

        cam.setLocation(new Vector3f(-64, 187, -55));
        cam.lookAtDirection(new Vector3f(0.64f, -0.45f, 0.6f), Vector3f.UNIT_Y);
        flyCam.setMoveSpeed(200);
        
        terrainMessageQueue = new ConcurrentLinkedQueue<byte[]>();
        client.addMessageListener(new TerrainMessageListener());
        client.send(new TerrainMessage());
    }

    public class TerrainMessageListener implements MessageListener<Client> {

        public void messageReceived(Client source, Message m) {
            if (m instanceof TerrainMessage) {
                final TerrainMessage msg = (TerrainMessage) m;
                terrainMessageQueue.add(msg.getWorldData());
            }
        }
    }
    
    @Override
    public void simpleUpdate(float tpf) {
        byte[] message = terrainMessageQueue.poll();
        if (message != null) {
            CubesSerializer.readFromBytes(blockTerrain, message);
        }
    }
    
    @Override
    public void destroy() {
        client.close();
        super.destroy();
    }
}
