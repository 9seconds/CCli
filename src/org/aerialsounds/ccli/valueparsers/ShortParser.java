package org.aerialsounds.ccli.valueparsers;



final class ShortParser
    implements NumberValueParser {


    @Override
    public Object parse (String value) {
        try {
            return Short.parseShort(value);
        }
        catch (NumberFormatException e) {
            return null;
        }
    }

}
