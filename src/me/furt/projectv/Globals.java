package me.furt.projectv;

/**
 * Project V
 *
 * @author Furt
 */
public class Globals {

    public static final String NAME = "ProjectV r" + Globals.CLIENT_VERSION;
    public static final String DEFAULT_SERVER = "127.0.0.1";
    public static final int PROTOCOL_VERSION = 1;
    public static final int CLIENT_VERSION = 1;
    public static final int SERVER_VERSION = 1;
    public static final float NETWORK_SYNC_FREQUENCY = 0.25f;
    public static final float NETWORK_MAX_PHYSICS_DELAY = 0.25f;
    public static final int SCENE_FPS = 60;
    public static final float PHYSICS_FPS = 1f / 30f;
    public static final boolean PHYSICS_THREADED = true;
    public static final boolean PHYSICS_DEBUG = false;
    public static final int DEFAULT_PORT_TCP = 25570;
    public static final int DEFAULT_PORT_UDP = 25570;
    public static final int WINDOW_HEIGHT = 720;
    public static final int WINDOW_WIDTH = 1280;
}
