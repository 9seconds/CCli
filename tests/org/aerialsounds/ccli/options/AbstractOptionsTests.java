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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashSet;

import org.aerialsounds.ccli.CCli;
import org.aerialsounds.ccli.Option;
import org.aerialsounds.ccli.OptionTypes;
import org.aerialsounds.ccli.ValueTypes;
import org.aerialsounds.ccli.datacontainer.DataContainer;
import org.aerialsounds.ccli.datacontainer.DataContainer.DataContainerException;
import org.aerialsounds.ccli.optionobservable.Observable;
import org.aerialsounds.ccli.options.AbstractOption.CannotBind;
import org.aerialsounds.ccli.options.AbstractOption.DataIsNotValid;
import org.aerialsounds.ccli.options.AbstractOption.NotCompatibleClasses;
import org.junit.Before;
import org.junit.Test;



public class AbstractOptionsTests {

    class AbstractOptionStub extends AbstractOption {

        public AbstractOptionStub (OptionTypes optionType, String name, DataContainer container) {
            super(optionType, name, container);
        }

    }

    class CCliStub extends CCli {

        public CCliStub () {
            super(null);
        }

        @Override
        public void update (Observable initiator, Object initiated) {
            AbstractOption one = (AbstractOption) initiator;
            AbstractOption another = (AbstractOption) initiated;

            another.setContainer(one.getContainer());

            updateValue++;
        }
    }

    private CCli ccliStub;
    private DataContainer container;
    private int updateValue;

    @Before
    public void setUp() {
        ccliStub = new CCliStub();
        updateValue = 0;
        container = new DataContainer(ccliStub);

        container.setHelp("help");
        container.setDefaultValue(Integer.MAX_VALUE);
        container.setValueType(ValueTypes.INTEGER);
    }

    @Test
    public void create() {
        AbstractOption o = new AbstractOptionStub(OptionTypes.SHORT, "no-rtti", container);
        o = new AbstractOptionStub(OptionTypes.SHORT, "z", container);
        o = new AbstractOptionStub(OptionTypes.LONG, "vbr-new", container);
        o = new AbstractOptionStub(OptionTypes.CUSTOM, "INLINE", container);

        o.dispose();
    }

    @Test(expected = DataIsNotValid.class)
    public void notValidDataWithNull() {
        @SuppressWarnings ("unused")
        AbstractOption o = new AbstractOptionStub(OptionTypes.SHORT, null, container);
    }

    @Test(expected = DataIsNotValid.class)
    public void notValidDataWithEmptyString() {
        @SuppressWarnings ("unused")
        AbstractOption o = new AbstractOptionStub(OptionTypes.SHORT, "", container);
    }

    @Test
    public void gettingAndSetting() {
        AbstractOption o = new AbstractOptionStub(OptionTypes.SHORT, "no-rtti", container);

        assertEquals(o.getContainer(), container);
        DataContainer c = new DataContainer(ccliStub);
        o.setContainer(c);
        assertEquals(o.getContainer(), c);

        o.setContainer(container);
        assertEquals(o.getContainer(), container);

        assertEquals("help", o.getHelp());
        assertEquals("no-rtti", o.getName());
        assertEquals(OptionTypes.SHORT, o.getType());
        assertEquals(ValueTypes.INTEGER, o.getValueType());
        assertFalse(o.isParsed());
        assertEquals(container.getDefaultValue(), o.getValue());

        container.setValue(Integer.MIN_VALUE);
        assertEquals(container.getValue(), o.getValue());

        o.setValue(30000);
        assertEquals(30000, o.getValue());
        assertEquals(30000, o.getContainer().getValue());

        o.setValue(null);
        assertFalse(o.isParsed());
        assertNull(o.getContainer().getValue());
        assertEquals(Integer.MAX_VALUE, o.getValue());
    }

    @Test
    public void equalTest() {
        AbstractOption o = new AbstractOptionStub(OptionTypes.SHORT, "no-rtti", container);
        AbstractOption ko = new AbstractOptionStub(OptionTypes.LONG, "inline", container);
        HashSet<Integer> set = new HashSet<Integer>();

        assertEquals(o, o);
        assertFalse(o.equals(set));

        assertTrue(o.equals(ko));

        ko.setContainer(new DataContainer(ccliStub));
        assertFalse(o.equals(ko));
    }

    @Test
    public void fullNameTesting() {
        AbstractOption a = new AbstractOptionStub(OptionTypes.SHORT, "fno-rtti", container);
        AbstractOption b = new AbstractOptionStub(OptionTypes.SHORT, "z", container);
        AbstractOption c = new AbstractOptionStub(OptionTypes.LONG, "finline-functions", container);
        AbstractOption d = new AbstractOptionStub(OptionTypes.LONG, "vbr-new", container);
        AbstractOption e = new AbstractOptionStub(OptionTypes.CUSTOM, "if", container);
        AbstractOption f = new AbstractOptionStub(OptionTypes.CUSTOM, "-DINLINE", container);
        AbstractOption g = new AbstractOptionStub(OptionTypes.CUSTOM, "--vbr-new", container);

        assertEquals("-fno-rtti", a.getFullName());
        assertEquals("-z", b.getFullName());
        assertEquals("--finline-functions", c.getFullName());
        assertEquals("--vbr-new", d.getFullName());
        assertEquals("if", e.getFullName());
        assertEquals("-DINLINE", f.getFullName());
        assertEquals("--vbr-new", g.getFullName());
    }

    @Test(expected = NotCompatibleClasses.class)
    public void bindingWithException() throws Throwable {
        AbstractOption a = new AbstractOptionStub(OptionTypes.SHORT, "no-rtti", container);
        Option opt = new Option() {

            @Override
            public void bind (Option other) throws CannotBind {}

            @Override
            public String getFullName () {return null;}

            @Override
            public String getHelp () {return null;}

            @Override
            public String getName () {return null;}

            @Override
            public OptionTypes getType () {return null;}

            @Override
            public Object getValue () {return null;}

            @Override
            public ValueTypes getValueType () {return null;}

            @Override
            public boolean isParsed () {return false;}

        };
        try {
            a.bind(opt);
            fail();
        }
        catch ( CannotBind e ) {
            throw e.getCause();
        }
    }

    @Test
    public void selfBinding() {
        AbstractOption a = new AbstractOptionStub(OptionTypes.SHORT, "fno-rtti", container);
        AbstractOption b = new AbstractOptionStub(OptionTypes.SHORT, "fno-rtti", container);

        assertEquals(a, b);
        a.bind(a);
        assertEquals(a, b);
    }

    @Test
    public void bindingWithUpdating() {
        AbstractOption a = new AbstractOptionStub(OptionTypes.SHORT, "fno-rtti", container);
        AbstractOption b = new AbstractOptionStub(OptionTypes.LONG, "vbr-new", new DataContainer(ccliStub));

        assertEquals(0, updateValue);
        a.bind(b);
        assertEquals(1, updateValue);
        b.bind(a);
        assertEquals(1, updateValue);
        a.bind(b);
        assertEquals(1, updateValue);

        assertEquals(a, b);
        assertEquals(b, a);
    }

    @Test(expected = DataContainerException.class)
    public void failedBinding() throws Throwable {
        DataContainer c1 = new DataContainer(ccliStub);
        DataContainer c2 = new DataContainer(ccliStub);

        c1.setHelp("111");
        c1.setValueType(ValueTypes.INTEGER);
        c1.setValue(BigDecimal.ONE);
        c1.setDefaultValue(BigDecimal.TEN);

        c2.setHelp("222");
        c2.setValueType(ValueTypes.BIG_INTEGER);
        c2.setDefaultValue(BigInteger.TEN);

        AbstractOption a = new AbstractOptionStub(OptionTypes.SHORT, "fno-rtti", c1);
        AbstractOption b = new AbstractOptionStub(OptionTypes.LONG, "vbr-new", c2);

        try {
            a.bind(b);
            fail();
        }
        catch ( CannotBind e ) {
            assertEquals(0, updateValue);
            throw e.getCause();
        }
    }

}
