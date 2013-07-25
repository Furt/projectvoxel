package me.furt.projectv.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;

import me.furt.projectv.NetPlayer;
import me.furt.projectv.Network;
import me.furt.projectv.Network.AddPlayer;
import me.furt.projectv.Network.Login;
import me.furt.projectv.Network.MovePlayer;
import me.furt.projectv.Network.Register;
import me.furt.projectv.Network.RegistrationRequired;
import me.furt.projectv.Network.RemovePlayer;
import me.furt.projectv.Network.UpdatePlayer;

import org.lwjgl.LWJGLException;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

public class GameServer {
	Server server;
	HashSet<NetPlayer> loggedIn = new HashSet<NetPlayer>();

	public GameServer() throws LWJGLException {
		server = new Server() {
			protected Connection newConnection() {
				return new PlayerConnection();
			}
		};

		Network.register(server);
		System.out.println("Network registered.");

		server.addListener(new Listener() {
			public void received(Connection c, Object object) {
				// all network stuff here
				PlayerConnection connection = (PlayerConnection) c;
				NetPlayer player = connection.player;

				// Login packet
				if (object instanceof Login) {
					// Ignore if already logged in.
					if (player != null)
						return;

					// Reject if the name is invalid.
					String name = ((Login) object).name;
					if (!isValid(name)) {
						c.close();
						return;
					}

					// Reject if already logged in.
					for (NetPlayer other : loggedIn) {
						if (other.rawName.equals(name)) {
							c.close();
							return;
						}
					}

					player = loadPlayer(name);

					// Reject if couldn't load character.
					if (player == null) {
						c.sendTCP(new RegistrationRequired());
						return;
					}

					loggedIn(connection, player);
					return;
				}

				// Register packet
				if (object instanceof Register) {
					// Ignore if already logged in.
					if (player != null)
						return;

					Register register = (Register) object;

					// Reject if the login is invalid.
					if (!isValid(register.name)) {
						c.close();
						return;
					}

					// Reject if player alread exists.
					if (loadPlayer(register.name) != null) {
						c.close();
						return;
					}

					// Register player
					player = new NetPlayer();
					player.id = 1;
					player.rawName = register.name;
					player.displayName = register.name;
					player.x = 0;
					player.y = 0;
					player.z = 0;
					if (!savePlayer(player)) {
						c.close();
						return;
					}

					loggedIn(connection, player);
					return;
				}

				if (object instanceof MovePlayer) {
					// Ignore if not logged in.
					if (player == null)
						return;

					MovePlayer msg = (MovePlayer) object;

					// Ignore if invalid move.
					if (Math.abs(msg.x) != 1 && Math.abs(msg.y) != 1
							&& Math.abs(msg.z) != 1)
						return;

					player.x += msg.x;
					player.y += msg.y;
					player.z += msg.z;
					if (!savePlayer(player)) {
						connection.close();
						return;
					}

					UpdatePlayer update = new UpdatePlayer();
					update.id = player.id;
					update.x = player.x;
					update.y = player.y;
					update.z = player.z;
					server.sendToAllTCP(update);
					return;
				}
			}

			private boolean isValid(String value) {
				if (value == null)
					return false;
				value = value.trim();
				if (value.length() == 0)
					return false;
				return true;
			}

			public void disconnected(Connection c) {
				PlayerConnection connection = (PlayerConnection) c;
				if (connection.player != null) {
					loggedIn.remove(connection.player);
					System.out.println(connection.player.rawName
							+ " has left the server.");
					RemovePlayer removePlayer = new RemovePlayer();
					removePlayer.id = connection.player.id;
					server.sendToAllTCP(removePlayer);
				}
			}
		});
		System.out.println("Server Listener Started.");

		try {
			server.bind(Network.port);
			System.out.println("Server Binding to port " + Network.port + ".");
		} catch (IOException e) {
			e.printStackTrace();
		}
		server.start();
		System.out.println("Server is ready for connections.");
	}

	static class PlayerConnection extends Connection {
		public NetPlayer player;
	}

	void loggedIn(PlayerConnection c, NetPlayer player) {
		c.player = player;

		// Add existing players to new logged in connection.
		for (NetPlayer other : loggedIn) {
			AddPlayer addPlayer = new AddPlayer();
			addPlayer.player = other;
			c.sendTCP(addPlayer);
		}

		loggedIn.add(player);
		System.out.println(player.displayName + " has joined the server.");
		// Add logged in player to all connections.
		AddPlayer addPlayer = new AddPlayer();
		addPlayer.player = player;
		server.sendToAllTCP(addPlayer);
	}

	boolean savePlayer(NetPlayer player) {
		File file = new File("players", player.rawName.toLowerCase());
		if (!file.getParentFile().exists())
			file.getParentFile().mkdirs();

		if (player.id == 0) {
			String[] children = file.getParentFile().list();
			if (children == null)
				return false;
			player.id = children.length + 1;
		}

		DataOutputStream output = null;
		try {
			output = new DataOutputStream(new FileOutputStream(file));
			output.writeInt(player.id);
			output.writeUTF(player.displayName);
			output.writeInt(player.health);
			output.writeInt(player.maxHealth);
			output.writeFloat(player.x);
			output.writeFloat(player.y);
			output.writeFloat(player.z);
			return true;
		} catch (IOException ex) {
			ex.printStackTrace();
			return false;
		} finally {
			try {
				output.close();
			} catch (IOException ignored) {
			}
		}
	}

	NetPlayer loadPlayer(String name) {
		File file = new File("players", name.toLowerCase());
		if (!file.exists())
			return null;
		DataInputStream input = null;
		try {
			input = new DataInputStream(new FileInputStream(file));
			NetPlayer player = new NetPlayer();
			player.id = input.readInt();
			player.rawName = name;
			player.displayName = input.readUTF();
			player.health = input.readInt();
			player.maxHealth = input.readInt();
			player.x = input.readFloat();
			player.y = input.readFloat();
			player.z = input.readFloat();
			input.close();
			return player;
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		} finally {
			try {
				if (input != null)
					input.close();
			} catch (IOException ignored) {
			}
		}
	}

	// For testing purposes only.
	public static void main(String[] args) throws IOException, LWJGLException {
		Log.set(Log.LEVEL_DEBUG);
		System.out.println("Server initializing.");
		new GameServer();
	}
}
