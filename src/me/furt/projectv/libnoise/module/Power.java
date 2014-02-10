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
package me.furt.projectv.libnoise.module;

import me.furt.projectv.libnoise.exception.ExceptionInvalidParam;

public class Power extends ModuleBase {
    /// Noise module that raises the output value from a first source module
    /// to the power of the output value from a second source module.
    ///
    /// The first source module must have an index value of 0.
    ///
    /// The second source module must have an index value of 1.
    ///
    /// This noise module requires two source modules.

    public Power(ModuleBase sourceModuleOne, ModuleBase sourceModuleTwo) throws ExceptionInvalidParam {
        super(2);
        setSourceModule(0, sourceModuleOne);
        setSourceModule(1, sourceModuleTwo);
    }

    public double getValue(double x, double y, double z) {
        assert (sourceModules[0] != null);
        assert (sourceModules[1] != null);

        return Math.pow(sourceModules[0].getValue(x, y, z),
                sourceModules[1].getValue(x, y, z));
    }
}
