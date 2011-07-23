package org.aerialsounds.nanocli.valueparsers;



final class LongParser
    implements NumberValueParser {


    @Override
    public Object parse (String value) {
        try {
            return Long.parseLong(value);
        }
        catch (NumberFormatException e) {
            return null;
        }
    }

}
