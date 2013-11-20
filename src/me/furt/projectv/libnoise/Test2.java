package me.furt.projectv.libnoise;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;
import javax.imageio.ImageIO;
import me.furt.projectv.libnoise.module.Billow;
import me.furt.projectv.libnoise.module.Perlin;
import me.furt.projectv.libnoise.module.RidgedMulti;
import me.furt.projectv.libnoise.module.ScaleBias;
import me.furt.projectv.libnoise.module.Select;
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
public class Test2 {

    public static void main(String[] args) {
        try {
            RidgedMulti mountainTerrain = new RidgedMulti();

            Billow baseFlatTerrain = new Billow();
            baseFlatTerrain.setFrequency(2.0);

            ScaleBias flatTerrain = new ScaleBias(baseFlatTerrain);
            flatTerrain.setScale(0.125);
            flatTerrain.setBias(-0.75);

            Perlin terrainType = new Perlin();
            terrainType.setFrequency(0.5);
            terrainType.setPersistence(0.25);

            Select finalTerrain = new Select(flatTerrain, mountainTerrain, terrainType);
            finalTerrain.setBounds(0.0, 1000.0);
            finalTerrain.setEdgeFalloff(0.125);

            NoiseMap heightMap = new NoiseMap(256, 256);
            NoiseMapBuilderPlane heightMapBuilder = new NoiseMapBuilderPlane();
            heightMapBuilder.setSourceModule(finalTerrain);
            heightMapBuilder.setDestNoiseMap(heightMap);
            heightMapBuilder.setDestSize(256, 256);
            heightMapBuilder.setBounds(6.0, 10.0, 1.0, 5.0);
            heightMapBuilder.build();

            RendererImage renderer = new RendererImage();
            ImageCafe image = new ImageCafe(heightMap.getWidth(), heightMap.getHeight());
            renderer.setSourceNoiseMap(heightMap);
            renderer.setDestImage(image);
            renderer.clearGradient();
            renderer.addGradientPoint(-1.00, new ColorCafe(32, 160, 0, 255)); // grass
            renderer.addGradientPoint(-0.25, new ColorCafe(224, 224, 0, 255)); // dirt
            renderer.addGradientPoint(0.25, new ColorCafe(128, 128, 128, 255)); // rock
            renderer.addGradientPoint(1.00, new ColorCafe(255, 255, 255, 255)); // snow
            renderer.enableLight(true);
            renderer.setLightContrast(3.0);
            renderer.setLightBrightness(2.0);
            renderer.render();

            BufferedImage im = buffBuilder(image.getHeight(), image.getWidth(), image);
            ImageIO.write(im, "png", new File("./noise/libnoise_2_" + new Random().nextInt() + ".png"));
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
