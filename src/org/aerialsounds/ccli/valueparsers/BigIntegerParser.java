package org.aerialsounds.ccli.valueparsers;

import java.math.BigInteger;



final class BigIntegerParser
    implements NumberValueParser {


    @Override
    public Object parse (String value) {
        try {
            return new BigInteger(value);
        }
        catch (Exception e) {
            return null;
        }
    }

}
