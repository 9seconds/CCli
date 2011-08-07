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

import static org.junit.Assert.*;

import org.aerialsounds.ccli.OptionTypes;
import org.aerialsounds.ccli.ValueTypes;
import org.aerialsounds.ccli.datacontainer.DataContainer;
import org.aerialsounds.ccli.options.AbstractOption.DataIsNotValid;
import org.junit.Before;
import org.junit.Test;




public class NumericalOptionTests {

    private DataContainer container;

    @Before
    public void setUp() {
        container = new DataContainer(null);
        container.setValueType(ValueTypes.BOOLEAN);
    }

    @Test(expected = DataIsNotValid.class)
    public void createWithWrongTypeLong() {
        @SuppressWarnings ("unused")
        NumericalOption opt = new NumericalOption(OptionTypes.LONG, "9", container);
    }

    @Test(expected = DataIsNotValid.class)
    public void createWithWrongTypeCustom() {
        @SuppressWarnings ("unused")
        NumericalOption opt = new NumericalOption(OptionTypes.CUSTOM, "9", container);
    }

    @Test(expected = DataIsNotValid.class)
    public void createWithWrongValueType() {
        container.setValueType(ValueTypes.STRING);
        @SuppressWarnings ("unused")
        NumericalOption opt = new NumericalOption(OptionTypes.SHORT, "9", container);
    }

    @Test
    public void appropriate() {
        NumericalOption opt = new NumericalOption(OptionTypes.SHORT, "9", container);

        assertTrue(opt.appropriate("-9"));

        assertFalse(opt.appropriate("9"));
        assertFalse(opt.appropriate("--9"));
        assertFalse(opt.appropriate("-f9"));
        assertFalse(opt.appropriate("-9f"));
        assertFalse(opt.appropriate("-9d"));
        assertFalse(opt.appropriate("-fdsfdf"));
        assertFalse(opt.appropriate("fdsffdx"));
    }

    @Test
    public void haveInlineValue() {
        NumericalOption opt = new NumericalOption(OptionTypes.SHORT, "9", container);
        assertTrue(opt.haveInlineValue("-9"));

        assertFalse(opt.haveInlineValue("-999"));
        assertFalse(opt.haveInlineValue("999"));
        assertFalse(opt.haveInlineValue("f999"));
        assertFalse(opt.haveInlineValue("9f99"));
        assertFalse(opt.haveInlineValue("999f"));
        assertFalse(opt.haveInlineValue("q999"));
        assertFalse(opt.haveInlineValue("999q"));
        assertFalse(opt.haveInlineValue("--999"));
    }

    @Test
    public void extractInlineValue() {
        NumericalOption opt = new NumericalOption(OptionTypes.SHORT, "9", container);
        assertEquals("true", opt.extractInlineValue("-9"));
    }

    @Test
    public void getInlineValue() {
        NumericalOption opt = new NumericalOption(OptionTypes.SHORT, "9", container);

        assertEquals("true", opt.getInlineValue("-9"));

        assertNull("true", opt.getInlineValue("-99"));
        assertNull("true", opt.getInlineValue("-99f"));
        assertNull("true", opt.getInlineValue("faqa"));
    }

}
