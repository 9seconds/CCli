package org.aerialsounds.nanocli.valueparsers;



final class NoneParser
    implements ValueParser {


    @Override
    public Object parse (String value) {
        return null;
    }

}
