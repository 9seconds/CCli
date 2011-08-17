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



package org.aerialsounds.ccli.options;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.aerialsounds.ccli.CCli;
import org.aerialsounds.ccli.OptionTypes;
import org.aerialsounds.ccli.ValueTypes;
import org.aerialsounds.ccli.datacontainer.DataContainer;
import org.aerialsounds.ccli.options.AbstractOption.DataIsNotValid;

import org.junit.Before;
import org.junit.Test;



public class ShortOptionTests {


    private DataContainer container;


    @Before
    public void setUp () {
        container = new DataContainer(new CCli(null));
        container.setDefaultValue(10);
        container.setValueType(ValueTypes.INTEGER);
        container.setHelp("");
    }


    @Test
    public void appropriate () {
        ShortOption opt = new ShortOption(OptionTypes.SHORT, "v", container);

        assertTrue(opt.appropriate("-v"));
        assertTrue(opt.appropriate("-v1"));
        assertTrue(opt.appropriate("-v1.0"));
        assertTrue(opt.appropriate("-v1.0e1"));
        assertTrue(opt.appropriate("-v1.0e1f"));
        assertTrue(opt.appropriate("-v1.0e01f"));
        assertTrue(opt.appropriate("-v1.0e+1f"));
        assertTrue(opt.appropriate("-v1.0e-1f"));
        assertTrue(opt.appropriate("-v1.0e1d"));
        assertTrue(opt.appropriate("-v1.e1f"));
        assertTrue(opt.appropriate("-v.1e1f"));
        assertTrue(opt.appropriate("-v.100e+01d"));

        assertFalse(opt.appropriate("-vTRUE"));
        assertFalse(opt.appropriate("-v=TRUE"));
        assertFalse(opt.appropriate("-v=01"));
        assertFalse(opt.appropriate("-v=1"));

        opt.getContainer().setValueType(ValueTypes.BOOLEAN);

        assertTrue(opt.appropriate("-v"));

        assertFalse(opt.appropriate("-v1"));
        assertFalse(opt.appropriate("-v1.0"));
        assertFalse(opt.appropriate("-v1.0e1"));
        assertFalse(opt.appropriate("-v1.0e1f"));
        assertFalse(opt.appropriate("-v1.0e01f"));
        assertFalse(opt.appropriate("-v1.0e+1f"));
        assertFalse(opt.appropriate("-v1.0e-1f"));
        assertFalse(opt.appropriate("-v1.0e1d"));
        assertFalse(opt.appropriate("-v1.e1f"));
        assertFalse(opt.appropriate("-v.1e1f"));
        assertFalse(opt.appropriate("-v.100e+01d"));

        assertFalse(opt.appropriate("-vTRUE"));
        assertFalse(opt.appropriate("-v=TRUE"));
        assertFalse(opt.appropriate("-v=01"));
        assertFalse(opt.appropriate("-v=1"));
    }


    @Test
    public void extractingInlineValues () {
        container.setValueType(ValueTypes.DOUBLE);
        container.setDefaultValue(10.0d);
        ShortOption opt = new ShortOption(OptionTypes.SHORT, "v", container);

        assertNull(opt.extractInlineValue("-v"));

        assertEquals("1", opt.getInlineValue("-v1"));
        assertEquals("1.0", opt.getInlineValue("-v1.0"));
        assertEquals("1.0e1", opt.getInlineValue("-v1.0e1"));
        assertEquals("1.0e1", opt.getInlineValue("-v1.0e1d"));
        assertEquals("1.0e01", opt.getInlineValue("-v1.0e01d"));
        assertEquals("1.0e+1", opt.getInlineValue("-v1.0e+1"));
        assertEquals("1.0e-1", opt.getInlineValue("-v1.0e-1"));
        assertEquals("1.0e1", opt.getInlineValue("-v1.0e1"));
        assertEquals("1.e1", opt.getInlineValue("-v1.e1"));
        assertEquals(".1e+05", opt.getInlineValue("-v.1e+05"));
        assertEquals(".100e+01", opt.getInlineValue("-v.100e+01d"));

        assertNull(opt.getInlineValue("-vTRUE"));
        assertNull(opt.getInlineValue("-v=TRUE"));
        assertNull(opt.getInlineValue("-v=01"));
        assertNull(opt.getInlineValue("-v=1"));

        opt.getContainer().setValueType(ValueTypes.BOOLEAN);

        assertNull(opt.getInlineValue("-v"));

        assertNull(opt.getInlineValue("-v1"));
        assertNull(opt.getInlineValue("-v1.0"));
        assertNull(opt.getInlineValue("-v1.0e1"));
        assertNull(opt.getInlineValue("-v1.0e1f"));
        assertNull(opt.getInlineValue("-v1.0e01f"));
        assertNull(opt.getInlineValue("-v1.0e+1f"));
        assertNull(opt.getInlineValue("-v1.0e-1f"));
        assertNull(opt.getInlineValue("-v1.0e1d"));
        assertNull(opt.getInlineValue("-v1.e1f"));
        assertNull(opt.getInlineValue("-v.1e1f"));
        assertNull(opt.getInlineValue("-v.100e+01d"));

        assertNull(opt.getInlineValue("-vTRUE"));
        assertNull(opt.getInlineValue("-v=TRUE"));
        assertNull(opt.getInlineValue("-v=01"));
        assertNull(opt.getInlineValue("-v=1"));
    }


    @Test
    public void extractInlineValues () {
        ShortOption opt = new ShortOption(OptionTypes.SHORT, "v", container);

        assertEquals("1", opt.extractInlineValue("-v1"));
        assertEquals("10", opt.extractInlineValue("-v10"));
        assertEquals("100", opt.extractInlineValue("-v100"));
        assertEquals("100.", opt.extractInlineValue("-v100."));
        assertEquals("100.1", opt.extractInlineValue("-v100.1"));
        assertEquals("100.123", opt.extractInlineValue("-v100.123"));
        assertEquals("100.123", opt.extractInlineValue("-v100.123f"));
        assertEquals("100.123", opt.extractInlineValue("-v100.123d"));
        assertEquals("100.123e1", opt.extractInlineValue("-v100.123e1"));
        assertEquals("100.123e01", opt.extractInlineValue("-v100.123e01"));
        assertEquals("100.123e+01", opt.extractInlineValue("-v100.123e+01"));
        assertEquals("100.123e-01", opt.extractInlineValue("-v100.123e-01"));
        assertEquals("100.123E5", opt.extractInlineValue("-v100.123E5f"));

        assertEquals(".1", opt.extractInlineValue("-v.1"));
        assertEquals(".10", opt.extractInlineValue("-v.10"));
        assertEquals(".123", opt.extractInlineValue("-v.123"));
        assertEquals(".123", opt.extractInlineValue("-v.123f"));
        assertEquals(".123", opt.extractInlineValue("-v.123d"));
        assertEquals(".123e1", opt.extractInlineValue("-v.123e1"));
        assertEquals(".123e01", opt.extractInlineValue("-v.123e01"));
        assertEquals(".123e+01", opt.extractInlineValue("-v.123e+01"));
        assertEquals(".123e-01", opt.extractInlineValue("-v.123e-01"));
        assertEquals(".123E5", opt.extractInlineValue("-v.123E5f"));

        assertNull(opt.extractInlineValue("-v100.123E5fd"));
        assertNull(opt.extractInlineValue("-v100.123E"));
        assertNull(opt.extractInlineValue("-v100.123E+"));
        assertNull(opt.extractInlineValue("-v100.123E-"));
        assertNull(opt.extractInlineValue("-v."));
    }


    @Test (expected = DataIsNotValid.class)
    public void incorrectOptionNaming () {
        @SuppressWarnings ("unused") ShortOption opt = new ShortOption(OptionTypes.SHORT, "value", container);
    }


    @Test
    public void optionHaveInlineValue () {
        ShortOption opt = new ShortOption(OptionTypes.SHORT, "v", container);

        assertTrue(opt.haveInlineValue("-v1"));
        assertTrue(opt.haveInlineValue("-v10"));
        assertTrue(opt.haveInlineValue("-v100"));
        assertTrue(opt.haveInlineValue("-v100."));
        assertTrue(opt.haveInlineValue("-v100.1"));
        assertTrue(opt.haveInlineValue("-v100.123"));
        assertTrue(opt.haveInlineValue("-v100.123f"));
        assertTrue(opt.haveInlineValue("-v100.123d"));
        assertTrue(opt.haveInlineValue("-v100.123e1"));
        assertTrue(opt.haveInlineValue("-v100.123e01"));
        assertTrue(opt.haveInlineValue("-v100.123e+01"));
        assertTrue(opt.haveInlineValue("-v100.123e-01"));
        assertTrue(opt.haveInlineValue("-v100.123E5f"));

        assertTrue(opt.haveInlineValue("-v.1"));
        assertTrue(opt.haveInlineValue("-v.10"));
        assertTrue(opt.haveInlineValue("-v.123"));
        assertTrue(opt.haveInlineValue("-v.123f"));
        assertTrue(opt.haveInlineValue("-v.123d"));
        assertTrue(opt.haveInlineValue("-v.123e1"));
        assertTrue(opt.haveInlineValue("-v.123e01"));
        assertTrue(opt.haveInlineValue("-v.123e+01"));
        assertTrue(opt.haveInlineValue("-v.123e-01"));
        assertTrue(opt.haveInlineValue("-v.123E5f"));

        assertFalse(opt.haveInlineValue("-v100.123E5fd"));
        assertFalse(opt.haveInlineValue("-v100.123E"));
        assertFalse(opt.haveInlineValue("-v100.123E+"));
        assertFalse(opt.haveInlineValue("-v100.123E-"));
        assertFalse(opt.haveInlineValue("-v."));

        container.setValueType(ValueTypes.BOOLEAN);

        assertFalse(opt.haveInlineValue("-v1"));
        assertFalse(opt.haveInlineValue("-v10"));
        assertFalse(opt.haveInlineValue("-v100"));
        assertFalse(opt.haveInlineValue("-v100."));
        assertFalse(opt.haveInlineValue("-v100.1"));
        assertFalse(opt.haveInlineValue("-v100.123"));
        assertFalse(opt.haveInlineValue("-v100.123f"));
        assertFalse(opt.haveInlineValue("-v100.123d"));
        assertFalse(opt.haveInlineValue("-v100.123e1"));
        assertFalse(opt.haveInlineValue("-v100.123e01"));
        assertFalse(opt.haveInlineValue("-v100.123e+01"));
        assertFalse(opt.haveInlineValue("-v100.123e-01"));
        assertFalse(opt.haveInlineValue("-v100.123E5f"));

        assertFalse(opt.haveInlineValue("-v.1"));
        assertFalse(opt.haveInlineValue("-v.10"));
        assertFalse(opt.haveInlineValue("-v.123"));
        assertFalse(opt.haveInlineValue("-v.123f"));
        assertFalse(opt.haveInlineValue("-v.123d"));
        assertFalse(opt.haveInlineValue("-v.123e1"));
        assertFalse(opt.haveInlineValue("-v.123e01"));
        assertFalse(opt.haveInlineValue("-v.123e+01"));
        assertFalse(opt.haveInlineValue("-v.123e-01"));
        assertFalse(opt.haveInlineValue("-v.123E5f"));

        assertFalse(opt.haveInlineValue("-v100.123E5fd"));
        assertFalse(opt.haveInlineValue("-v100.123E"));
        assertFalse(opt.haveInlineValue("-v100.123E+"));
        assertFalse(opt.haveInlineValue("-v100.123E-"));
        assertFalse(opt.haveInlineValue("-v."));
    }


    @Test
    public void optionHaveNumbers () {
        assertFalse(ShortOption.haveNumbers("-vghi"));
        assertFalse(ShortOption.haveNumbers("-v"));

        assertTrue(ShortOption.haveNumbers("-9"));
        assertTrue(ShortOption.haveNumbers("-99"));
        assertTrue(ShortOption.haveNumbers("-v9"));
        assertTrue(ShortOption.haveNumbers("-v9v"));
        assertTrue(ShortOption.haveNumbers("1"));
        assertTrue(ShortOption.haveNumbers("1v"));
        assertTrue(ShortOption.haveNumbers("v1v"));
        assertTrue(ShortOption.haveNumbers("vv1"));
    }

}
