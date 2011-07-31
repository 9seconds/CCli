package org.aerialsounds.ccli.valueparsers;



final class ShortParser
    implements NumberValueParser {


    @Override
    public Object parse (String value) {
        try {
            return Short.valueOf(value);
        }
        catch (Exception e) {
            return null;
        }
    }

}
