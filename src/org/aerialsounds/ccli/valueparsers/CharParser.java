package org.aerialsounds.ccli.valueparsers;




final class CharParser
    implements ValueParser {


    @Override
    public Object parse (String value) {
        if ( value == null )
            return null;

        return ( value.length() == 1 )
            ? value.toCharArray()[0]
            : null;
    }

}
