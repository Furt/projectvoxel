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
        Serializer.registerClass(ChatMessages.class);
    }
    
    @Serializable
    public static class ChatMessages extends AbstractMessage {
        private Object channel;
        private String player;
        private String message;
        
        public ChatMessages() {   
        }
        
        public ChatMessages(Object channel, String player, String message) {
            this.channel = channel;
            this.player = player;
            this.message = message;
            
        }
        
        public String getMessage() {
            return channel + ":" + player + ":" + message;
        }
    }
}
