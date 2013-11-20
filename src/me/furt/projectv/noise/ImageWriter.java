package me.furt.projectv.noise;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * ProjectV
 *
 * @author Furt
 */
public class ImageWriter {
    //just convinence methods for debug

    public static void greyWriteImage(double[][] data, int rand) {
        //this takes and array of doubles between 0 and 1 and generates a grey scale image from them

        BufferedImage image = new BufferedImage(data.length, data[0].length, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < data[0].length; y++) {
            for (int x = 0; x < data.length; x++) {
                if (data[x][y] > 1) {
                    data[x][y] = 1;
                }
                if (data[x][y] < 0) {
                    data[x][y] = 0;
                }
                Color col = new Color((float) data[x][y], (float) data[x][y], (float) data[x][y]);
                image.setRGB(x, y, col.getRGB());
            }
        }

        try {
            // retrieve image

            File outputfile = new File("./noise/noise_" + rand + ".png");
            outputfile.createNewFile();

            ImageIO.write(image, "png", outputfile);
        } catch (IOException e) {
            //o no! Blank catches are bad
            throw new RuntimeException("I didn't handle this very well");
        }
    }

    public static void colorWriteImage(double[][] data, int rand) {

        BufferedImage image = new BufferedImage(data.length, data[0].length, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < data[0].length; y++) {
            for (int x = 0; x < data.length; x++) {
                System.out.println("Simplex Noise: " + data[x][y]);
                Color col = new Color(0, 0, 0);
                //Dark Blue
                if (data[x][y] > -1 && data[x][y] <= -0.25) {
                    col = new Color(0, 0, 153);
                }
                // Lighter Blue
                if (data[x][y] > -0.25 && data[x][y] <= 0) {
                    col = new Color(0, 0, 255);
                }
                // Light Blue
                if (data[x][y] > 0 && data[x][y] <= 0.0625) {
                    col = new Color(0, 0, 255);
                }
                // Tan
                if (data[x][y] > 0.0625 && data[x][y] <= 0.125) {
                    col = new Color(255, 255, 204);
                }
                // Green
                if (data[x][y] > 0.125 && data[x][y] <= 0.375) {
                    col = new Color(102, 204, 51);
                }
                // Brown
                if (data[x][y] > 0.375 && data[x][y] <= 0.75) {
                    col = new Color(153, 102, 0);
                }
                // Grey
                if (data[x][y] > 0.75 && data[x][y] <= 0.90) {
                    col = new Color(153, 153, 153);
                }
                // White
                if (data[x][y] > 0.90) {
                    col = new Color(255, 255, 255);
                }

                //col = new Color((float) data[x][y], (float) data[x][y], (float) data[x][y]);
                image.setRGB(x, y, col.getRGB());
            }
        }

        try {

            File outputfile = new File("./noise/simplex_noise_" + rand + ".png");
            outputfile.createNewFile();

            ImageIO.write(image, "png", outputfile);
        } catch (IOException e) {
            throw new RuntimeException("I didn't handle this very well");
        }
    }

    public static void colorWriteImage(float[][] data, int rand) {

        BufferedImage image = new BufferedImage(data.length, data[0].length, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < data[0].length; y++) {
            for (int x = 0; x < data.length; x++) {
                System.out.println("Value Noise: " + data[x][y]);
                Color col = new Color(0, 0, 0);
                //Dark Blue
                if (data[x][y] > -1 && data[x][y] <= -0.25) {
                    col = new Color(0, 0, 153);
                }
                // Lighter Blue
                if (data[x][y] > -0.25 && data[x][y] <= 0) {
                    col = new Color(0, 0, 255);
                }
                // Light Blue
                if (data[x][y] > 0 && data[x][y] <= 0.0625) {
                    col = new Color(0, 0, 255);
                }
                // Tan
                if (data[x][y] > 0.0625 && data[x][y] <= 0.125) {
                    col = new Color(255, 255, 204);
                }
                // Green
                if (data[x][y] > 0.125 && data[x][y] <= 0.375) {
                    col = new Color(102, 204, 51);
                }
                // Brown
                if (data[x][y] > 0.375 && data[x][y] <= 0.75) {
                    col = new Color(153, 102, 0);
                }
                // Grey
                if (data[x][y] > 0.75 && data[x][y] <= 0.90) {
                    col = new Color(153, 153, 153);
                }
                // White
                if (data[x][y] > 0.90) {
                    col = new Color(255, 255, 255);
                }

                //col = new Color((float) data[x][y], (float) data[x][y], (float) data[x][y]);
                image.setRGB(x, y, col.getRGB());
            }
        }

        try {

            File outputfile = new File("./noise/value_noise_" + rand + ".png");
            outputfile.createNewFile();

            ImageIO.write(image, "png", outputfile);
        } catch (IOException e) {
            throw new RuntimeException("I didn't handle this very well");
        }
    }
}
