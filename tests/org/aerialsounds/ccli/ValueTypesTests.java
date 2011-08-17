/*
 * Copyright (C) 2011 by Serge Arkhipov <serge@aerialsounds.org>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
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



import static org.aerialsounds.ccli.ValueTypes.ATOMIC_BOOLEAN;
import static org.aerialsounds.ccli.ValueTypes.ATOMIC_INTEGER;
import static org.aerialsounds.ccli.ValueTypes.ATOMIC_LONG;
import static org.aerialsounds.ccli.ValueTypes.BIG_DECIMAL;
import static org.aerialsounds.ccli.ValueTypes.BIG_INTEGER;
import static org.aerialsounds.ccli.ValueTypes.BOOLEAN;
import static org.aerialsounds.ccli.ValueTypes.BYTE;
import static org.aerialsounds.ccli.ValueTypes.CHAR;
import static org.aerialsounds.ccli.ValueTypes.DOUBLE;
import static org.aerialsounds.ccli.ValueTypes.FLOAT;
import static org.aerialsounds.ccli.ValueTypes.INTEGER;
import static org.aerialsounds.ccli.ValueTypes.LONG;
import static org.aerialsounds.ccli.ValueTypes.SHORT;
import static org.aerialsounds.ccli.ValueTypes.STRING;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;



public class ValueTypesTests {


    @Test
    public void atomicBoolean () {
        assertTrue(ATOMIC_BOOLEAN.isBoolean());
        assertFalse(ATOMIC_BOOLEAN.isFloat());
        assertFalse(ATOMIC_BOOLEAN.isInt());
        assertFalse(ATOMIC_BOOLEAN.isNumber());
        assertFalse(ATOMIC_BOOLEAN.isString());
        assertTrue(ATOMIC_BOOLEAN.isAtomic());
    }


    @Test
    public void atomicInteger () {
        assertFalse(ATOMIC_INTEGER.isBoolean());
        assertFalse(ATOMIC_INTEGER.isFloat());
        assertTrue(ATOMIC_INTEGER.isInt());
        assertTrue(ATOMIC_INTEGER.isNumber());
        assertFalse(ATOMIC_INTEGER.isString());
        assertTrue(ATOMIC_INTEGER.isAtomic());
    }


    @Test
    public void atomicLong () {
        assertFalse(ATOMIC_LONG.isBoolean());
        assertFalse(ATOMIC_LONG.isFloat());
        assertTrue(ATOMIC_LONG.isInt());
        assertTrue(ATOMIC_LONG.isNumber());
        assertFalse(ATOMIC_LONG.isString());
        assertTrue(ATOMIC_LONG.isAtomic());
    }


    @Test
    public void bigDecimal () {
        assertFalse(BIG_DECIMAL.isBoolean());
        assertTrue(BIG_DECIMAL.isFloat());
        assertFalse(BIG_DECIMAL.isInt());
        assertTrue(BIG_DECIMAL.isNumber());
        assertFalse(BIG_DECIMAL.isString());
        assertFalse(BIG_DECIMAL.isAtomic());
    }


    @Test
    public void bigInteger () {
        assertFalse(BIG_INTEGER.isBoolean());
        assertFalse(BIG_INTEGER.isFloat());
        assertTrue(BIG_INTEGER.isInt());
        assertTrue(BIG_INTEGER.isNumber());
        assertFalse(BIG_INTEGER.isString());
        assertFalse(BIG_INTEGER.isAtomic());
    }


    @Test
    public void boolean_ () {
        assertTrue(BOOLEAN.isBoolean());
        assertFalse(BOOLEAN.isFloat());
        assertFalse(BOOLEAN.isInt());
        assertFalse(BOOLEAN.isNumber());
        assertFalse(BOOLEAN.isString());
        assertFalse(BOOLEAN.isAtomic());
    }


    @Test
    public void byte_ () {
        assertFalse(BYTE.isBoolean());
        assertFalse(BYTE.isFloat());
        assertTrue(BYTE.isInt());
        assertTrue(BYTE.isNumber());
        assertFalse(BYTE.isString());
        assertFalse(BYTE.isAtomic());
    }


    @Test
    public void char_ () {
        assertFalse(CHAR.isBoolean());
        assertFalse(CHAR.isFloat());
        assertFalse(CHAR.isInt());
        assertFalse(CHAR.isNumber());
        assertTrue(CHAR.isString());
        assertFalse(CHAR.isAtomic());
    }


    @Test
    public void double_ () {
        assertFalse(DOUBLE.isBoolean());
        assertTrue(DOUBLE.isFloat());
        assertFalse(DOUBLE.isInt());
        assertTrue(DOUBLE.isNumber());
        assertFalse(DOUBLE.isString());
        assertFalse(DOUBLE.isAtomic());
    }


    @Test
    public void float_ () {
        assertFalse(FLOAT.isBoolean());
        assertTrue(FLOAT.isFloat());
        assertFalse(FLOAT.isInt());
        assertTrue(FLOAT.isNumber());
        assertFalse(FLOAT.isString());
        assertFalse(FLOAT.isAtomic());
    }


    @Test
    public void integer_ () {
        assertFalse(INTEGER.isBoolean());
        assertFalse(INTEGER.isFloat());
        assertTrue(INTEGER.isInt());
        assertTrue(INTEGER.isNumber());
        assertFalse(INTEGER.isString());
        assertFalse(INTEGER.isAtomic());
    }


    @Test
    public void long_ () {
        assertFalse(LONG.isBoolean());
        assertFalse(LONG.isFloat());
        assertTrue(LONG.isInt());
        assertTrue(LONG.isNumber());
        assertFalse(LONG.isString());
        assertFalse(LONG.isAtomic());
    }


    @Test
    public void short_ () {
        assertFalse(SHORT.isBoolean());
        assertFalse(SHORT.isFloat());
        assertTrue(SHORT.isInt());
        assertTrue(SHORT.isNumber());
        assertFalse(SHORT.isString());
        assertFalse(SHORT.isAtomic());
    }


    @Test
    public void string_ () {
        assertFalse(STRING.isBoolean());
        assertFalse(STRING.isFloat());
        assertFalse(STRING.isInt());
        assertFalse(STRING.isNumber());
        assertTrue(STRING.isString());
        assertFalse(STRING.isAtomic());
    }

}
