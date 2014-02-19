package me.furt.projectv.core.network;

public class BitUtil {

    public static int getNeededBitsCount(int value) {
        return (32 - Integer.numberOfLeadingZeros(value));
    }
}
