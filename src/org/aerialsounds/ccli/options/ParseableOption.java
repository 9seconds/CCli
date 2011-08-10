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



package org.aerialsounds.ccli.options;



import java.util.regex.Pattern;

import org.aerialsounds.ccli.OptionTypes;
import org.aerialsounds.ccli.datacontainer.DataContainer;
import org.aerialsounds.ccli.valueparsers.ValueParser;



abstract public class ParseableOption
    extends AbstractOption {



// ===============================================================================================================
// S T A T I C   F I E L D S
// ===============================================================================================================



    static protected final int    patternFlags;
    static public    final String STRING_INLINE_DELIMETER;
    static private   final Pattern numericalRegexp;



// ===============================================================================================================
// S T A T I C   I N I T I A L I Z A T O R
// ===============================================================================================================



    static {
        patternFlags            = Pattern.UNICODE_CASE;
        STRING_INLINE_DELIMETER = "=".intern();
        numericalRegexp         = Pattern.compile("^\\d+$", patternFlags);
    } /* static */



// ===============================================================================================================
// S T A T I C   M E T H O D S
// ===============================================================================================================



    static protected boolean
    haveInlineDelimeter (final String option) {
        return option.indexOf(STRING_INLINE_DELIMETER) != -1;
    } /* haveInlineDelimeter */


    static public boolean
    isPureNumerical (final String option) {
        return numericalRegexp.matcher(option).matches();
    } /* isPureNumerical */



// ===============================================================================================================
// F I E L D S
// ===============================================================================================================



    private final ValueParser parser;



// ===============================================================================================================
// C O N S T R U C T O R S
// ===============================================================================================================


    public
    ParseableOption (final OptionTypes optionType, final String name, final DataContainer container)
    throws DataIsNotValid {
        super(optionType, name, container);
        parser = getValueType().createParser();
    } /* ParseableOption */



// ===============================================================================================================
// P U B L I C   M E T H O D S
// ===============================================================================================================



    public boolean
    appropriate (final String value) {
        return value.equals(fullName);
    } /* appropriate */


    final public String
    getInlineValue (final String option) {
        return ( haveInlineValue(option) )
            ? extractInlineValue(option)
            : null;
    } /* getInlineValue */


    final public Object
    parse (final String value) {
        return parser.parse(value);
    } /* parse */


    abstract public boolean
    haveInlineValue (final String option);



// ===============================================================================================================
// P R O T E C T E D   M E T H O D S
// ===============================================================================================================



    @Override
    protected boolean
    isDataValid () {
        return super.isDataValid() && !haveInlineDelimeter(fullName);
    } /* isDataValid */


    abstract protected String
    extractInlineValue (final String option);


} /* class ParseableOption */

