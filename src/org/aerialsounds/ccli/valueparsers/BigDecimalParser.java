


package org.aerialsounds.ccli.valueparsers;



import java.math.BigDecimal;



final public class BigDecimalParser
    implements NumberValueParser {


    @Override
    public Object parse (final String value) {
        try {
            return new BigDecimal(value);
        }
        catch ( Exception e ) {
            return null;
        }
    }

}
