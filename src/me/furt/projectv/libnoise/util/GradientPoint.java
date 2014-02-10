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

public class GradientPoint {
    /// Defines a point used to build a color gradient.
    ///
    /// A color gradient is a list of gradually-changing colors.  A color
    /// gradient is defined by a list of <i>gradient points</i>.  Each
    /// gradient point has a position and a color.  In a color gradient, the
    /// colors between two adjacent gradient points are linearly interpolated.
    ///
    /// The ColorGradient class defines a color gradient by a list of these
    /// objects.

    double position;
    ColorCafe color;

    public GradientPoint() {
        position = 0.0;
        color = new ColorCafe(0, 0, 0, 0);
    }

    public GradientPoint(double position, ColorCafe color) {
        this.position = position;
        this.color = color;
    }

    public double getPosition() {
        return position;
    }

    public ColorCafe getColor() {
        return color;
    }

    public void setPosition(double position) {
        this.position = position;
    }

    public void setColor(ColorCafe color) {
        this.color = color;
    }
}
