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
package me.furt.projectv.libnoise.util;

public class MiscUtilities {

    // Performs linear interpolation between two 8-bit channel values.
    public static short blendChannel(int red, int red2, float alpha) {
        double c0 = (float) red / 255.0;
        double c1 = (float) red2 / 255.0;
        return (short) (((c1 * alpha) + (c0 * (1.0f - alpha))) * 255.0f);
    }

    // Performs linear interpolation between two colors 
    public static ColorCafe linearInterpColor(ColorCafe color0, ColorCafe color1,
            float alpha) {
        ColorCafe color = new ColorCafe(
                blendChannel(color0.red, color1.red, alpha),
                blendChannel(color0.green, color1.green, alpha),
                blendChannel(color0.blue, color1.blue, alpha),
                blendChannel(color0.alpha, color1.alpha, alpha));
        return color;
    }
}
