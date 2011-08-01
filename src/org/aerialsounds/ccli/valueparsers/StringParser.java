


package org.aerialsounds.ccli.valueparsers;



final public class StringParser
    implements ValueParser {


    @Override
    public Object parse (final String value) {
        return (value != null && value != "")
            ? value
            : null;
    }

}
