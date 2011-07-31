package org.aerialsounds.ccli.valueparsers;



public class DoubleParser
    implements NumberValueParser {


    @Override
    public Object parse (String value) {
        try {
            return Double.valueOf(value);
        }
        catch (Exception e) {
            return null;
        }
    }

}
