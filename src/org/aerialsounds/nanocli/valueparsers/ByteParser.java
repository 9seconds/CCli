package org.aerialsounds.nanocli.valueparsers;



final class ByteParser
    implements NumberValueParser {


    @Override
    public Object parse (String value) {
        try {
            return Byte.parseByte(value);
        }
        catch (NumberFormatException e) {
            return null;
        }
    }

}
