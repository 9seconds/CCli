


package org.aerialsounds.ccli.valueparsers;



final public class LongParser
    implements NumberValueParser {


    @Override
    public Object parse (final String value) {
        try {
            return Long.parseLong(value);
        }
        catch ( Exception e ) {
            return null;
        }
    }

}
