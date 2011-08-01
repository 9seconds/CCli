


package org.aerialsounds.ccli.valueparsers;



final public class DoubleParser
    implements NumberValueParser {


    @Override
    public Object parse (final String value) {
        try {
            return Double.valueOf(value);
        }
        catch ( Exception e ) {
            return null;
        }
    }

}
