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



package org.aerialsounds.ccli.valueparsers;




public class BooleanConverter {


    protected class CannotConvert
        extends RuntimeException {
        private static final long serialVersionUID = 5652929665240085597L;
    }


    protected class ValuePair {

        final public boolean value;
        final public boolean converted;

        public ValuePair (final boolean value, final boolean converted) {
            this.value = value;
            this.converted = converted;
        }
    }


    final static private String[] positive             = {"true", "y", "yes"};
    final static private String[] negative             = {"false", "n", "no"};
    final static public byte      NUMERICAL_SIGN_TRUE  = 1;
    final static public byte      NUMERICAL_SIGN_FALSE = 0;

    final static public String TRUE = Boolean.TRUE.toString();
    final static public String FALSE = Boolean.FALSE.toString();


    final boolean convert (final String value) throws CannotConvert {
        final String s = value.toLowerCase();

        if ( interpretAsString(s, positive) )
            return true;
        else if ( interpretAsString(s, negative) )
            return false;

        final ValuePair result = interpretAsNumber(s);
        if ( result.converted )
            return result.value;

        throw new CannotConvert();
    }


    final private ValuePair interpretAsNumber (final String value) {
        try {
            final byte result = Byte.valueOf(value);

            if ( result == NUMERICAL_SIGN_TRUE )
                return new ValuePair(true, true);
            else if ( result == NUMERICAL_SIGN_FALSE )
                return new ValuePair(false, true);
        }
        catch ( Exception e ) { }

        return new ValuePair(false, false);
    }


    final private boolean interpretAsString (final String value, final String[] values) {
        for ( String s : values )
            if ( value.equals(s) )
                return true;

        return false;
    }

}
