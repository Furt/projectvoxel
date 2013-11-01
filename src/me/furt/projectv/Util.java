package me.furt.projectv;

import com.jme3.network.serializing.Serializer;
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
}
