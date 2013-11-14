package me.furt.projectv.noise;

/**
 * ProjectV
 *
 * @author Furt
 */
public class Test {

    public static void main(String args[]) {
        SimplexNoise simplexNoise = new SimplexNoise(100, 0.1, 5000);

        double xStart = 0;
        double XEnd = 32;
        double yStart = 0;
        double yEnd = 32;

        int xResolution = 16;
        int yResolution = 16;

        double[][] result = new double[xResolution][yResolution];

        for (int i = 0; i < xResolution; i++) {
            for (int j = 0; j < yResolution; j++) {
                int x = (int) (xStart + i * ((XEnd - xStart) / xResolution));
                int y = (int) (yStart + j * ((yEnd - yStart) / yResolution));
                result[i][j] = 0.5 * (1 + simplexNoise.getNoise(x, y));
                System.out.println(result[i][j]);
            }
        }

        ImageWriter.greyWriteImage(result);



    }
}
