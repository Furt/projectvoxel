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
@Serializable()
public class PlayerQueMessage extends AbstractMessage {

    public int playerID;
    public int playerQue;
    public int waitTime;

    public PlayerQueMessage() {
    }

    public PlayerQueMessage(int id, int que, int waitTime) {
        this.playerID = id;
        this.playerQue = que;
        this.waitTime = waitTime;
    }
}
