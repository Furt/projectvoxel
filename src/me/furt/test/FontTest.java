package me.furt.test;

import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.system.AppSettings;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * ProjectV
 *
 * @author Furt
 */
public class FontTest extends SimpleApplication {

    public static void main(String[] args) {
        Logger.getLogger("").setLevel(Level.SEVERE);
        FontTest app = new FontTest();
        app.start();
    }

    public FontTest() {
        settings = new AppSettings(true);
        settings.setWidth(1280);
        settings.setHeight(720);
        settings.setTitle("ProjectV Demo - FontTest");
        settings.setSettingsDialogImage("/Interface/pv-splash.png");
        try {
            settings.setIcons(new BufferedImage[]{
                        ImageIO.read(getClass().getResourceAsStream("/Interface/magex16.png")),
                        ImageIO.read(getClass().getResourceAsStream("/Interface/magex32.png"))
                    });
        } catch (IOException e) {
        }
    }

    @Override
    public void simpleInitApp() {
        BitmapFont myFont = assetManager.loadFont("Interface/Fonts/Digiffiti.fnt");
        BitmapText hudText = new BitmapText(myFont, false);
        hudText.setText("You can write any string here");
        
        hudText.setLocalTranslation(300, hudText.getLineHeight() * 3, 0);
        hudText.setSize(50);
        guiNode.attachChild(hudText);
    }
}
