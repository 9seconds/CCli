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



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.atomic.AtomicBoolean;

import org.aerialsounds.ccli.datacontainer.DataContainer;
import org.aerialsounds.ccli.options.LongOption;
import org.aerialsounds.ccli.options.NumericalOption;
import org.aerialsounds.ccli.options.ParseableOption;
import org.aerialsounds.ccli.options.ShortOption;
import org.aerialsounds.ccli.options.AbstractOption.DataIsNotValid;

import org.junit.Before;
import org.junit.Test;



public class CliFactoryTests {


    private CliFactory    factory;
    private DataContainer container;


    @Before
    public void setUp () {
        factory = new CliFactory(new CCli(null));
        container = factory.createDataContainer(10, ValueTypes.INTEGER, "Just dumb test value");
    }


    @Test
    public void createCustomOption () {
        ParseableOption o = factory.createOption(OptionTypes.CUSTOM, "-DINLINE", container);
        assertTrue(o instanceof LongOption);
    }


    @Test (expected = DataIsNotValid.class)
    public void createCustomOptionWithFail () {
        @SuppressWarnings ("unused") ParseableOption o = factory.createOption(
            OptionTypes.CUSTOM,
            "-DINLINE=",
            container);
    }


    @Test
    public void createDataContainers () {
        assertTrue(container.isConsistent());
        assertEquals(10, container.getDefaultValue());
        assertEquals(ValueTypes.INTEGER, container.getValueType());
        assertEquals("Just dumb test value", container.getHelp());
        assertFalse(container.isDefined());
        assertNull(container.getValue());
    }


    @Test
    public void createLongOption () {
        ParseableOption o = factory.createOption(OptionTypes.LONG, "vbr-new", container);
        assertTrue(o instanceof LongOption);
    }


    @Test (expected = DataIsNotValid.class)
    public void createLongOptionWithFail () {
        @SuppressWarnings ("unused") ParseableOption o = factory.createOption(OptionTypes.LONG, "vbr-n=ew", container);
    }


    @Test
    public void createNumericalOption () {
        container.setValueType(ValueTypes.ATOMIC_BOOLEAN);
        container.setDefaultValue(new AtomicBoolean(false));
        ParseableOption o = factory.createOption(OptionTypes.SHORT, "99", container);
        assertTrue(o instanceof NumericalOption);
    }


    @Test (expected = DataIsNotValid.class)
    public void createNumericalOptionWithFail () {
        @SuppressWarnings ("unused") ParseableOption o = factory.createOption(OptionTypes.SHORT, "99", container);
    }


    @Test
    public void createShortOption () {
        ParseableOption o = factory.createOption(OptionTypes.SHORT, "v", container);
        assertTrue(o instanceof ShortOption);
    }


    @Test (expected = DataIsNotValid.class)
    public void createShortOptionWithFail () {
        @SuppressWarnings ("unused") ParseableOption o = factory.createOption(OptionTypes.SHORT, "vvv", container);
    }

}
