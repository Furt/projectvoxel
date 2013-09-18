package me.furt.projectv;

import me.furt.projectv.client.Client;
import me.furt.projectv.server.Server;

/**
 *
 * @author Terry
 */
public class Main {

    public static void main(String[] args) {
        for (String s : args) {
            if (s.equalsIgnoreCase("-client")) {
                GameClient app = new GameClient();
                app.start();
            } else if(s.equalsIgnoreCase("-server")) {
                GameServer app = new GameServer();
                app.start();
            }
        }

    }
}
