/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.furt.projectv.network.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Terry
 */
@Serializable
public class ChatMessage extends AbstractMessage {

    private Object channel;
    private String player;
    private String message;

    public ChatMessage() {
    }

    public ChatMessage(Object channel, String player, String message) {
        this.channel = channel;
        this.player = player;
        this.message = message;

    }

    public String getMessage() {
        return channel + ":" + player + ":" + message;
    }
}
