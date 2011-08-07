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



import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.aerialsounds.ccli.valueparsers.AtomicBooleanParser;
import org.aerialsounds.ccli.valueparsers.AtomicIntegerParser;
import org.aerialsounds.ccli.valueparsers.AtomicLongParser;
import org.aerialsounds.ccli.valueparsers.BigDecimalParser;
import org.aerialsounds.ccli.valueparsers.BigIntegerParser;
import org.aerialsounds.ccli.valueparsers.BooleanParser;
import org.aerialsounds.ccli.valueparsers.ByteParser;
import org.aerialsounds.ccli.valueparsers.CharParser;
import org.aerialsounds.ccli.valueparsers.DoubleParser;
import org.aerialsounds.ccli.valueparsers.FloatParser;
import org.aerialsounds.ccli.valueparsers.IntegerParser;
import org.aerialsounds.ccli.valueparsers.LongParser;
import org.aerialsounds.ccli.valueparsers.ShortParser;
import org.aerialsounds.ccli.valueparsers.StringParser;
import org.aerialsounds.ccli.valueparsers.ValueParser;



public enum ValueTypes {
    ATOMIC_BOOLEAN (AtomicBoolean.class),
    ATOMIC_INTEGER (AtomicInteger.class),
    ATOMIC_LONG    (AtomicLong.class),
    BIG_DECIMAL    (BigDecimal.class),
    BIG_INTEGER    (BigInteger.class),
    BOOLEAN        (Boolean.class),
    BYTE           (Byte.class),
    CHAR           (Character.class),
    DOUBLE         (Double.class),
    FLOAT          (Float.class),
    INTEGER        (Integer.class),
    LONG           (Long.class),
    SHORT          (Short.class),
    STRING         (String.class);


    private final Class<?> type;


    private ValueTypes (final Class<?> type) {
        this.type = type;
    }


    public ValueParser createParser () {
        switch ( this ) {
            case ATOMIC_BOOLEAN:
                return new AtomicBooleanParser();

            case ATOMIC_INTEGER:
                return new AtomicIntegerParser();

            case ATOMIC_LONG:
                return new AtomicLongParser();

            case BIG_DECIMAL:
                return new BigDecimalParser();

            case BIG_INTEGER:
                return new BigIntegerParser();

            case BOOLEAN:
                return new BooleanParser();

            case BYTE:
                return new ByteParser();

            case CHAR:
                return new CharParser();

            case DOUBLE:
                return new DoubleParser();

            case FLOAT:
                return new FloatParser();

            case INTEGER:
                return new IntegerParser();

            case LONG:
                return new LongParser();

            case SHORT:
                return new ShortParser();

            case STRING:
            default:
                return new StringParser();
        }
    }

    final public boolean isInstancedBy (final Object o) {
        return type.isInstance(o);
    }


    public boolean isAtomic () {
        return (
               this == ATOMIC_BOOLEAN
            || this == ATOMIC_INTEGER
            || this == ATOMIC_LONG
        );
    }


    public boolean isBoolean () {
        return (
               this == BOOLEAN
            || this == ATOMIC_BOOLEAN
        );
    }


    public boolean isFloat () {
        return (
               this == DOUBLE
            || this == FLOAT
            || this == BIG_DECIMAL
        );
    }


    public boolean isInt () {
        return (
               this == INTEGER
            || this == BYTE
            || this == SHORT
            || this == LONG
            || this == BIG_INTEGER
            || this == ATOMIC_INTEGER
            || this == ATOMIC_LONG
        );
    }


    public boolean isNumber () {
        return ( isInt() || isFloat() );
    }


    public boolean isString () {
        return (
               this == STRING
            || this == CHAR
        );
    }
}
