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
                Client app = new Client();
                app.start();
            } else if(s.equalsIgnoreCase("-server")) {
                Server app = new Server();
                app.start();
            }
        }

    }
}
