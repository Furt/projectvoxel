package me.furt.projectv;

import com.cubes.CubesSettings;
import com.cubes.Vector3Int;
import com.jme3.math.Vector3f;
import com.jme3.network.serializing.Serializer;
import com.jme3.renderer.Camera;
import me.furt.projectv.network.messages.*;

/**
 * Project V
 *
 * @author Furt
 */
class Util {

    static void registerSerializers() {
        Serializer.registerClass(ActionMessage.class);
        Serializer.registerClass(ChatMessage.class);
        Serializer.registerClass(ClientJoinMessage.class);
        Serializer.registerClass(HandshakeMessage.class);
        Serializer.registerClass(LoginMessage.class);
        Serializer.registerClass(ManualControlMessage.class);
        Serializer.registerClass(ServerAddPlayerMessage.class);
        Serializer.registerClass(ServerJoinMessage.class);
        Serializer.registerClass(ServerTerrainMessage.class);
        Serializer.registerClass(StartGameMessage.class);
    }
    
    static Vector3Int float2int(Vector3f vec) {
        int x = Math.round(vec.getX());
        int y = Math.round(vec.getY());
        int z = Math.round(vec.getZ());
        return new Vector3Int(x,y,z);
    }
    
    static Vector3Int cam2chunk(Camera cam) {
        Vector3f vFloat = cam.getLocation();
        Vector3Int vInt = float2int(vFloat);
        int x = vInt.getX()/16;
        int y = 0;
        int z = vInt.getZ()/16;
        return new Vector3Int(x,y,z);
    }
}
