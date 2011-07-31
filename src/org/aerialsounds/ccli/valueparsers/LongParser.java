package org.aerialsounds.ccli.valueparsers;



final class LongParser
    implements NumberValueParser {


    @Override
    public Object parse (String value) {
        try {
            return Long.valueOf(value);
        }
        catch (Exception e) {
            return null;
        }
    }

}
