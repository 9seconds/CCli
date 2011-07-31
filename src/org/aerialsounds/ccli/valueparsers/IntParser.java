package org.aerialsounds.ccli.valueparsers;




final class IntParser
    implements NumberValueParser {

    @Override
    public Object parse (String value) {
        try {
            return Integer.parseInt(value);
        }
        catch (NumberFormatException e) {
            return null;
        }
        
    }

}
