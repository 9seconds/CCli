package org.aerialsounds.ccli.valueparsers;




final class BooleanParser
    extends BooleanConverter
    implements ValueParser {


    @Override
    public Object parse (String value) {
        try {
            return convert(value);
        }
        catch (Exception e) {
            return null;
        }
    }

}
