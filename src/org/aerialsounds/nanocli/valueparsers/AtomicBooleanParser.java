package org.aerialsounds.nanocli.valueparsers;

import java.util.concurrent.atomic.AtomicBoolean;

import org.aerialsounds.nanocli.ValueParser;



final class AtomicBooleanParser
    extends BooleanConverter
    implements ValueParser {


    @Override
    public Object parse (String value) {
        try {
            return new AtomicBoolean(convert(value));
        }
        catch (Exception e) {
            return null;
        }
    }

}
