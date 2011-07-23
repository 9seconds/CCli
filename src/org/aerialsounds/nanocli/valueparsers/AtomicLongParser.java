package org.aerialsounds.nanocli.valueparsers;

import java.util.concurrent.atomic.AtomicLong;



final class AtomicLongParser
    implements NumberValueParser {


    @Override
    public Object parse (String value) {
        try {
            return new AtomicLong(Long.parseLong(value));
        }
        catch (NumberFormatException e) {
            return null;
        }
    }

}
