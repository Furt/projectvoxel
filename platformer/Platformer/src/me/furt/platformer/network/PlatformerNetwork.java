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
        Serializer.registerClass(PlayerMessage.class);
    }
    
    @Serializable
    public static class PlayerMessage extends AbstractMessage {
        private String player;
        private String message;
        
        public PlayerMessage() {   
        }
        
        public PlayerMessage(String player, String message) {
            this.player = player;
            this.message = message;
            
        }
        
        public String getMessage() {
            return "<" + this.player + "> " + this.message;
        }
    }
    
}
