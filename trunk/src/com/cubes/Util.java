package com.cubes;

import com.jme3.math.Vector3f;
import java.util.Map;

public class Util {

    private static final float MAX_FLOAT_ROUNDING_DIFFERENCE = 0.0001f;

    /**
     *
     * @param array
     * @param index
     * @return
     */
    public static boolean isValidIndex(byte[][][] array, Vector3Int index) {
        return ((index.getX() >= 0) && (index.getX() < array.length)
                && (index.getY() >= 0) && (index.getY() < array[0].length)
                && (index.getZ() >= 0) && (index.getZ() < array[0][0].length));
    }

    /**
     *
     * @param array
     * @param index
     * @return
     */
    public static boolean isValidIndex(Object[][][] array, Vector3Int index) {
        return ((index.getX() >= 0) && (index.getX() < array.length)
                && (index.getY() >= 0) && (index.getY() < array[0].length)
                && (index.getZ() >= 0) && (index.getZ() < array[0][0].length));
    }

    /**
     *
     * @param vector
     * @return
     */
    public static Vector3f compensateFloatRoundingErrors(Vector3f vector) {
        return new Vector3f(compensateFloatRoundingErrors(vector.getX()),
                compensateFloatRoundingErrors(vector.getY()),
                compensateFloatRoundingErrors(vector.getZ()));
    }

    /**
     *
     * @param number
     * @return
     */
    public static float compensateFloatRoundingErrors(float number) {
        float remainder = (number % 1);
        if ((remainder < MAX_FLOAT_ROUNDING_DIFFERENCE) || (remainder > (1 - MAX_FLOAT_ROUNDING_DIFFERENCE))) {
            number = Math.round(number);
        }
        return number;
    }

    /**
     *
     * @param <T>
     * @param <E>
     * @param map
     * @param value
     * @return
     */
    public static <T, E> T getHashKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
}
