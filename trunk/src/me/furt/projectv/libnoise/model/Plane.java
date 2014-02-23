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
package me.furt.projectv.libnoise.model;

import me.furt.projectv.libnoise.module.ModuleBase;

public class Plane {
    /// Model that defines the surface of a plane.
    ///
    /// This model returns an output value from a noise module given the
    /// coordinates of an input value located on the surface of an ( @a x,
    /// @a z ) plane.
    ///
    /// To generate an output value, pass the ( @a x, @a z ) coordinates of
    /// an input value to the GetValue() method.
    ///
    /// This model is useful for creating:
    /// - two-dimensional textures
    /// - terrain height maps for local areas
    ///
    /// This plane extends infinitely in both directions.

    /// A pointer to the noise module used to generate the output values.
    ModuleBase module;

    public Plane() {
        module = new ModuleBase(1);
    }

    public Plane(ModuleBase module) {
        this.module = module;
    }

    /// Returns the output value from the noise module given the
    /// ( @a x, @a z ) coordinates of the specified input value located
    /// on the surface of the plane.
    ///
    /// @param x The @a x coordinate of the input value.
    /// @param z The @a z coordinate of the input value.
    ///
    /// @returns The output value from the noise module.
    ///
    /// @pre A noise module was passed to the setModule() method.
    ///
    /// This output value is generated by the noise module passed to the
    /// setModule() method.
    public double getValue(double x, double z) {
        assert (module != null);

        return module.getValue(x, 0, z);
    }

    /// Returns the noise module that is used to generate the output
    /// values.
    ///
    /// @returns A reference to the noise module.
    ///
    /// @pre A noise module was passed to the setModule() method.
    public ModuleBase getModule() {
        assert (module != null);
        return module;
    }

    /// Sets the noise module that is used to generate the output values.
    ///
    /// @param module The noise module that is used to generate the output
    /// values.
    ///
    /// This noise module must exist for the lifetime of this object,
    /// until you pass a new noise module to this method.
    public void setModule(ModuleBase module) {
        this.module = module;
    }
}