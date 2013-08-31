/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.furt.platformer.network;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import com.jme3.network.serializing.Serializer;

/**
 *
 * @author Terry
 */
public class PlatformerNetwork {
    public static void initializeSerializables() {
        Serializer.registerClass(ServerMessage.class);
    }
    
    @Serializable
    public static class ServerMessage extends AbstractMessage {
        private String player;
        private String message;
        
        public ServerMessage() {   
        }
        
        public ServerMessage(String message) {
            this.player = player;
            this.message = message;
            
        }
        
        public String getMessage() {
            return "<Server> " + this.message;
        }
    }
    
    @Serializable
    public class HandShake extends AbstractMessage {
        
    }
    
}
