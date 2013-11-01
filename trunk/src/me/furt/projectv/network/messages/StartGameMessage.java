package me.furt.projectv.network.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 * Project V
 *
 * @author Furt
 */
@Serializable()
public class StartGameMessage extends AbstractMessage{
    public String levelName;
    public String[] modelNames;

    public StartGameMessage() {
    }

    public StartGameMessage(String levelName) {
        this.levelName = levelName;
    }

    public StartGameMessage(String levelName, String[] modelNames) {
        this.levelName = levelName;
        this.modelNames = modelNames;
    }
}
