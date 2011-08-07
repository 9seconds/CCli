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

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.aerialsounds.ccli.ValueTypes;
import org.junit.BeforeClass;
import org.junit.Test;


public class ValueParserTests {

    private static final String BOOLEAN_TRUE = "true";
    private static final String BIG_INT      = "1046943479208775920458790207774013104";
    private static final String NAN          = "sdflsdklfsldfnpppsdfns;[sdfn";

    static private ValueParser parserAtomicBoolean;
    static private ValueParser parserAtomicInteger;
    static private ValueParser parserAtomicLong;
    static private ValueParser parserBigDecimal;
    static private ValueParser parserBigInteger;
    static private ValueParser parserBoolean;
    static private ValueParser parserByte;
    static private ValueParser parserChar;
    static private ValueParser parserDouble;
    static private ValueParser parserFloat;
    static private ValueParser parserInteger;
    static private ValueParser parserLong;
    static private ValueParser parserShort;
    static private ValueParser parserString;


    @BeforeClass
    static public void setUp () throws Exception {
        parserAtomicBoolean = ValueTypes.ATOMIC_BOOLEAN.createParser();
        parserAtomicInteger = ValueTypes.ATOMIC_INTEGER.createParser();
        parserAtomicLong    = ValueTypes.ATOMIC_LONG.createParser();
        parserBigDecimal    = ValueTypes.BIG_DECIMAL.createParser();
        parserBigInteger    = ValueTypes.BIG_INTEGER.createParser();
        parserBoolean       = ValueTypes.BOOLEAN.createParser();
        parserByte          = ValueTypes.BYTE.createParser();
        parserChar          = ValueTypes.CHAR.createParser();
        parserDouble        = ValueTypes.DOUBLE.createParser();
        parserFloat         = ValueTypes.FLOAT.createParser();
        parserInteger       = ValueTypes.INTEGER.createParser();
        parserLong          = ValueTypes.LONG.createParser();
        parserShort         = ValueTypes.SHORT.createParser();
        parserString        = ValueTypes.STRING.createParser();
    }


    @Test
    public void testImplementInterface () {
        assertTrue(parserAtomicBoolean instanceof ValueParser);
        assertTrue(parserAtomicInteger instanceof ValueParser);
        assertTrue(parserAtomicLong instanceof ValueParser);
        assertTrue(parserBigDecimal instanceof ValueParser);
        assertTrue(parserBigInteger instanceof ValueParser);
        assertTrue(parserBoolean instanceof ValueParser);
        assertTrue(parserByte instanceof ValueParser);
        assertTrue(parserChar instanceof ValueParser);
        assertTrue(parserDouble instanceof ValueParser);
        assertTrue(parserFloat instanceof ValueParser);
        assertTrue(parserInteger instanceof ValueParser);
        assertTrue(parserLong instanceof ValueParser);
        assertTrue(parserShort instanceof ValueParser);
        assertTrue(parserString instanceof ValueParser);
    }

    @Test
    public void testAtomicBoolean () {
        assertNull(parserAtomicBoolean.parse(null));
        assertNull(parserAtomicBoolean.parse(""));
        assertNull(parserAtomicBoolean.parse(NAN));

        assertTrue(parserAtomicBoolean.parse("false") instanceof AtomicBoolean);
        assertTrue(parserAtomicBoolean.parse("n") instanceof AtomicBoolean);
        assertTrue(parserAtomicBoolean.parse("no") instanceof AtomicBoolean);
        assertTrue(parserAtomicBoolean.parse(BOOLEAN_TRUE) instanceof AtomicBoolean);
        assertTrue(parserAtomicBoolean.parse("y") instanceof AtomicBoolean);
        assertTrue(parserAtomicBoolean.parse("yes") instanceof AtomicBoolean);

        assertTrue(parserAtomicBoolean.parse("False") instanceof AtomicBoolean);
        assertTrue(parserAtomicBoolean.parse("N") instanceof AtomicBoolean);
        assertTrue(parserAtomicBoolean.parse("No") instanceof AtomicBoolean);
        assertTrue(parserAtomicBoolean.parse("True") instanceof AtomicBoolean);
        assertTrue(parserAtomicBoolean.parse("Y") instanceof AtomicBoolean);
        assertTrue(parserAtomicBoolean.parse("yes") instanceof AtomicBoolean);

        assertTrue(parserAtomicBoolean.parse("FALSE") instanceof AtomicBoolean);
        assertTrue(parserAtomicBoolean.parse("TRUE") instanceof AtomicBoolean);
        assertTrue(parserAtomicBoolean.parse("NO") instanceof AtomicBoolean);
        assertTrue(parserAtomicBoolean.parse("YES") instanceof AtomicBoolean);

        assertTrue(parserAtomicBoolean.parse("0") instanceof AtomicBoolean);
        assertTrue(parserAtomicBoolean.parse("1") instanceof AtomicBoolean);

        assertFalse(((AtomicBoolean) parserAtomicBoolean.parse("false")).get());
        assertFalse(((AtomicBoolean) parserAtomicBoolean.parse("n")).get());
        assertFalse(((AtomicBoolean) parserAtomicBoolean.parse("no")).get());
        assertFalse(((AtomicBoolean) parserAtomicBoolean.parse("False")).get());
        assertFalse(((AtomicBoolean) parserAtomicBoolean.parse("FALSE")).get());
        assertFalse(((AtomicBoolean) parserAtomicBoolean.parse("N")).get());
        assertFalse(((AtomicBoolean) parserAtomicBoolean.parse("NO")).get());
        assertFalse(((AtomicBoolean) parserAtomicBoolean.parse("No")).get());
        assertFalse(((AtomicBoolean) parserAtomicBoolean.parse("0")).get());

        assertTrue(((AtomicBoolean) parserAtomicBoolean.parse(BOOLEAN_TRUE)).get());
        assertTrue(((AtomicBoolean) parserAtomicBoolean.parse("y")).get());
        assertTrue(((AtomicBoolean) parserAtomicBoolean.parse("yes")).get());
        assertTrue(((AtomicBoolean) parserAtomicBoolean.parse("True")).get());
        assertTrue(((AtomicBoolean) parserAtomicBoolean.parse("TRUE")).get());
        assertTrue(((AtomicBoolean) parserAtomicBoolean.parse("Y")).get());
        assertTrue(((AtomicBoolean) parserAtomicBoolean.parse("Yes")).get());
        assertTrue(((AtomicBoolean) parserAtomicBoolean.parse("YES")).get());
        assertTrue(((AtomicBoolean) parserAtomicBoolean.parse("1")).get());

        assertTrue(((AtomicBoolean) parserAtomicBoolean.parse(Boolean.TRUE.toString())).get());
        assertFalse(((AtomicBoolean) parserAtomicBoolean.parse(Boolean.FALSE.toString())).get());
        assertTrue(((AtomicBoolean) parserAtomicBoolean.parse(String.valueOf(BooleanConverter.NUMERICAL_SIGN_TRUE))).get());
    }

    @Test
    public void testAtomicInteger () {
        assertNull(parserAtomicInteger.parse(null));
        assertNull(parserAtomicInteger.parse(""));
        assertNull(parserAtomicInteger.parse(NAN));
        assertNull(parserAtomicInteger.parse(BOOLEAN_TRUE));

        assertTrue(parserAtomicInteger.parse("0") instanceof AtomicInteger);
        assertTrue(parserAtomicInteger.parse("1") instanceof AtomicInteger);
        assertTrue(parserAtomicInteger.parse("-1") instanceof AtomicInteger);

        assertEquals(0, ((AtomicInteger) parserAtomicInteger.parse("0")).get());
        assertEquals(1, ((AtomicInteger) parserAtomicInteger.parse("1")).get());
        assertEquals(-1, ((AtomicInteger) parserAtomicInteger.parse("-1")).get());
    }

    @Test
    public void testAtomicLong () {
        assertNull(parserAtomicLong.parse(null));
        assertNull(parserAtomicLong.parse(""));
        assertNull(parserAtomicLong.parse(NAN));
        assertNull(parserAtomicLong.parse(BOOLEAN_TRUE));

        assertTrue(parserAtomicLong.parse("0") instanceof AtomicLong);
        assertTrue(parserAtomicLong.parse("1") instanceof AtomicLong);
        assertTrue(parserAtomicLong.parse("-1") instanceof AtomicLong);

        assertEquals(0L, ((AtomicLong) parserAtomicLong.parse("0")).get());
        assertEquals(1L, ((AtomicLong) parserAtomicLong.parse("1")).get());
        assertEquals(-1L, ((AtomicLong) parserAtomicLong.parse("-1")).get());
    }

    @Test
    public void testBigDecimal () {
        assertNull(parserBigDecimal.parse(null));
        assertNull(parserBigDecimal.parse(""));
        assertNull(parserBigDecimal.parse(NAN));
        assertNull(parserBigDecimal.parse(BOOLEAN_TRUE));

        assertTrue(parserBigDecimal.parse("0") instanceof BigDecimal);
        assertTrue(parserBigDecimal.parse("1") instanceof BigDecimal);
        assertTrue(parserBigDecimal.parse("-1") instanceof BigDecimal);
        assertTrue(parserBigDecimal.parse("1.0") instanceof BigDecimal);
        assertTrue(parserBigDecimal.parse("-1.0") instanceof BigDecimal);
        assertTrue(parserBigDecimal.parse(".1") instanceof BigDecimal);
        assertTrue(parserBigDecimal.parse("1.2e-05") instanceof BigDecimal);

        assertEquals(BigDecimal.valueOf(0), parserBigDecimal.parse("0"));
        assertEquals(BigDecimal.valueOf(1), parserBigDecimal.parse("1"));
        assertEquals(BigDecimal.valueOf(-1), parserBigDecimal.parse("-1"));
        assertEquals(BigDecimal.valueOf(1.0), parserBigDecimal.parse("1.0"));
        assertEquals(BigDecimal.valueOf(-1.0), parserBigDecimal.parse("-1.0"));
        assertEquals(BigDecimal.valueOf(1.2e-05), parserBigDecimal.parse("1.2e-05"));
    }

    @Test
    public void testBigInteger () {
        assertNull(parserBigInteger.parse(null));
        assertNull(parserBigInteger.parse(""));
        assertNull(parserBigInteger.parse(NAN));
        assertNull(parserBigInteger.parse(BOOLEAN_TRUE));

        assertTrue(parserBigInteger.parse("0") instanceof BigInteger);
        assertTrue(parserBigInteger.parse("1") instanceof BigInteger);
        assertTrue(parserBigInteger.parse("-1") instanceof BigInteger);
        assertTrue(parserBigInteger.parse(BIG_INT) instanceof BigInteger);

        assertEquals(BigInteger.ZERO, parserBigInteger.parse("0"));
        assertEquals(BigInteger.ONE, parserBigInteger.parse("1"));
        assertEquals(-1, ((BigInteger) parserBigInteger.parse("-1")).intValue());
        assertEquals(new BigInteger(BIG_INT), parserBigInteger.parse(BIG_INT));
    }

    @Test
    public void testBoolean () {
        assertNull(parserAtomicBoolean.parse(null));
        assertNull(parserBoolean.parse(""));
        assertNull(parserBoolean.parse(NAN));

        assertTrue(parserBoolean.parse("false") instanceof Boolean);
        assertTrue(parserBoolean.parse("n") instanceof Boolean);
        assertTrue(parserBoolean.parse("no") instanceof Boolean);
        assertTrue(parserBoolean.parse(BOOLEAN_TRUE) instanceof Boolean);
        assertTrue(parserBoolean.parse("y") instanceof Boolean);
        assertTrue(parserBoolean.parse("yes") instanceof Boolean);

        assertTrue(parserBoolean.parse("False") instanceof Boolean);
        assertTrue(parserBoolean.parse("N") instanceof Boolean);
        assertTrue(parserBoolean.parse("No") instanceof Boolean);
        assertTrue(parserBoolean.parse("True") instanceof Boolean);
        assertTrue(parserBoolean.parse("Y") instanceof Boolean);
        assertTrue(parserBoolean.parse("yes") instanceof Boolean);

        assertTrue(parserBoolean.parse("FALSE") instanceof Boolean);
        assertTrue(parserBoolean.parse("TRUE") instanceof Boolean);
        assertTrue(parserBoolean.parse("NO") instanceof Boolean);
        assertTrue(parserBoolean.parse("YES") instanceof Boolean);

        assertTrue(parserBoolean.parse("0") instanceof Boolean);
        assertTrue(parserBoolean.parse("1") instanceof Boolean);

        assertFalse((Boolean) parserBoolean.parse("false"));
        assertFalse((Boolean) parserBoolean.parse("n"));
        assertFalse((Boolean) parserBoolean.parse("no"));
        assertFalse((Boolean) parserBoolean.parse("False"));
        assertFalse((Boolean) parserBoolean.parse("No"));
        assertFalse((Boolean) parserBoolean.parse("FALSE"));
        assertFalse((Boolean) parserBoolean.parse("NO"));
        assertFalse((Boolean) parserBoolean.parse("N"));
        assertFalse((Boolean) parserBoolean.parse("0"));

        assertTrue((Boolean) parserBoolean.parse(BOOLEAN_TRUE));
        assertTrue((Boolean) parserBoolean.parse("y"));
        assertTrue((Boolean) parserBoolean.parse("yes"));
        assertTrue((Boolean) parserBoolean.parse("True"));
        assertTrue((Boolean) parserBoolean.parse("Yes"));
        assertTrue((Boolean) parserBoolean.parse("TRUE"));
        assertTrue((Boolean) parserBoolean.parse("YES"));
        assertTrue((Boolean) parserBoolean.parse("Y"));
        assertTrue((Boolean) parserBoolean.parse("1"));

        assertTrue((Boolean) parserBoolean.parse(Boolean.TRUE.toString()));
        assertFalse((Boolean) parserBoolean.parse(Boolean.FALSE.toString()));
        assertTrue((Boolean) parserBoolean.parse(String.valueOf(BooleanConverter.NUMERICAL_SIGN_TRUE)));
    }

    @Test
    public void testByte () {
        assertNull(parserByte.parse(null));
        assertNull(parserByte.parse(""));
        assertNull(parserByte.parse(NAN));
        assertNull(parserByte.parse("1000"));
        assertNull(parserByte.parse(BOOLEAN_TRUE));

        assertTrue(parserByte.parse("0") instanceof Byte);
        assertTrue(parserByte.parse("1") instanceof Byte);
        assertTrue(parserByte.parse("-1") instanceof Byte);

        assertEquals((byte) 0, parserByte.parse("0"));
        assertEquals((byte) 1, parserByte.parse("1"));
        assertEquals((byte) -1, parserByte.parse("-1"));
    }

    @Test
    public void testChar () {
        assertNull(parserChar.parse(null));
        assertNull(parserChar.parse(""));
        assertNull(parserChar.parse(NAN));
        assertNull(parserChar.parse("1000"));
        assertNull(parserChar.parse("ab"));
        assertNull(parserChar.parse(BOOLEAN_TRUE));

        assertTrue(parserChar.parse("0") instanceof Character);
        assertTrue(parserChar.parse("1") instanceof Character);
        assertTrue(parserChar.parse("a") instanceof Character);
        assertTrue(parserChar.parse("*") instanceof Character);

        assertEquals('0', parserChar.parse("0"));
        assertEquals('1', parserChar.parse("1"));
        assertEquals('a', parserChar.parse("a"));
        assertEquals('*', parserChar.parse("*"));
    }

    @Test
    public void testDouble () {
        assertNull(parserDouble.parse(null));
        assertNull(parserDouble.parse(""));
        assertNull(parserDouble.parse(NAN));
        assertNull(parserDouble.parse("1.0E"));
        assertNull(parserDouble.parse(BOOLEAN_TRUE));

        assertTrue(parserDouble.parse("0") instanceof Double);
        assertTrue(parserDouble.parse("1") instanceof Double);
        assertTrue(parserDouble.parse("1.0") instanceof Double);
        assertTrue(parserDouble.parse("-1.0") instanceof Double);
        assertTrue(parserDouble.parse("-1.033E0") instanceof Double);
        assertTrue(parserDouble.parse("-1.033e01") instanceof Double);
        assertTrue(parserDouble.parse("-1.033E-02") instanceof Double);
        assertTrue(parserDouble.parse("-1.033E+03") instanceof Double);

        assertEquals(0.0, parserDouble.parse("0"));
        assertEquals(1.0, parserDouble.parse("1"));
        assertEquals(1.0, parserDouble.parse("1.0"));
        assertEquals(-1.0, parserDouble.parse("-1.0"));
        assertEquals(-1.033E0, parserDouble.parse("-1.033E0"));
        assertEquals(-1.033e01, parserDouble.parse("-1.033e01"));
        assertEquals(-1.033E-02, parserDouble.parse("-1.033E-02"));
        assertEquals(-1.033E+03, parserDouble.parse("-1.033E+03"));
    }

    @Test
    public void testFloat () {
        assertNull(parserFloat.parse(null));
        assertNull(parserFloat.parse(""));
        assertNull(parserFloat.parse(NAN));
        assertNull(parserFloat.parse("1.0E"));
        assertNull(parserFloat.parse(BOOLEAN_TRUE));

        assertTrue(parserFloat.parse("0") instanceof Float);
        assertTrue(parserFloat.parse("1") instanceof Float);
        assertTrue(parserFloat.parse("1.0") instanceof Float);
        assertTrue(parserFloat.parse("-1.0") instanceof Float);
        assertTrue(parserFloat.parse("-1.033E0") instanceof Float);
        assertTrue(parserFloat.parse("-1.033e01") instanceof Float);
        assertTrue(parserFloat.parse("-1.033E-02") instanceof Float);
        assertTrue(parserFloat.parse("-1.033E+03f") instanceof Float);

        assertEquals(0.0f, parserFloat.parse("0"));
        assertEquals(1.0f, parserFloat.parse("1"));
        assertEquals(1.0f, parserFloat.parse("1.0"));
        assertEquals(-1.0f, parserFloat.parse("-1.0"));
        assertEquals(-1.033E0f, parserFloat.parse("-1.033E0"));
        assertEquals(-1.033e01f, parserFloat.parse("-1.033e01"));
        assertEquals(-1.033E-02f, parserFloat.parse("-1.033E-02"));
        assertEquals(-1.033E+03f, parserFloat.parse("-1.033E+03F"));
    }

    @Test
    public void testInteger () {
        assertNull(parserInteger.parse(null));
        assertNull(parserInteger.parse(""));
        assertNull(parserInteger.parse(NAN));
        assertNull(parserInteger.parse("1.0"));
        assertNull(parserInteger.parse(BIG_INT));
        assertNull(parserInteger.parse(BOOLEAN_TRUE));

        assertTrue(parserInteger.parse("0") instanceof Integer);
        assertTrue(parserInteger.parse("1") instanceof Integer);
        assertTrue(parserInteger.parse("-1") instanceof Integer);

        assertEquals(0, parserInteger.parse("0"));
        assertEquals(1, parserInteger.parse("1"));
        assertEquals(-1, parserInteger.parse("-1"));
    }

    @Test
    public void testLong () {
        assertNull(parserLong.parse(null));
        assertNull(parserLong.parse(""));
        assertNull(parserLong.parse(NAN));
        assertNull(parserLong.parse("1.0"));
        assertNull(parserLong.parse(BIG_INT));
        assertNull(parserLong.parse(BOOLEAN_TRUE));

        assertTrue(parserLong.parse("0") instanceof Long);
        assertTrue(parserLong.parse("1") instanceof Long);
        assertTrue(parserLong.parse("-1") instanceof Long);

        assertEquals(0L, parserLong.parse("0"));
        assertEquals(1L, parserLong.parse("1"));
        assertEquals(-1L, parserLong.parse("-1"));
    }

    @Test
    public void testShort () {
        assertNull(parserShort.parse(null));
        assertNull(parserShort.parse(""));
        assertNull(parserShort.parse(NAN));
        assertNull(parserShort.parse("1.0"));
        assertNull(parserShort.parse(BIG_INT));
        assertNull(parserShort.parse(BOOLEAN_TRUE));
        assertNull(parserShort.parse("1000000"));

        assertTrue(parserShort.parse("0") instanceof Short);
        assertTrue(parserShort.parse("1") instanceof Short);
        assertTrue(parserShort.parse("-1") instanceof Short);

        assertEquals((short) 0, parserShort.parse("0"));
        assertEquals((short) 1, parserShort.parse("1"));
        assertEquals((short) -1, parserShort.parse("-1"));
    }

    @Test
    public void testString () {
        assertNull(parserString.parse(null));
        assertNull(parserString.parse(""));

        assertTrue(parserString.parse("0") instanceof String);
        assertTrue(parserString.parse(BIG_INT) instanceof String);
        assertTrue(parserString.parse(BOOLEAN_TRUE) instanceof String);

        assertEquals(BOOLEAN_TRUE, parserString.parse(BOOLEAN_TRUE));
        assertEquals(BIG_INT, parserString.parse(BIG_INT));
        assertEquals("0", parserString.parse("0"));
        assertEquals("1.2e-05", parserString.parse("1.2e-05"));
    }

}
