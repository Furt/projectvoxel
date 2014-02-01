package me.furt.projectv.core;

import com.jme3.math.FastMath;
import java.util.HashMap;
import java.util.Map;

/**
 * ProjectV
 *
 * @author Furt
 */
public class Seed {

    final Map<Character, Integer> map;
    String str = null;

    public Seed(String s) {
        map = new HashMap<Character, Integer>();
        str = s;

        map.put('a', 1);
        map.put('b', 2);
        map.put('c', 3);
        map.put('d', 4);
        map.put('e', 5);
        map.put('f', 6);
        map.put('g', 7);
        map.put('h', 8);
        map.put('i', 9);
        map.put('j', 10);
        map.put('k', 11);
        map.put('l', 12);
        map.put('m', 13);
        map.put('n', 14);
        map.put('o', 15);
        map.put('p', 16);
        map.put('q', 17);
        map.put('r', 18);
        map.put('s', 19);
        map.put('t', 20);
        map.put('u', 21);
        map.put('v', 22);
        map.put('w', 23);
        map.put('x', 24);
        map.put('y', 25);
        map.put('z', 26);
    }
    
    public int returnInteger() {
        int i = 0;
        for (final char c : str.toCharArray()) {
            final int val;

            val = map.get(c);

            i += val;
        }
        
        return (int)Math.round(FastMath.sqr(i*FastMath.PI));
    }
}
