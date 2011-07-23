package org.aerialsounds.nanocli.valueparsers;

import java.math.BigDecimal;



final class BigDecimalParser
    implements NumberValueParser {


    @Override
    public Object parse (String value) {
        try {
            return BigDecimal.valueOf(Double.parseDouble(value));
        }
        catch (NumberFormatException e) {
            return null;
        }
    }

}
