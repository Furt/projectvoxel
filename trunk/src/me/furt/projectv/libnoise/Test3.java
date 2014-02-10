/*
 * Copyright (C) 2003, 2004 Jason Bevins (original libnoise code)
 * Copyright (C) 2013, 2014 Terry J Earnheart (java port of libnoise)
 * 
 * This file is part of lb4j.
 * 
 * lb4j is a Java port of the C++ library libnoise, which may be found at 
 * http://libnoise.sourceforge.net/.
 * 
 * lb4j is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * lb4j is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * libnoiseforjava.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package me.furt.projectv.libnoise;

import java.awt.Color;
import java.awt.image.BufferedImage;
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
