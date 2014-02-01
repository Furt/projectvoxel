package me.furt.projectv.core.network.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 * Project V
 *
 * @author Furt
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

    public String getMessageData() {
        return channel + ":" + player + ":" + message;
    }

    public Object getChannel() {
        return channel;
    }

    public String getPlayer() {
        return player;
    }

    public String getMessage() {
        return message;
    }
}
