


package org.aerialsounds.ccli.valueparsers;



final public class BooleanParser
    extends BooleanConverter
    implements ValueParser {


    @Override
    public Object parse (final String value) {
        try {
            return convert(value);
        }
        catch ( Exception e ) {
            return null;
        }
    }

}
