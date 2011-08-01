


package org.aerialsounds.ccli.valueparsers;



import java.util.concurrent.atomic.AtomicBoolean;



final public class AtomicBooleanParser
    extends BooleanConverter
    implements ValueParser {


    @Override
    public Object parse (final String value) {
        try {
            return new AtomicBoolean(convert(value));
        }
        catch ( Exception e ) {
            return null;
        }
    }

}
