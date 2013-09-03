package com.cubes;

import com.jme3.math.Vector3f;
import java.util.Map;

public class Util {

    private static final float MAX_FLOAT_ROUNDING_DIFFERENCE = 1.0E-004F;

    public static boolean isValidIndex(byte[][][] array, Vector3Int index) {
        return (index.getX() >= 0) && (index.getX() < array.length) && (index.getY() >= 0) && (index.getY() < array[0].length) && (index.getZ() >= 0) && (index.getZ() < array[0][0].length);
    }

    public static boolean isValidIndex(Object[][][] array, Vector3Int index) {
        return (index.getX() >= 0) && (index.getX() < array.length) && (index.getY() >= 0) && (index.getY() < array[0].length) && (index.getZ() >= 0) && (index.getZ() < array[0][0].length);
    }

    public static Vector3f compensateFloatRoundingErrors(Vector3f vector) {
        return new Vector3f(compensateFloatRoundingErrors(vector.getX()), compensateFloatRoundingErrors(vector.getY()), compensateFloatRoundingErrors(vector.getZ()));
    }

    public static float compensateFloatRoundingErrors(float number) {
        float remainder = number % 1.0F;
        if ((remainder < 1.0E-004F) || (remainder > 0.9999F)) {
            number = Math.round(number);
        }
        return number;
    }
    
    // TODO could be cause 
    public static <T, E> T getHashKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return (T) entry.getKey();
            }
        }
        return null;
    }
}