package me.furt.projectv.libnoise;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import me.furt.projectv.libnoise.module.Perlin;
import me.furt.projectv.libnoise.util.ColorCafe;
import me.furt.projectv.libnoise.util.ImageCafe;
import me.furt.projectv.libnoise.util.NoiseMap;
import me.furt.projectv.libnoise.util.NoiseMapBuilderPlane;
import me.furt.projectv.libnoise.util.RendererImage;

/**
 * ProjectV
 *
 * @author Furt
 */
public class Test {

    public static void main(String[] args) {
        Logger.getLogger("").setLevel(Level.SEVERE);
        try {
            Perlin myModule = new Perlin();
            NoiseMap heightMap = new NoiseMap(256, 256);
            NoiseMapBuilderPlane heightMapBuilder = new NoiseMapBuilderPlane();
            heightMapBuilder.setSourceModule(myModule);
            heightMapBuilder.setDestNoiseMap(heightMap);
            heightMapBuilder.setDestSize(256, 256);
            heightMapBuilder.setBounds(2.0, 6.0, 1.0, 5.0);
            heightMapBuilder.build();

            RendererImage renderer = new RendererImage();
            ImageCafe image = new ImageCafe(heightMap.getWidth(), heightMap.getHeight());
            renderer.setSourceNoiseMap(heightMap);
            renderer.setDestImage(image);
            renderer.clearGradient();
            renderer.addGradientPoint(-1.0000, new ColorCafe(0, 0, 128, 255)); // deeps
            renderer.addGradientPoint(-0.2500, new ColorCafe(0, 0, 255, 255)); // shallow
            renderer.addGradientPoint(0.0000, new ColorCafe(0, 128, 255, 255)); // shore
            renderer.addGradientPoint(0.0625, new ColorCafe(240, 240, 64, 255)); // sand
            renderer.addGradientPoint(0.1250, new ColorCafe(32, 160, 0, 255)); // grass
            renderer.addGradientPoint(0.3750, new ColorCafe(224, 224, 0, 255)); // dirt
            renderer.addGradientPoint(0.7500, new ColorCafe(128, 128, 128, 255)); // rock
            renderer.addGradientPoint(1.0000, new ColorCafe(255, 255, 255, 255)); // snow
            renderer.enableLight(true);
            renderer.render();

            BufferedImage im = buffBuilder(image.getHeight(), image.getWidth(), image);
            ImageIO.write(im, "png", new File("./noise/libnoise_" + new Random().nextInt() + ".png"));

        } catch (Exception e) {
        }


    }

    public static BufferedImage buffBuilder(int height, int width, ImageCafe imageCafe) {

        BufferedImage im = new BufferedImage(height, width, BufferedImage.TYPE_INT_ARGB);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int c = getRGBA(imageCafe.getValue(i, j));
                im.setRGB(i, j, c);
            }
        }
        return im;
    }

    public static int getRGBA(ColorCafe colorCafe) {
        int red, blue, green, alpha;
        red = colorCafe.getRed();
        blue = colorCafe.getBlue();
        green = colorCafe.getGreen();
        alpha = colorCafe.getAlpha();
        Color color = new Color(red, green, blue, alpha);
        int rgbnumber = color.getRGB();
        return rgbnumber;
    }
}
