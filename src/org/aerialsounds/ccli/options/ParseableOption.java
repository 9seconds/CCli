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


    static protected boolean haveInlineDelimeter (final String option) {
        return option.indexOf(STRING_INLINE_DELIMETER) != -1;
    }


    static public boolean isPureNumerical (final String option) {
        return numericalRegexp.matcher(option).matches();
    }

    static protected final int    patternFlags;
    static public    final String STRING_INLINE_DELIMETER;
    static private   final Pattern numericalRegexp;

    static {
        patternFlags            = Pattern.UNICODE_CASE;
        STRING_INLINE_DELIMETER = "=".intern();
        numericalRegexp         = Pattern.compile("^\\d+$", patternFlags);
    }


    protected final ValueParser parser;


    public ParseableOption (final OptionTypes optionType, final String name, final DataContainer container)
        throws DataIsNotValid {
        super(optionType, name, container);
        parser = getValueType().createParser();
    }


    public boolean appropriate (final String value) {
        return value.equals(fullName);
    }


    public String getInlineValue (final String option) {
        return ( haveInlineValue(option) )
            ? extractInlineValue(option)
            : null;
    }

    @Override
    protected boolean isDataValid () {
        return super.isDataValid() && !haveInlineDelimeter(fullName);
    }


    public Object parse (final String value) {
        return parser.parse(value);
    }


    abstract public    boolean haveInlineValue    (final String option);
    abstract protected String  extractInlineValue (final String option);

}
