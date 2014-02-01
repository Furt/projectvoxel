package me.furt.projectv;

import me.furt.projectv.core.network.messages.LoginMessage;
import me.furt.projectv.core.network.messages.ChatMessage;
import me.furt.projectv.core.network.messages.ServerJoinMessage;
import me.furt.projectv.core.network.messages.ClientJoinMessage;
import me.furt.projectv.core.network.messages.ServerTerrainMessage;
import me.furt.projectv.core.network.messages.HandshakeMessage;
import com.cubes.Vector3Int;
import com.jme3.math.Vector3f;
import com.jme3.network.serializing.Serializer;
import com.jme3.renderer.Camera;

/**
 * Project V
 *
 * @author Furt
 */
public class Util {

    public static void registerSerializers() {
        Serializer.registerClass(ChatMessage.class);
        Serializer.registerClass(ClientJoinMessage.class);
        Serializer.registerClass(HandshakeMessage.class);
        Serializer.registerClass(LoginMessage.class);
        Serializer.registerClass(ServerJoinMessage.class);
        Serializer.registerClass(ServerTerrainMessage.class);
    }

    public static Vector3Int float2int(Vector3f vec) {
        int x = Math.round(vec.getX());
        int y = Math.round(vec.getY());
        int z = Math.round(vec.getZ());
        return new Vector3Int(x, y, z);
    }

    public static Vector3Int cam2chunk(Camera cam) {
        Vector3f vFloat = cam.getLocation();
        Vector3Int vInt = float2int(vFloat);
        int x = vInt.getX() / 16;
        int y = 0;
        int z = vInt.getZ() / 16;
        return new Vector3Int(x, y, z);
    }
}
