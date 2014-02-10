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

public class Misc {
    /// Clamps a value onto a clamping range.
    ///
    /// @param value The value to clamp.
    /// @param lowerBound The lower bound of the clamping range.
    /// @param upperBound The upper bound of the clamping range.
    ///
    /// @returns
    /// - @a value if @a value lies between @a lowerBound and @a upperBound.
    /// - @a lowerBound if @a value is less than @a lowerBound.
    /// - @a upperBound if @a value is greater than @a upperBound.
    ///
    /// This function does not modify any parameters.

    public static int ClampValue(int value, int lowerBound, int upperBound) {
        if (value < lowerBound) {
            return lowerBound;
        } else if (value > upperBound) {
            return upperBound;
        } else {
            return value;
        }
    }
}
