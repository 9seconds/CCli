package org.aerialsounds.nanocli.valueparsers;

import org.aerialsounds.nanocli.ValueParser;



final class StringParser
    implements ValueParser {


    @Override
    public Object parse (String value) {
        return value;
    }

}
