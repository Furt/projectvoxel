package me.furt.projectv;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class Network {
	static public final int port = 25570;

	// register objects
	static public void register(EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		kryo.register(Login.class);
		kryo.register(RegistrationRequired.class);
		kryo.register(Register.class);
		kryo.register(AddPlayer.class);
		kryo.register(UpdatePlayer.class);
		kryo.register(RemovePlayer.class);
		kryo.register(NetPlayer.class);
		kryo.register(MovePlayer.class);
		kryo.register(RegenHealth.class);
	}

	static public class Login {
		public String name;
	}
	
	static public class RegistrationRequired {
		
	}

	static public class Register {
		public String name;
	}

	static public class UpdatePlayer {
		public int id, health;
		public float x, y, z;
	}

	static public class AddPlayer {
		public NetPlayer player;
	}

	static public class RemovePlayer {
		public int id;
	}

	static public class MovePlayer {
		public float x, y, z;
	}

	static public class RegenHealth {
		public int health, maxHealth;
	}
}
