package org.aerialsounds.ccli.valueparsers;

import java.util.concurrent.atomic.AtomicInteger;



final class AtomicIntegerParser
    implements NumberValueParser {


    @Override
    public Object parse (String value) {
        try {
            return new AtomicInteger(Integer.parseInt(value));
        }
        catch (NumberFormatException e) {
            return null;
        }
    }

}
