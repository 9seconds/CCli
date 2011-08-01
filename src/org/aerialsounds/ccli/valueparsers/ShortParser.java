


package org.aerialsounds.ccli.valueparsers;



final public class ShortParser
    implements NumberValueParser {


    @Override
    public Object parse (final String value) {
        try {
            return Short.valueOf(value);
        }
        catch ( Exception e ) {
            return null;
        }
    }

}
