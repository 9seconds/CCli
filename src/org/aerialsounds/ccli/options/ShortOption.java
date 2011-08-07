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



import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.aerialsounds.ccli.OptionTypes;
import org.aerialsounds.ccli.datacontainer.DataContainer;



public class ShortOption
    extends ParseableOption {

    static protected final Pattern inlineRegexp;
    static protected final Pattern numbersRegexp;

    static {
        inlineRegexp = Pattern.compile(
            "^[\\D&&[^\\.]]+((?:(?:\\d*\\.?\\d+)|(?:\\d+\\.?\\d*))\\d*(?:[eE][\\+\\-]?\\d+)?)[fd]?$",
            patternFlags
        );
        numbersRegexp = Pattern.compile("\\D*(\\d+)\\D*", patternFlags);
    }


    static public boolean haveNumbers (final String option) {
        return numbersRegexp.matcher(option).matches();
    }


    public ShortOption (final OptionTypes optionType, final String name, final DataContainer container)
        throws DataIsNotValid {
        super(optionType, name, container);
    }


    @Override
    public boolean appropriate (final String value) {
        return super.appropriate(value) || (value.startsWith(fullName) && haveInlineValue(value));
    }


    @Override
    protected String extractInlineValue (final String option) {
        final Matcher match = inlineRegexp.matcher(option);
        return ( match.find() )
            ? match.group(1)
            : null;
    }


    @Override
    public boolean haveInlineValue (final String option) {
        return getValueType().isNumber() && !haveInlineDelimeter(option) && inlineRegexp.matcher(option).matches();
    }


    @Override
    protected boolean isDataValid () {
        return super.isDataValid() && optionType == OptionTypes.SHORT && name.length() == 1;
    }

}
