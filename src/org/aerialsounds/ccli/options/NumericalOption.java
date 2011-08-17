/*
 * Copyright (C) 2011 by Serge Arkhipov <serge@aerialsounds.org>
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



package org.aerialsounds.ccli.options;



import org.aerialsounds.ccli.OptionTypes;
import org.aerialsounds.ccli.datacontainer.DataContainer;
import org.aerialsounds.ccli.valueparsers.BooleanConverter;



final public class NumericalOption
    extends ParseableOption {



// ===============================================================================================================
// C O N S T R U C T O R S
// ===============================================================================================================



    public
    NumericalOption (final OptionTypes optionType, final String name, final DataContainer container)
    throws DataIsNotValid {
        super(optionType, name, container);
    } /* NumericalOption */



// ===============================================================================================================
// P U B L I C   M E T H O D S
// ===============================================================================================================



    @Override
    public boolean
    haveInlineValue (final String option) {
        return option.equals(fullName);
    } /* haveInlineValue */



// ===============================================================================================================
// P R O T E C T E D   M E T H O D S
// ===============================================================================================================



    @Override
    protected String
    extractInlineValue (final String option) {
        return BooleanConverter.TRUE;
    } /* extractInlineValue */


    @Override
    protected boolean
    isDataValid () {
        return super.isDataValid() && optionType == OptionTypes.SHORT && getValueType().isBoolean();
    } /* isDataValid */


} /* class NumericalOption */

