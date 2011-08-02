package org.aerialsounds.ccli.options;


import static org.junit.Assert.*;

import java.util.HashSet;

import org.aerialsounds.ccli.CCli;
import org.aerialsounds.ccli.OptionTypes;
import org.aerialsounds.ccli.ValueTypes;
import org.aerialsounds.ccli.datacontainer.DataContainer;
import org.junit.Before;
import org.junit.Test;



public class AbstractOptionsTest {

    class AbstractOptionStub extends AbstractOption {

        public AbstractOptionStub (OptionTypes optionType, String customPrefix, String name, DataContainer container) {
            super(optionType, customPrefix, name, container);
        }

    }

    private CCli ccliStub;
    private DataContainer container;

    @Before
    public void setUp() {
        ccliStub = new CCli(null);
        container = new DataContainer(ccliStub);

        container.setHelp("help");
        container.setDefaultValue(Integer.MAX_VALUE);
        container.setValueType(ValueTypes.INTEGER);
    }

    @Test
    public void create() {
        AbstractOption o = new AbstractOptionStub(OptionTypes.SHORT, "f", "no-rtti", container);
        o = new AbstractOptionStub(OptionTypes.SHORT, "", "z", container);
        o = new AbstractOptionStub(OptionTypes.LONG, "", "vbr-new", container);
        o = new AbstractOptionStub(OptionTypes.CUSTOM, "-D", "INLINE", container);

        o.dispose();
    }

    @Test
    public void gettingAndSetting() {
        AbstractOption o = new AbstractOptionStub(OptionTypes.SHORT, "f", "no-rtti", container);

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
        assertEquals("f", o.getCustomPrefix());
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
        AbstractOption o = new AbstractOptionStub(OptionTypes.SHORT, "f", "no-rtti", container);
        AbstractOption ko = new AbstractOptionStub(OptionTypes.LONG, "", "inline", container);
        HashSet<Integer> set = new HashSet<Integer>();

        assertEquals(o, o);
        assertFalse(o.equals(set));

        assertTrue(o.equals(ko));

        ko.setContainer(new DataContainer(ccliStub));
        assertFalse(o.equals(ko));
    }

    @Test
    public void fullNameTesting() {
        AbstractOption a = new AbstractOptionStub(OptionTypes.SHORT, "f", "no-rtti", container);
        AbstractOption b = new AbstractOptionStub(OptionTypes.SHORT, "", "z", container);
        AbstractOption c = new AbstractOptionStub(OptionTypes.LONG, "f", "inline-functions", container);
        AbstractOption d = new AbstractOptionStub(OptionTypes.LONG, "", "vbr-new", container);
        AbstractOption e = new AbstractOptionStub(OptionTypes.CUSTOM, "", "if", container);
        AbstractOption f = new AbstractOptionStub(OptionTypes.CUSTOM, "-D", "INLINE", container);
        AbstractOption g = new AbstractOptionStub(OptionTypes.CUSTOM, "--", "vbr-new", container);

        assertEquals("-fno-rtti", a.getFullName());
        assertEquals("-z", b.getFullName());
        assertEquals("--finline-functions", c.getFullName());
        assertEquals("--vbr-new", d.getFullName());
        assertEquals("if", e.getFullName());
        assertEquals("-DINLINE", f.getFullName());
        assertEquals("--vbr-new", g.getFullName());
    }

}
