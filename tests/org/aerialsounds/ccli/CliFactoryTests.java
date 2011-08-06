package org.aerialsounds.ccli;

import static org.junit.Assert.*;

import org.aerialsounds.ccli.datacontainer.DataContainer;
import org.aerialsounds.ccli.options.LongOption;
import org.aerialsounds.ccli.options.NumericalOption;
import org.aerialsounds.ccli.options.ParseableOption;
import org.aerialsounds.ccli.options.ShortOption;
import org.aerialsounds.ccli.options.AbstractOption.DataIsNotValid;
import org.junit.Before;
import org.junit.Test;




public class CliFactoryTests {

    private CliFactory factory;
    private DataContainer container;

    @Before
    public void setUp () {
        factory = new CliFactory(new CCli(null));
        container = factory.createDataContainer(10, ValueTypes.INTEGER, "Just dumb test value");
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
    public void createNumericalOption () {
        container.setValueType(ValueTypes.ATOMIC_BOOLEAN);
        ParseableOption o = factory.createOption(OptionTypes.SHORT, "99", container);
        assertTrue(o instanceof NumericalOption);
    }

    @Test(expected = DataIsNotValid.class)
    public void createNumericalOptionWithFail () {
        @SuppressWarnings ("unused")
        ParseableOption o = factory.createOption(OptionTypes.SHORT, "99", container);
    }

    @Test
    public void createShortOption () {
        ParseableOption o = factory.createOption(OptionTypes.SHORT, "v", container);
        assertTrue(o instanceof ShortOption);
    }

    @Test(expected = DataIsNotValid.class)
    public void createShortOptionWithFail () {
        @SuppressWarnings ("unused")
        ParseableOption o = factory.createOption(OptionTypes.SHORT, "vvv", container);
    }

    @Test
    public void createLongOption () {
        ParseableOption o = factory.createOption(OptionTypes.LONG, "vbr-new", container);
        assertTrue(o instanceof LongOption);
    }

    @Test(expected = DataIsNotValid.class)
    public void createLongOptionWithFail () {
        @SuppressWarnings ("unused")
        ParseableOption o = factory.createOption(OptionTypes.LONG, "vbr-n=ew", container);
    }

    @Test
    public void createCustomOption () {
        ParseableOption o = factory.createOption(OptionTypes.CUSTOM, "-DINLINE", container);
        assertTrue(o instanceof LongOption);
    }

    @Test(expected = DataIsNotValid.class)
    public void createCustomOptionWithFail () {
        @SuppressWarnings ("unused")
        ParseableOption o = factory.createOption(OptionTypes.CUSTOM, "-DINLINE=", container);
    }

}
