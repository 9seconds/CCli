package org.aerialsounds.ccli.valueparsers;




final class IntegerParser
    implements NumberValueParser {

    @Override
    public Object parse (String value) {
        try {
            return Integer.valueOf(value);
        }
        catch (Exception e) {
            return null;
        }

    }

}
