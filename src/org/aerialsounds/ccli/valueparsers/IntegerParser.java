


package org.aerialsounds.ccli.valueparsers;



final public class IntegerParser
    implements NumberValueParser {


    @Override
    public Object parse (final String value) {
        try {
            return Integer.valueOf(value);
        }
        catch ( Exception e ) {
            return null;
        }

    }

}
