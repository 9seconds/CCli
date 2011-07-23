package org.aerialsounds.nanocli.valueparsers;

import org.aerialsounds.nanocli.ValueParser;



final class CharParser
    implements ValueParser {


    @Override
    public Object parse (String value) {
        return ( value.length() == 1 )
            ? value.toCharArray()[0]
            : null;
    }

}
