package org.aerialsounds.ccli.valueparsers;

import java.math.BigDecimal;



final class BigDecimalParser
    implements NumberValueParser {


    @Override
    public Object parse (String value) {
        try {
            return new BigDecimal(value);
        }
        catch (Exception e) {
            return null;
        }
    }

}
