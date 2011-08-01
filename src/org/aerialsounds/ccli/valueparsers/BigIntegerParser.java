


package org.aerialsounds.ccli.valueparsers;



import java.math.BigInteger;



final public class BigIntegerParser
    implements NumberValueParser {


    @Override
    public Object parse (final String value) {
        try {
            return new BigInteger(value);
        }
        catch ( Exception e ) {
            return null;
        }
    }

}
