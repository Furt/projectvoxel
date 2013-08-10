package com.cubes;

import java.util.Random;

public class Noise {

    private Random rand_;
    float roughness_;
    private float[][] grid_;

    public Noise(Random rand, float roughness, int width, int height) {
        roughness_ = (roughness / width);
        grid_ = new float[width][height];
        rand_ = (rand == null ? new Random() : rand);
        initialise();
    }

    public final void initialise() {
        int xh = grid_.length - 1;
        int yh = grid_[0].length - 1;

        grid_[0][0] = (rand_.nextFloat() - 0.5F);
        grid_[0][yh] = (rand_.nextFloat() - 0.5F);
        grid_[xh][0] = (rand_.nextFloat() - 0.5F);
        grid_[xh][yh] = (rand_.nextFloat() - 0.5F);

        generate(0, 0, xh, yh);
    }

    private void generate(int xl, int yl, int xh, int yh) {
        int xm = (xl + xh) / 2;
        int ym = (yl + yh) / 2;
        if ((xl == xm) && (yl == ym)) {
            return;
        }
        grid_[xm][yl] = (0.5F * (grid_[xl][yl] + grid_[xh][yl]));
        grid_[xm][yh] = (0.5F * (grid_[xl][yh] + grid_[xh][yh]));
        grid_[xl][ym] = (0.5F * (grid_[xl][yl] + grid_[xl][yh]));
        grid_[xh][ym] = (0.5F * (grid_[xh][yl] + grid_[xh][yh]));
        float v = roughen(0.5F * (grid_[xm][yl] + grid_[xm][yh]), xl + yl, yh + xh);
        grid_[xm][ym] = v;
        grid_[xm][yl] = roughen(grid_[xm][yl], xl, xh);
        grid_[xm][yh] = roughen(grid_[xm][yh], xl, xh);
        grid_[xl][ym] = roughen(grid_[xl][ym], yl, yh);
        grid_[xh][ym] = roughen(grid_[xh][ym], yl, yh);
        generate(xl, yl, xm, ym);
        generate(xm, yl, xh, ym);
        generate(xl, ym, xm, yh);
        generate(xm, ym, xh, yh);
    }

    private float roughen(float v, int l, int h) {
        return (float) (v + roughness_ * (rand_.nextGaussian() * (h - l)));
    }

    public void printAsCSV() {
        for (int i = 0; i < grid_.length; i++) {
            for (int j = 0; j < grid_[0].length; j++) {
                System.out.print(grid_[i][j]);
                System.out.print(",");
            }
            System.out.println();
        }
    }

    public boolean[][] toBooleans() {
        int w = grid_.length;
        int h = grid_[0].length;
        boolean[][] ret = new boolean[w][h];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                ret[i][j] = (grid_[i][j] < 0.0F);
            }
        }
        return ret;
    }

    public float[][] getGrid() {
        return grid_;
    }

    public float getGridValue(int x, int y) {
        return grid_[x][y];
    }

    public float getMinimum() {
        float minimum = 3.4028235E+38F;
        for (int i = 0; i < grid_.length; i++) {
            float[] row = grid_[i];
            for (int r = 0; r < row.length; r++) {
                if (row[r] < minimum) {
                    minimum = row[r];
                }
            }
        }
        return minimum;
    }

    public float getMaximum() {
        float maximum = 1.4E-45F;
        for (int i = 0; i < grid_.length; i++) {
            float[] row = grid_[i];
            for (int r = 0; r < row.length; r++) {
                if (row[r] > maximum) {
                    maximum = row[r];
                }
            }
        }
        return maximum;
    }
}