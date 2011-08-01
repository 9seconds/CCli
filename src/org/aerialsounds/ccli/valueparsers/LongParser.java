


package org.aerialsounds.ccli.valueparsers;



final public class LongParser
    implements NumberValueParser {


    @Override
    public Object parse (final String value) {
        try {
            return Long.valueOf(value);
        }
        catch ( Exception e ) {
            return null;
        }
    }

}
