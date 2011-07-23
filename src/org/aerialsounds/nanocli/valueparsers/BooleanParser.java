package org.aerialsounds.nanocli.valueparsers;

import org.aerialsounds.nanocli.ValueParser;



final class BooleanParser
    extends BooleanConverter
    implements ValueParser {


    @Override
    public Object parse (String value) {
        try {
            return convert(value);
        }
        catch (Exception e) {
            return null;
        }
    }

}
