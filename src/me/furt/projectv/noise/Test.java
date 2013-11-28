package me.furt.projectv.noise;

import java.util.Random;
import me.furt.projectv.world.Seed;

/**
 * ProjectV
 *
 * @author Furt
 */
public class Test {

    public static void main(String args[]) {
        int rand = new Random().nextInt();
        Seed seed = new Seed("jim bob".replace(" ", ""));
        // Largest Feature, Persistence, Seed
        SimplexNoise simplexNoise = new SimplexNoise(60, 0.9, seed.returnInteger());

        double xStart = 0;
        double XEnd = 200;
        double yStart = 0;
        double yEnd = 200;

        int xResolution = 200;
        int yResolution = 200;

        double[][] result = new double[xResolution][yResolution];

        for (int i = 0; i < xResolution; i++) {
            for (int j = 0; j < yResolution; j++) {
                int x = (int) (xStart + i * ((XEnd - xStart) / xResolution));
                int y = (int) (yStart + j * ((yEnd - yStart) / yResolution));
                result[i][j] = 0.5 * (1 + simplexNoise.getNoise(x, y));
            }
        }

        //ImageWriter.colorWriteImage(result, rand);

        // ValueNoise

        float[][] vNoise = ValueNoise.GenerateValueNoise(200, 200, 10);
        ImageWriter.colorWriteImage(vNoise, rand);



    }
}
