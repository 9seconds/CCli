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



public class LongOption
    extends ParseableOption {



// ===============================================================================================================
// F I E L D S
// ===============================================================================================================



    protected final String fullNameWithDelimeter;



 // ===============================================================================================================
 // C O N S T R U C T O R S
 // ===============================================================================================================



    public
    LongOption (final OptionTypes optionType, final String name, final DataContainer container)
    throws DataIsNotValid {
        super(optionType, name, container);
        fullNameWithDelimeter = fullName + STRING_INLINE_DELIMETER;
    } /* LongOption */


    @Override
    public boolean
    appropriate (final String value) {
        return super.appropriate(value) || value.startsWith(fullNameWithDelimeter);
    } /* appropriate */


    @Override
    public boolean
    haveInlineValue (final String option) {
        return option.indexOf(STRING_INLINE_DELIMETER) == fullName.length();
    } /* haveInlineValue */



// ===============================================================================================================
// P R O T E C T E D   M E T H O D S
// ===============================================================================================================



    @Override
    protected String
    extractInlineValue (final String option) {
        return ( option.endsWith(STRING_INLINE_DELIMETER) )
            ? "".intern()
            : option.substring(fullName.length() + 1);
    } /* extractInlineValue */


    @Override
    protected boolean
    isDataValid () {
        return super.isDataValid() && optionType != OptionTypes.SHORT;
    } /* isDataValid */


} /* class LongOption */

