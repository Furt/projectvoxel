/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.furt.projectv.test;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.Camera;
import com.jme3.terrain.geomipmap.TerrainLodControl;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.heightmap.AbstractHeightMap;
import com.jme3.terrain.heightmap.ImageBasedHeightMap;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Controller;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.tools.SizeValue;
import de.lessvoid.xml.xpp3.Attributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class LoadingText extends SimpleApplication implements ScreenController, Controller {

    private NiftyJmeDisplay niftyDisplay;
    private Nifty nifty;
    private Element progressBarElement;
    private TerrainQuad terrain;
    private Material mat_terrain;
    private boolean load = false;
    private ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(2);
    private Future loadFuture = null;
    private TextRenderer textRenderer;

    public static void main(String[] args) {
        LoadingText app = new LoadingText();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        flyCam.setEnabled(false);
        niftyDisplay = new NiftyJmeDisplay(assetManager,
                inputManager,
                audioRenderer,
                guiViewPort);
        nifty = niftyDisplay.getNifty();

        nifty.fromXml("Interface/nifty_loading.xml", "start", this);

        guiViewPort.addProcessor(niftyDisplay);
    }

    @Override
    public void simpleUpdate(float tpf) {
        if (load) {
            if (loadFuture != null) {
                //if we have not started loading yet, submit the Callable to the executor
                loadFuture = exec.submit(loadingCallable);
            }
            //check if the execution on the other thread is done
            if (loadFuture.isDone()) {
                //these calls have to be done on the update loop thread, 
                //especially attaching the terrain to the rootNode
                //after it is attached, it's managed by the update loop thread 
                // and may not be modified from any other thread anymore!
                nifty.gotoScreen("end");
                nifty.exit();
                guiViewPort.removeProcessor(niftyDisplay);
                flyCam.setEnabled(true);
                flyCam.setMoveSpeed(50);
                rootNode.attachChild(terrain);
                load = false;
            }
        }
    }
    //this is the callable that contains the code that is run on the other thread.
    //since the assetmananger is threadsafe, it can be used to load data from any thread
    //we do *not* attach the objects to the rootNode here!
    Callable<Void> loadingCallable = new Callable<Void>() {
        public Void call() {

            Element element = nifty.getScreen("loadlevel").findElementByName("loadingtext");
            textRenderer = element.getRenderer(TextRenderer.class);

            mat_terrain = new Material(assetManager, "Common/MatDefs/Terrain/Terrain.j3md");
            mat_terrain.setTexture("Alpha", assetManager.loadTexture("Textures/Terrain/splat/alphamap.png"));
            //setProgress is thread safe (see below)
            setProgress(0.2f, "Loading grass");

            Texture grass = assetManager.loadTexture("Textures/Terrain/splat/grass.jpg");
            grass.setWrap(WrapMode.Repeat);
            mat_terrain.setTexture("Tex1", grass);
            mat_terrain.setFloat("Tex1Scale", 64f);
            setProgress(0.4f, "Loading dirt");

            Texture dirt = assetManager.loadTexture("Textures/Terrain/splat/dirt.jpg");

            dirt.setWrap(WrapMode.Repeat);
            mat_terrain.setTexture("Tex2", dirt);
            mat_terrain.setFloat("Tex2Scale", 32f);
            setProgress(0.5f, "Loading rocks");

            Texture rock = assetManager.loadTexture("Textures/Terrain/splat/road.jpg");

            rock.setWrap(WrapMode.Repeat);

            mat_terrain.setTexture("Tex3", rock);
            mat_terrain.setFloat("Tex3Scale", 128f);
            setProgress(0.6f, "Creating terrain");

            AbstractHeightMap heightmap;
            Texture heightMapImage = assetManager.loadTexture("Textures/Terrain/splat/mountains512.png");
            heightmap = new ImageBasedHeightMap(heightMapImage.getImage());

            heightmap.load();
            terrain = new TerrainQuad("my terrain", 65, 513, heightmap.getHeightMap());
            setProgress(0.8f, "Positioning terrain");

            terrain.setMaterial(mat_terrain);

            terrain.setLocalTranslation(0, -100, 0);
            terrain.setLocalScale(2f, 1f, 2f);
            setProgress(0.9f, "Loading cameras");

            List<Camera> cameras = new ArrayList<Camera>();
            cameras.add(getCamera());
            TerrainLodControl control = new TerrainLodControl(terrain, cameras);
            terrain.addControl(control);
            setProgress(1f, "Loading complete");

            return null;
        }
    };

    public void setProgress(final float progress, final String loadingText) {
        //since this method is called from another thread, we enqueue the changes to the progressbar to the update loop thread
        enqueue(new Callable() {
            public Object call() throws Exception {
                final int MIN_WIDTH = 32;
                int pixelWidth = (int) (MIN_WIDTH + (progressBarElement.getParent().getWidth() - MIN_WIDTH) * progress);
                progressBarElement.setConstraintWidth(new SizeValue(pixelWidth + "px"));
                progressBarElement.getParent().layoutElements();

                textRenderer.setText(loadingText);
                return null;
            }
        });

    }

    public void showLoadingMenu() {
        nifty.gotoScreen("loadlevel");
        load = true;
    }

    @Override
    public void onStartScreen() {
    }

    @Override
    public void onEndScreen() {
    }

    @Override
    public void bind(Nifty nifty, Screen screen) {
        progressBarElement = nifty.getScreen("loadlevel").findElementByName("progressbar");
    }

    // methods for Controller
    @Override
    public boolean inputEvent(final NiftyInputEvent inputEvent) {
        return false;
    }

    @Override
    public void bind(Nifty nifty, Screen screen, Element elmnt, Properties prprts, Attributes atrbts) {
        progressBarElement = elmnt.findElementByName("progressbar");
    }

    @Override
    public void init(Properties prprts, Attributes atrbts) {
    }

    public void onFocus(boolean getFocus) {
    }

    @Override
    public void stop() {
        super.stop();
        //the pool executor needs to be shut down so the application properly exits.
        exec.shutdown();
    }
}
