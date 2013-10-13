/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.furt.projectv;

import com.jme3.app.SimpleApplication;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector4f;
import tonegod.gui.controls.extras.Indicator;
import tonegod.gui.controls.lists.Slider;
import tonegod.gui.core.Screen;

/**
 *
 * @author Terry
 */
public class Test4 extends SimpleApplication {
    private Screen screen;

    @Override
    public void simpleInitApp() {
        flyCam.setDragToRotate(true);
        inputManager.setCursorVisible(true);

        screen = new Screen(this);
        guiNode.addControl(screen);

        final ColorRGBA color = new ColorRGBA();

        final Indicator ind = new Indicator(
                screen,
                new Vector2f(50, 50),
                new Vector2f(300, 30),
                Indicator.Orientation.HORIZONTAL) {
            @Override
            public void onChange(float currentValue, float currentPercentage) {
            }
        };
        ind.setBaseImage(screen.getStyle("Window").getString("defaultImg"));
        ind.setIndicatorColor(ColorRGBA.randomColor());
        ind.setAlphaMap(screen.getStyle("Indicator").getString("alphaImg"));
        ind.setIndicatorPadding(new Vector4f(7, 7, 7, 7));
        ind.setMaxValue(100);
        ind.setDisplayPercentage();

        screen.addElement(ind);

        Slider slider = new Slider(screen, new Vector2f(100, 100), Slider.Orientation.HORIZONTAL, true) {
            @Override
            public void onChange(int selectedIndex, Object value) {
                float blend = (float)value.hashCode() * 0.01f;
                float blend1 = selectedIndex * 0.01f;
                color.interpolate(ColorRGBA.Red, ColorRGBA.Green, blend);
                ind.setIndicatorColor(color);
                ind.setCurrentValue((Integer) value);
            }
        };

        screen.addElement(slider);

    }
    
    public static void main(String[] args) {
        Test4 app = new Test4();
        app.start();
    }
}
