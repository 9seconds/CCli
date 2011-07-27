package org.aerialsounds.nanocli.valueparsers;




final class StringParser
    implements ValueParser {

    @Override
    public Object parse (String value) {
        return value;
    }

}
