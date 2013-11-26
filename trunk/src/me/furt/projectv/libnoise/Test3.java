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
import me.furt.projectv.libnoise.module.Turbulence;
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
public class Test3 {

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

            Select terrainSelector = new Select(flatTerrain, mountainTerrain, terrainType);
            terrainSelector.setBounds(0.0, 1000.0);
            terrainSelector.setEdgeFalloff(0.125);

            ScaleBias terrainScaler = new ScaleBias(terrainSelector);
            terrainScaler.setScale (80.0);
            terrainScaler.setBias (80.0);

            Turbulence finalTerrain = new Turbulence(terrainScaler);
            finalTerrain.setFrequency(4.0);
            finalTerrain.setPower(0.125);

            

            NoiseMap heightMap = new NoiseMap(160, 160);
            NoiseMapBuilderPlane heightMapBuilder = new NoiseMapBuilderPlane();
            heightMapBuilder.setSourceModule(finalTerrain);
            heightMapBuilder.setDestNoiseMap(heightMap);
            heightMapBuilder.setDestSize(160, 160);
            //heightMapBuilder.setBounds(6.0, 10.0, 1.0, 5.0);
            heightMapBuilder.build();
            
            for (int x = 0; x < 160; x++) {
                for (int z = 0; z < 160; z++) {
                    System.out.println(heightMap.getValue(x, z));
                }
            }

            
        } catch (Exception e) {
        }
    }

    private static BufferedImage buffBuilder(int height, int width, ImageCafe imageCafe) {

        BufferedImage im = new BufferedImage(height, width, BufferedImage.TYPE_INT_ARGB);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int c = getRGBA(imageCafe.getValue(i, j));
                im.setRGB(i, j, c);
            }
        }
        return im;
    }

    private static int getRGBA(ColorCafe colorCafe) {
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
