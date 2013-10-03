package me.furt.projectv.network.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 * used to load a level on the client and to report that a level has been loaded
 * to the server.
 * @author Terry
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
