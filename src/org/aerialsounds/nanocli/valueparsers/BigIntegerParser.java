package org.aerialsounds.nanocli.valueparsers;

import java.math.BigInteger;



final class BigIntegerParser
    implements NumberValueParser {


    @Override
    public Object parse (String value) {
        try {
            return BigInteger.valueOf(Long.parseLong(value));
        }
        catch (NumberFormatException e) {
            return null;
        }
    }

}
