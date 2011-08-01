


package org.aerialsounds.ccli.valueparsers;



import java.util.concurrent.atomic.AtomicInteger;



final public class AtomicIntegerParser
    implements NumberValueParser {


    @Override
    public Object parse (final String value) {
        try {
            return new AtomicInteger(Integer.valueOf(value));
        }
        catch ( Exception e ) {
            return null;
        }
    }

}
