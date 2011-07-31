package org.aerialsounds.ccli.valueparsers;



final class FloatParser
    implements NumberValueParser {


    @Override
    public Object parse (String value) {
        try {
            return Float.valueOf(value);
        }
        catch (Exception e) {
            return null;
        }
    }

}
