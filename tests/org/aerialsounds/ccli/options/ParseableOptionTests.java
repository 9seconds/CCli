package org.aerialsounds.ccli.options;

import static org.junit.Assert.*;

import org.aerialsounds.ccli.OptionTypes;
import org.aerialsounds.ccli.ValueTypes;
import org.aerialsounds.ccli.datacontainer.DataContainer;
import org.aerialsounds.ccli.options.AbstractOption.DataIsNotValid;
import org.junit.Before;
import org.junit.Test;




public class ParseableOptionTests {

    class ParseableOptionStub extends ParseableOption {

        public ParseableOptionStub (OptionTypes optionType, String name, DataContainer container)
            throws DataIsNotValid {
            super(optionType, name, container);
        }

        @Override
        protected String extractInlineValue (String option) {
            return "1";
        }

        @Override
        public boolean haveInlineValue (String option) {
            return true;
        }

    }

    private DataContainer container;

    @Before
    public void setUp() {
        container = new DataContainer(null);
        container.setValueType(ValueTypes.BOOLEAN);
    }

    @Test(expected = DataIsNotValid.class)
    public void failedCreateWithInlinePrefixInName() {
        @SuppressWarnings ("unused")
        ParseableOption o = new ParseableOptionStub(OptionTypes.SHORT, "z", container);

        o = new ParseableOptionStub(OptionTypes.SHORT, "lets=rock", container);
    }

    @Test
    public void checkNumerical() {
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
    public void checkParsing() {
        ParseableOption o = new ParseableOptionStub(OptionTypes.SHORT, "z", container);
        assertTrue((Boolean) o.parse("1"));
    }

    @Test
    public void appropriate() {
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

}
