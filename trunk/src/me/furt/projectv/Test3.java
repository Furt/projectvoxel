/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.furt.projectv;

import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapFont.Align;
import com.jme3.font.BitmapFont.VAlign;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.math.Vector4f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import java.util.Random;
import tonegod.gui.controls.buttons.ButtonAdapter;
import tonegod.gui.controls.extras.DragElement;
import tonegod.gui.controls.windows.Window;
import tonegod.gui.core.Element;
import tonegod.gui.core.Screen;

/**
 *
 * @author Terry
 */
public class Test3 extends SimpleApplication implements ActionListener {

    class Item extends DragElement {

        int count = 1;
        int id = 0;

        public Item(Screen screen, int id, Vector2f pos, Vector2f dimensions, String img) {
            super(screen, "item" + id, pos, dimensions, Vector4f.ZERO, img);
            this.id = id;
            setTextAlign(Align.Right);
            setTextVAlign(VAlign.Bottom);
            setFontColor(ColorRGBA.Red);
            setTextPadding(5);
            setUseSpringBack(true);
        }

        public void add(int i) {
            count += i;
            updateAmmount();
        }

        public void take(int i) {
            count -= i;
            updateAmmount();
        }

        private void updateAmmount() {
            if (count > 1) {
                setText(Integer.toString(count));
            } else {
                setText("");
            }
        }

        @Override
        public boolean onDragEnd(MouseButtonEvent arg0, Element el) {
            if (null == screen.getDropElement()) {
                take(1);
                Vector3f pos = getCamera().getLocation().clone();
                Vector3f dir = getCamera().getDirection();
                pos.addLocal(dir.mult(10));
                makeCube(id, pos.x, 0, pos.z);
                if (count <= 0) {
                    getElementParent().removeChild(this);
                }
                return false;
            }
            if (screen.getDropElement().equals(inventory)) {
                return true;
            }
            return false;
        }

        @Override
        public void onDragStart(MouseButtonEvent arg0) {
            // TODO Auto-generated method stub
        }
    }

    public static void main(String[] args) {
        Test3 app = new Test3();
        app.start();
    }
    Screen screen;
    Window inventory;

    @Override
    public void simpleInitApp() {
        getFlyByCamera().setEnabled(false);

        mouseInput.setCursorVisible(true);
        screen = new Screen(this);
        guiNode.addControl(screen);

        inventory = new Window(screen, new Vector2f((screen.getWidth() / 2) - 175, (screen.getHeight() / 2) - 100));
        inventory.setWindowTitle("Inventory");
        inventory.setIsResizable(false);
        inventory.setIsDragDropDropElement(true);

        ButtonAdapter inv_sh = new ButtonAdapter(screen, "inv_sh", new Vector2f(10, 10)) {
            @Override
            public void onButtonMouseLeftUp(MouseButtonEvent evt, boolean toggled) {
                if (inventory.getIsClipped()) {
                    inventory.show();
                } else {
                    inventory.hide();
                }
            }
        };

        inv_sh.setText("Inventory");
        screen.addElement(inv_sh);
        screen.addElement(inventory);
        inventory.hide();

        initKeys();

        makeCube(1, 1, 0, 1);
        makeCube(1, 3, 0, 1);
        makeCube(2, 5, 0, -5);
        makeCube(1, -3, 0, 3);
    }

    protected void makeCube(int item_id, float x, float y, float z) {
        Box box = new Box(1, 1, 1);
        Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat1.setColor("Color", ColorRGBA.randomColor());
        Geometry reBoxg = new Geometry("item_" + item_id, box);
        reBoxg.setMaterial(mat1);
        reBoxg.setUserData("item_id", item_id);
        reBoxg.setLocalTranslation(x, y, z);
        rootNode.attachChild(reBoxg);
    }
    private boolean left = false, right = false, up = false, down = false;

    @Override
    public void onAction(String name, boolean pressed, float arg2) {
        if ("Left".equals(name)) {
            left = (pressed) ? (true) : (false);
        }
        if ("Up".equals(name)) {
            up = (pressed) ? (true) : (false);
        }
        if ("Right".equals(name)) {
            right = (pressed) ? (true) : (false);
        }
        if ("Down".equals(name)) {
            down = (pressed) ? (true) : (false);
        }
        if ("Inventory".equals(name)) {
            if (!pressed) {
                if (inventory.getIsClipped()) {
                    inventory.show();
                } else {
                    inventory.hide();
                }
            }
        }
        if ("Take".equals(name)) {
            if (!pressed) {
                take();
            }
        }
    }

    @Override
    public void simpleUpdate(final float tpf) {
        Vector3f camDir = cam.getDirection().clone().multLocal(0.3f);
        Vector3f camLeft = cam.getLeft().clone().multLocal(0.2f);
        if (left) {
            cam.setLocation(cam.getLocation().addLocal(camLeft));
        }
        if (right) {
            cam.setLocation(cam.getLocation().addLocal(camLeft.negate()));
        }
        if (up) {
            cam.setLocation(cam.getLocation().addLocal(camDir));
        }
        if (down) {
            cam.setLocation(cam.getLocation().addLocal(camDir.negate()));
        }
    }

    private void initKeys() {
        inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("Inventory", new KeyTrigger(KeyInput.KEY_I));
        inputManager.addMapping("Take", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));

        inputManager.addListener(this, new String[]{"Left", "Right", "Up", "Down", "Inventory", "Take"});
    }

    private void take() {
        CollisionResults results = new CollisionResults();
        Vector2f click2d = inputManager.getCursorPosition();
        Vector3f click3d = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 0f).clone();
        Vector3f dir = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d).normalizeLocal();
        Ray ray = new Ray(click3d, dir);
        rootNode.collideWith(ray, results);
        if (results.size() > 0) {
            CollisionResult closest = results.getClosestCollision();
            if (!closest.getGeometry().getUserDataKeys().isEmpty()
                    && closest.getGeometry().getUserDataKeys().contains("item_id")) {
                closest.getGeometry().removeFromParent();
                int item_id = closest.getGeometry().getUserData("item_id");
                if (null == screen.getElementById("item" + item_id)) {
                    Random r = new Random();
                    int x = r.nextInt(Math.round(inventory.getWidth()));
                    int y = r.nextInt(Math.round(inventory.getHeight()));
                    inventory.addChild(new Item(
                            screen,
                            item_id,
                            new Vector2f((x > 100) ? (x - 100) : x, (y > 100) ? (y - 100) : y),
                            new Vector2f(100, 100),
                            "Interface/Logo/Monkey.jpg"));
                } else {
                    ((Item) screen.getElementById("item" + item_id)).add(1);
                }
            }
        }
    }
}
