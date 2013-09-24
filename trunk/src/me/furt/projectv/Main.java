package me.furt.projectv;

import com.jme3.system.AppSettings;

/**
 *
 * @author Terry
 */
public class Main {

    public static void main(String[] args) {
        for (String s : args) {
            if (s.equalsIgnoreCase("-client")) {
                AppSettings settings = new AppSettings(true);
                settings.setFrameRate(Globals.SCENE_FPS);
                settings.setSettingsDialogImage("/Interface/splash.png");
                settings.setTitle("ProjectV");
                Util.registerSerializers();
                GameClient app = new GameClient();
                app.setSettings(settings);
                app.setPauseOnLostFocus(false);
                app.start();
            } else if (s.equalsIgnoreCase("-server")) {
                GameServer app = new GameServer();
                app.start();
            }
        }

    }
}
