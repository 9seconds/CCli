package org.aerialsounds.ccli;

import static org.junit.Assert.*;

import static org.aerialsounds.ccli.ValueTypes.*;

import org.junit.Test;



public class ValueTypesTests {

    @Test
    public void atomicBoolean() {
        assertTrue(ATOMIC_BOOLEAN.isBoolean());
        assertFalse(ATOMIC_BOOLEAN.isFloat());
        assertFalse(ATOMIC_BOOLEAN.isInt());
        assertFalse(ATOMIC_BOOLEAN.isNumber());
        assertFalse(ATOMIC_BOOLEAN.isString());
        assertTrue(ATOMIC_BOOLEAN.isAtomic());
    }

    @Test
    public void atomicInteger() {
        assertFalse(ATOMIC_INTEGER.isBoolean());
        assertFalse(ATOMIC_INTEGER.isFloat());
        assertTrue(ATOMIC_INTEGER.isInt());
        assertTrue(ATOMIC_INTEGER.isNumber());
        assertFalse(ATOMIC_INTEGER.isString());
        assertTrue(ATOMIC_INTEGER.isAtomic());
    }

    @Test
    public void atomicLong() {
        assertFalse(ATOMIC_LONG.isBoolean());
        assertFalse(ATOMIC_LONG.isFloat());
        assertTrue(ATOMIC_LONG.isInt());
        assertTrue(ATOMIC_LONG.isNumber());
        assertFalse(ATOMIC_LONG.isString());
        assertTrue(ATOMIC_LONG.isAtomic());
    }

    @Test
    public void bigDecimal() {
        assertFalse(BIG_DECIMAL.isBoolean());
        assertTrue(BIG_DECIMAL.isFloat());
        assertFalse(BIG_DECIMAL.isInt());
        assertTrue(BIG_DECIMAL.isNumber());
        assertFalse(BIG_DECIMAL.isString());
        assertFalse(BIG_DECIMAL.isAtomic());
    }

    @Test
    public void bigInteger() {
        assertFalse(BIG_INTEGER.isBoolean());
        assertFalse(BIG_INTEGER.isFloat());
        assertTrue(BIG_INTEGER.isInt());
        assertTrue(BIG_INTEGER.isNumber());
        assertFalse(BIG_INTEGER.isString());
        assertFalse(BIG_INTEGER.isAtomic());
    }

    @Test
    public void boolean_() {
        assertTrue(BOOLEAN.isBoolean());
        assertFalse(BOOLEAN.isFloat());
        assertFalse(BOOLEAN.isInt());
        assertFalse(BOOLEAN.isNumber());
        assertFalse(BOOLEAN.isString());
        assertFalse(BOOLEAN.isAtomic());
    }

    @Test
    public void byte_() {
        assertFalse(BYTE.isBoolean());
        assertFalse(BYTE.isFloat());
        assertTrue(BYTE.isInt());
        assertTrue(BYTE.isNumber());
        assertFalse(BYTE.isString());
        assertFalse(BYTE.isAtomic());
    }

    @Test
    public void char_() {
        assertFalse(CHAR.isBoolean());
        assertFalse(CHAR.isFloat());
        assertFalse(CHAR.isInt());
        assertFalse(CHAR.isNumber());
        assertTrue(CHAR.isString());
        assertFalse(CHAR.isAtomic());
    }

    @Test
    public void double_() {
        assertFalse(DOUBLE.isBoolean());
        assertTrue(DOUBLE.isFloat());
        assertFalse(DOUBLE.isInt());
        assertTrue(DOUBLE.isNumber());
        assertFalse(DOUBLE.isString());
        assertFalse(DOUBLE.isAtomic());
    }

    @Test
    public void float_() {
        assertFalse(FLOAT.isBoolean());
        assertTrue(FLOAT.isFloat());
        assertFalse(FLOAT.isInt());
        assertTrue(FLOAT.isNumber());
        assertFalse(FLOAT.isString());
        assertFalse(FLOAT.isAtomic());
    }

    @Test
    public void integer_() {
        assertFalse(INTEGER.isBoolean());
        assertFalse(INTEGER.isFloat());
        assertTrue(INTEGER.isInt());
        assertTrue(INTEGER.isNumber());
        assertFalse(INTEGER.isString());
        assertFalse(INTEGER.isAtomic());
    }

    @Test
    public void long_() {
        assertFalse(LONG.isBoolean());
        assertFalse(LONG.isFloat());
        assertTrue(LONG.isInt());
        assertTrue(LONG.isNumber());
        assertFalse(LONG.isString());
        assertFalse(LONG.isAtomic());
    }

    @Test
    public void short_() {
        assertFalse(SHORT.isBoolean());
        assertFalse(SHORT.isFloat());
        assertTrue(SHORT.isInt());
        assertTrue(SHORT.isNumber());
        assertFalse(SHORT.isString());
        assertFalse(SHORT.isAtomic());
    }

    @Test
    public void string_() {
        assertFalse(STRING.isBoolean());
        assertFalse(STRING.isFloat());
        assertFalse(STRING.isInt());
        assertFalse(STRING.isNumber());
        assertTrue(STRING.isString());
        assertFalse(STRING.isAtomic());
    }

}
