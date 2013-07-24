package me.furt.projectv;

import org.lwjgl.LWJGLException;

import me.furt.projectv.client.GameClient;
import me.furt.projectv.server.GameServer;

public class ProjectV {

	public static void main(String[] args) {
		// Find out which class to run
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("-client")) {
				try {
					new GameClient();
				} catch (LWJGLException e) {
					e.printStackTrace();
				}
			}

			if (args[0].equalsIgnoreCase("-server")) {
				try {
					new GameServer();
				} catch (LWJGLException e) {
					e.printStackTrace();
				}
			}
		} else {
			System.exit(0);
		}

	}

}
