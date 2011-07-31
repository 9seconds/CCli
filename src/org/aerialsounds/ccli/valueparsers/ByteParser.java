package org.aerialsounds.ccli.valueparsers;



final class ByteParser
    implements NumberValueParser {


    @Override
    public Object parse (String value) {
        try {
            return Byte.valueOf(value);
        }
        catch (Exception e) {
            return null;
        }
    }

}
