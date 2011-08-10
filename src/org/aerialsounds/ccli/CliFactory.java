/*
 * Copyright (C) 2011 by Sergey Arkhipov <serge@aerialsounds.org>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */



package org.aerialsounds.ccli;



import static org.aerialsounds.ccli.options.AbstractOption.DataIsNotValid;

import org.aerialsounds.ccli.datacontainer.DataContainer;
import org.aerialsounds.ccli.options.LongOption;
import org.aerialsounds.ccli.options.NumericalOption;
import org.aerialsounds.ccli.options.ParseableOption;
import org.aerialsounds.ccli.options.ShortOption;



class CliFactory {



// ===============================================================================================================
// F I E L D S
// ===============================================================================================================



    private final CCli repository;



 // ===============================================================================================================
 // C O N S T R U C T O R S
 // ===============================================================================================================



    public
    CliFactory (final CCli repository) {
        this.repository = repository;
    } /* CliFactory */



// ===============================================================================================================
// P U B L I C   M E T H O D S
// ===============================================================================================================



    public DataContainer
    createDataContainer (final Object defaultValue, final ValueTypes valueType, final String help) {
        final DataContainer container = new DataContainer(repository);

        container.setDefaultValue(defaultValue);
        container.setValueType(valueType);
        container.setHelp(help);

        return container;
    } /* createDataContainer */


    public ParseableOption
    createOption (final OptionTypes type, final String name, final DataContainer container)
    throws DataIsNotValid {
        if ( type == OptionTypes.SHORT && ParseableOption.isPureNumerical(name) )
            return new NumericalOption(type, name, container);

        switch ( type ) {
            case SHORT:
                return new ShortOption(type, name, container);

            default:
                return new LongOption(type, name, container);
        }
    } /* createOption */


} /* class CliFactory */

