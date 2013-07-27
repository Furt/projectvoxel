package me.furt.projectv.client;

import static org.lwjgl.opengl.GL11.glViewport;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.ByteBuffer;
import java.util.HashMap;

import javax.swing.JOptionPane;

import me.furt.projectv.EntityCreate;
import me.furt.projectv.NetPlayer;
import me.furt.projectv.Network;
import me.furt.projectv.Network.AddPlayer;
import me.furt.projectv.Network.Login;
import me.furt.projectv.Network.Register;
import me.furt.projectv.Network.RegistrationRequired;
import me.furt.projectv.Network.RemovePlayer;
import me.furt.projectv.Network.UpdatePlayer;
import me.furt.projectv.camera.FirstPersonCamera;
import me.furt.projectv.system.RenderPlayerSystem;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.vector.Vector3f;

import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Listener.ThreadedListener;
import com.esotericsoftware.minlog.Log;
import de.matthiasmann.twl.utils.PNGDecoder;

public class GameClient {
	Client client;
	String pname;
	World world;
	FirstPersonCamera camera = FirstPersonCamera.getInstance();
	UI ui;
	public static final int WINDOW_WIDTH = 1024;
	public static final int WINDOW_HEIGHT = 768;

	public GameClient() throws LWJGLException {
		client = new Client();
		client.start();
		world = new World();
		pname = "Player";
		Network.register(client);
		// int centerX = (Display.getDisplayMode().getWidth() - WINDOW_WIDTH) /
		// 2;
		// int centerY = (Display.getDisplayMode().getHeight() - WINDOW_HEIGHT)
		// / 2;

		client.addListener(new ThreadedListener(new Listener() {
			public void connected(Connection connection) {

			}

			public void received(Connection connection, Object object) {
				if (object instanceof RegistrationRequired) {
					Register register = new Register();
					register.name = pname;
					client.sendTCP(register);
				}

				if (object instanceof AddPlayer) {
					AddPlayer msg = (AddPlayer) object;
					ui.addPlayer(msg.player);
					return;
				}

				if (object instanceof UpdatePlayer) {
					ui.updatePlayer((UpdatePlayer) object);
					return;
				}

				if (object instanceof RemovePlayer) {
					RemovePlayer msg = (RemovePlayer) object;
					ui.removePlayer(msg.id);
					return;
				}
			}

			public void disconnected(Connection connection) {
				System.exit(0);
			}
		}));

		ui = new UI();
		String host = ui.inputHost();
		try {
			client.connect(5000, host, Network.port);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		pname = ui.inputName();
		Login login = new Login();
		login.name = pname;
		client.sendTCP(login);
		start();
	}

	static class UI {
		HashMap<Integer, NetPlayer> players = new HashMap<Integer, NetPlayer>();

		public String inputHost() {
			String input = (String) JOptionPane.showInputDialog(null, "Host:",
					"Connect to server", JOptionPane.QUESTION_MESSAGE, null,
					null, "localhost");
			if (input == null || input.trim().length() == 0)
				System.exit(1);
			return input.trim();
		}

		public String inputName() {
			String input = (String) JOptionPane.showInputDialog(null, "Name:",
					"Connect to server", JOptionPane.QUESTION_MESSAGE, null,
					null, "Test");
			if (input == null || input.trim().length() == 0)
				System.exit(1);
			return input.trim();
		}

		public void addPlayer(NetPlayer player) {
			players.put(player.id, player);
			System.out.println(player.rawName + " has joined the server.");
		}

		public void updatePlayer(UpdatePlayer msg) {
			NetPlayer player = players.get(msg.id);
			if (player == null)
				return;
			player.x = msg.x;
			player.y = msg.y;
			player.z = msg.z;
			System.out.println(player.rawName + " moved to " + player.x + ", "
					+ player.y + ", " + player.z);
		}

		public void removePlayer(int id) {
			NetPlayer player = players.remove(id);
			if (player != null)
				System.out.println(player.rawName + " has left the server.");
		}
	}

	public void start() throws LWJGLException {
		System.out.println("Start init");

		try {
			Display.setDisplayMode(new DisplayMode(1024, 768));
			ByteBuffer[] list = new ByteBuffer[2];

			list[0] = loadIcon(getClass().getResourceAsStream(
					"assets/images/magex16.png"));
			list[1] = loadIcon(getClass().getResourceAsStream(
					"assets/images/magex32.png"));
			Display.setIcon(list);
			Display.setTitle("Project V");
			Display.setVSyncEnabled(true);

			PixelFormat pixelFormat = new PixelFormat();
			ContextAttribs contextAtrributes = new ContextAttribs(3, 0);
			Display.create(pixelFormat, contextAtrributes);
			glViewport(0, 0, Display.getDisplayMode().getWidth(), Display
					.getDisplayMode().getHeight());

			if (GLContext.getCapabilities().OpenGL21) {
				// opengl 2.0 supported.
				System.out.println("OpenGL21 supported");
			}

			System.out.println("OpenGL version: "
					+ GL11.glGetString(GL11.GL_VERSION));

			SkyBox.initSkyBox(new Vector3f(0, 0, 50), new Vector3f(.5f, .5f,
					.5f));
			world.setManager(new GroupManager());
			// TODO add entity systems here
			world.setSystem(new RenderPlayerSystem());
			world.initialize();

			// Add entity player to world
			NetPlayer player = ui.players.get(1);
			EntityCreate.createPlayer(world, player.id, player.rawName,
					player.displayName, player.health, player.maxHealth,
					player.x, player.y, player.z).addToWorld();

			while (!Display.isCloseRequested() && client.isConnected()) {
				SkyBox.renderSkyBox(camera.getCameraPitch(),
						camera.getCameraYaw(), 0);
				Display.update();
				Display.sync(60);
			}
			Display.destroy();
			System.exit(0);

		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static ByteBuffer loadIcon(InputStream is) throws IOException {
		try {
			PNGDecoder decoder = new PNGDecoder(is);
			ByteBuffer bb = ByteBuffer.allocateDirect(decoder.getWidth()
					* decoder.getHeight() * 4);
			decoder.decode(bb, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
			bb.flip();
			return bb;
		} finally {
			is.close();
		}
	}

	// For testing purposes only.
	public static void main(String[] args) throws LWJGLException {
		Log.set(Log.LEVEL_DEBUG);
		new GameClient();
	}

}
