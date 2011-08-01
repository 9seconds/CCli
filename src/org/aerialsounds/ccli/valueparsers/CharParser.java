


package org.aerialsounds.ccli.valueparsers;



final public class CharParser
    implements ValueParser {


    @Override
    public Object parse (final String value) {
        if ( value == null ) return null;

        return (value.length() == 1)
            ? value.toCharArray()[0]
            : null;
    }

}
