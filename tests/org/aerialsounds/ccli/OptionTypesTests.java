package org.aerialsounds.ccli;

import static org.junit.Assert.assertEquals;

import org.junit.Test;




public class OptionTypesTests {

    @Test
    public void checkPrefix () {
        assertEquals("-", OptionTypes.SHORT.getPrefix());
        assertEquals("--", OptionTypes.LONG.getPrefix());
        assertEquals("", OptionTypes.CUSTOM.getPrefix());
    }

}
