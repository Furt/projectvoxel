package me.furt.platformer.sprites;

import com.jme3.app.SimpleApplication;

public class Main extends SimpleApplication {
    
    static Main app;
    SpriteLibrary library;
    
    public static void main(String[] args) {
        app = new Main();
        app.setDisplayFps(false);
        app.setDisplayStatView(false);
        app.start();
    }
    
    static SpriteEngine engine = new SpriteEngine();
    
    @Override
    public void simpleInitApp() {
        Sprite sprite = new Sprite("Textures/Sprite.png", "Sprite 1", assetManager, true, true, 9, 1, 0.015f, "Loop", "Start");
        SpriteLibrary.l_guiNode = guiNode;
        library = new SpriteLibrary("Library 1", false);
        library.addSprite(sprite);
        
        engine.addLibrary(library);
        sprite.setTimeSeparation(1);
        sprite.moveAbsolute(app.settings.getWidth()/2, app.settings.getHeight()/2);
    }
    
    @Override
    public void simpleUpdate(float tpf) {
        engine.update(tpf);
    }
}