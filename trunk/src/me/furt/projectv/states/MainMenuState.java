/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.furt.projectv.states;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;

/**
 *
 * @author Terry
 */
public class MainMenuState extends AbstractAppState {

    private SimpleApplication app;
    private Node rootNode;
    private Node guiNode;
    private AssetManager assetManager;
    private Camera cam;
    private Node menu;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        this.app = (SimpleApplication) app;
        this.rootNode = this.app.getRootNode();
        this.guiNode = this.app.getGuiNode();
        this.assetManager = this.app.getAssetManager();
        this.cam = this.app.getCamera();
        this.menu = new Node("Menu");
        setupMenu();
    }

    @Override
    public void cleanup() {
        this.rootNode.detachChild(menu);
    }

    public void setupMenu() {
    }
}
