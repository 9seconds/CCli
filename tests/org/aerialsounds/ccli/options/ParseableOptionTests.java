/*
 * Copyright (C) 2011 by Sergey Arkhipov <serge@aerialsounds.org>
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



import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.aerialsounds.ccli.CCli;
import org.aerialsounds.ccli.OptionTypes;
import org.aerialsounds.ccli.ValueTypes;
import org.aerialsounds.ccli.datacontainer.DataContainer;
import org.aerialsounds.ccli.options.AbstractOption.DataIsNotValid;

import org.junit.Before;
import org.junit.Test;



public class ParseableOptionTests {


    class ParseableOptionStub
        extends ParseableOption {

        public ParseableOptionStub (final OptionTypes optionType, final String name, final DataContainer container)
        throws DataIsNotValid {
            super(optionType, name, container);
        }

        @Override
        protected String extractInlineValue (final String option) {
            return "1";
        }

        @Override
        public boolean haveInlineValue (final String option) {
            return true;
        }

    }


    private DataContainer container;


    @Before
    public void setUp () {
        container = new DataContainer(new CCli(null));
        container.setDefaultValue(false);
        container.setValueType(ValueTypes.BOOLEAN);
        container.setHelp("");
    }


    @Test
    public void appropriate () {
        ParseableOption s = new ParseableOptionStub(OptionTypes.SHORT, "z", container);
        ParseableOption l = new ParseableOptionStub(OptionTypes.LONG, "verbose", container);
        ParseableOption c = new ParseableOptionStub(OptionTypes.CUSTOM, "-DINLINE", container);

        assertTrue(s.appropriate("-z"));
        assertTrue(l.appropriate("--verbose"));
        assertTrue(c.appropriate("-DINLINE"));

        assertFalse(s.appropriate("-z1"));
        assertFalse(s.appropriate("-z1."));
        assertFalse(s.appropriate("-z1.0"));
        assertFalse(s.appropriate("-z1.0e1"));
        assertFalse(s.appropriate("-z1.0e01"));
        assertFalse(s.appropriate("-z1e+01"));
        assertFalse(s.appropriate("-z1e+01f"));
        assertFalse(s.appropriate("-z1f"));

        assertFalse(l.appropriate("--verbose1"));
        assertFalse(l.appropriate("--verbose=1"));
        assertFalse(l.appropriate("--verbose=false"));

        assertFalse(l.appropriate("-DINLINE=true"));
        assertFalse(l.appropriate("-DINLINE=1"));
    }


    @Test
    public void checkNumerical () {
        assertTrue(ParseableOption.isPureNumerical("9"));
        assertTrue(ParseableOption.isPureNumerical("999"));
        assertTrue(ParseableOption.isPureNumerical("0999"));

        assertFalse(ParseableOption.isPureNumerical("9-9"));
        assertFalse(ParseableOption.isPureNumerical("-9"));
        assertFalse(ParseableOption.isPureNumerical("9-"));
        assertFalse(ParseableOption.isPureNumerical("test9"));
        assertFalse(ParseableOption.isPureNumerical("9test"));
    }


    @Test
    public void checkParsing () {
        ParseableOption o = new ParseableOptionStub(OptionTypes.SHORT, "z", container);
        assertTrue((Boolean)o.parse("1"));
    }


    @Test (expected = DataIsNotValid.class)
    public void failedCreateWithInlinePrefixInName () {
        @SuppressWarnings ("unused") ParseableOption o = new ParseableOptionStub(OptionTypes.SHORT, "z", container);

        o = new ParseableOptionStub(OptionTypes.SHORT, "lets=rock", container);
    }

}
