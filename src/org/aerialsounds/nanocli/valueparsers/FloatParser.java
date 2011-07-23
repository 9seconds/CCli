package org.aerialsounds.nanocli.valueparsers;



final class FloatParser
    implements NumberValueParser {


    @Override
    public Object parse (String value) {
        try {
            return Float.parseFloat(value);
        }
        catch (NumberFormatException e) {
            return null;
        }
    }

}
