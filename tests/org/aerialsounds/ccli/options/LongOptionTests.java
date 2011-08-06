package org.aerialsounds.ccli.options;

import static org.junit.Assert.*;

import org.aerialsounds.ccli.OptionTypes;
import org.aerialsounds.ccli.ValueTypes;
import org.aerialsounds.ccli.datacontainer.DataContainer;
import org.aerialsounds.ccli.options.AbstractOption.DataIsNotValid;
import org.junit.Before;
import org.junit.Test;




public class LongOptionTests {

    private DataContainer container;

    @Before
    public void setUp() {
        container = new DataContainer();
        container.setValueType(ValueTypes.BOOLEAN);
    }

    @Test(expected = DataIsNotValid.class)
    public void createWithWrongTypeShort() {
        @SuppressWarnings ("unused")
        LongOption l = new LongOption(OptionTypes.SHORT, "vbr-new", container);
    }

    @Test
    public void appropriate() {
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

    @Test
    public void haveInlineValue() {
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

    @Test
    public void extractInlineValue() {
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

}
