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

import me.furt.projectv.libnoise.Interp;
import me.furt.projectv.libnoise.exception.ExceptionInvalidParam;

public class Blend extends ModuleBase {
    /// Noise module that outputs a weighted blend of the output values from
    /// two source modules given the output value supplied by a control module.
    ///
    /// Unlike most other noise modules, the index value assigned to a source
    /// module determines its role in the blending operation:
    /// - Source module 0 outputs one of the
    ///   values to blend.
    /// - Source module 1 outputs one of the
    ///   values to blend.
    /// - Source module 2 is known as the <i>control
    ///   module</i>.  The control module determines the weight of the
    ///   blending operation.  Negative values weigh the blend towards the
    ///   output value from the source module with an index value of 0.
    ///   Positive values weigh the blend towards the output value from the
    ///   source module with an index value of 1.
    ///
    /// An application can pass the control module to the setControlModule()
    /// method instead of the setSourceModule() method.  This may make the
    /// application code easier to read.
    ///
    /// This noise module uses linear interpolation to perform the blending
    /// operation.
    ///
    /// This noise module requires three source modules.

    public Blend(ModuleBase sourceModuleOne, ModuleBase sourceModuleTwo,
            ModuleBase sourceModuleThree) throws ExceptionInvalidParam {
        super(3);
        setSourceModule(0, sourceModuleOne);
        setSourceModule(1, sourceModuleTwo);
        setSourceModule(2, sourceModuleThree);
    }

    public double getValue(double x, double y, double z) {
        assert (sourceModules[0] != null);
        assert (sourceModules[1] != null);
        assert (sourceModules[2] != null);

        double v0 = sourceModules[0].getValue(x, y, z);
        double v1 = sourceModules[1].getValue(x, y, z);
        double alpha = (sourceModules[2].getValue(x, y, z) + 1.0) / 2.0;

        return Interp.linearInterp(v0, v1, alpha);
    }
}
