


package org.aerialsounds.ccli.valueparsers;



final public class FloatParser
    implements NumberValueParser {


    @Override
    public Object parse (final String value) {
        try {
            return Float.valueOf(value);
        }
        catch ( Exception e ) {
            return null;
        }
    }

}
