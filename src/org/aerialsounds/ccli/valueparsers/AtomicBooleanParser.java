package org.aerialsounds.ccli.valueparsers;

import java.util.concurrent.atomic.AtomicBoolean;




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
