package org.aerialsounds.nanocli.valueparsers;



public class DoubleParser
    implements NumberValueParser {


    @Override
    public Object parse (String value) {
        try {
            return Double.parseDouble(value);
        }
        catch (NumberFormatException e) {
            return null;
        }
    }

}
