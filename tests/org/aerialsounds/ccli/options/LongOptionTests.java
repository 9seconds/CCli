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



public class LongOptionTests {


    private DataContainer container;


    @Before
    public void setUp () {
        container = new DataContainer(new CCli(null));
        container.setValueType(ValueTypes.BOOLEAN);
        container.setDefaultValue(false);
        container.setHelp("");
    }


    @Test
    public void appropriate () {
        LongOption l = new LongOption(OptionTypes.LONG, "vbr-new", container);

        assertTrue(l.appropriate("--vbr-new"));
        assertTrue(l.appropriate("--vbr-new="));
        assertTrue(l.appropriate("--vbr-new=1"));
        assertTrue(l.appropriate("--vbr-new=TRUE"));
        assertTrue(l.appropriate("--vbr-new=1.2"));
        assertTrue(l.appropriate("--vbr-new=.2"));
        assertTrue(l.appropriate("--vbr-new=1."));
        assertTrue(l.appropriate("--vbr-new=1.2ef"));

        assertFalse(l.appropriate("--vbr-ne"));
        assertFalse(l.appropriate("--vbr-ne=w"));
        assertFalse(l.appropriate("--vbr-new1"));
        assertFalse(l.appropriate("--vbr-newTRUE"));
        assertFalse(l.appropriate("--vbr-new1.2"));
        assertFalse(l.appropriate("--vbr-new.2"));
        assertFalse(l.appropriate("--vbr-new1."));
        assertFalse(l.appropriate("--vbr-new1.2ef"));
    }


    @Test (expected = DataIsNotValid.class)
    public void createWithWrongTypeShort () {
        @SuppressWarnings ("unused") LongOption l = new LongOption(OptionTypes.SHORT, "vbr-new", container);
    }


    @Test
    public void extractInlineValue () {
        LongOption l = new LongOption(OptionTypes.LONG, "vbr-new", container);

        assertNull(l.getInlineValue("--vbr-new"));

        assertEquals("", l.getInlineValue("--vbr-new="));
        assertEquals("1", l.getInlineValue("--vbr-new=1"));
        assertEquals("TRUE", l.getInlineValue("--vbr-new=TRUE"));
        assertEquals("1.2", l.getInlineValue("--vbr-new=1.2"));
        assertEquals(".2", l.getInlineValue("--vbr-new=.2"));
        assertEquals("1.", l.getInlineValue("--vbr-new=1."));
        assertEquals("1.2ef", l.getInlineValue("--vbr-new=1.2ef"));
    }


    @Test
    public void haveInlineValue () {
        LongOption l = new LongOption(OptionTypes.LONG, "vbr-new", container);

        assertFalse(l.haveInlineValue("--vbr-new"));

        assertTrue(l.haveInlineValue("--vbr-new="));
        assertTrue(l.haveInlineValue("--vbr-new=1"));
        assertTrue(l.haveInlineValue("--vbr-new=TRUE"));
        assertTrue(l.haveInlineValue("--vbr-new=1.2"));
        assertTrue(l.haveInlineValue("--vbr-new=.2"));
        assertTrue(l.haveInlineValue("--vbr-new=1."));
        assertTrue(l.haveInlineValue("--vbr-new=1.2ef"));
    }

}
