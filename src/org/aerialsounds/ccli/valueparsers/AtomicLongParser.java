


package org.aerialsounds.ccli.valueparsers;



import java.util.concurrent.atomic.AtomicLong;



final public class AtomicLongParser
    implements NumberValueParser {


    @Override
    public Object parse (final String value) {
        try {
            return new AtomicLong(Long.parseLong(value));
        }
        catch ( Exception e ) {
            return null;
        }
    }

}
