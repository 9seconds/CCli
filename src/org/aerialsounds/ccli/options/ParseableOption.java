


package org.aerialsounds.ccli.options;



import java.util.regex.Pattern;

import org.aerialsounds.ccli.OptionTypes;
import org.aerialsounds.ccli.datacontainer.DataContainer;
import org.aerialsounds.ccli.valueparsers.ValueParser;



abstract public class ParseableOption
    extends AbstractOption {


    static protected boolean haveInlineDelimeter (final String option) {
        return option.indexOf(STRING_INLINE_DELIMETER) != -1;
    }


    static public boolean isPureNumerical (final String option) {
        return numericalRegexp.matcher(option).matches();
    }

    static protected final int    patternFlags;
    static public    final String STRING_INLINE_DELIMETER;
    static private   final Pattern numericalRegexp;

    static {
        patternFlags            = Pattern.UNICODE_CASE;
        STRING_INLINE_DELIMETER = "=".intern();
        numericalRegexp         = Pattern.compile("^\\d+$", patternFlags);
    }


    protected final ValueParser parser;


    public ParseableOption (final OptionTypes optionType, final String name, final DataContainer container)
        throws DataIsNotValid {
        super(optionType, name, container);
        parser = getValueType().createParser();
    }


    public boolean appropriate (final String value) {
        return value.equals(fullName);
    }


    public String getInlineValue (final String option) {
        return ( haveInlineValue(option) )
            ? extractInlineValue(option)
            : null;
    }

    @Override
    protected boolean isDataValid () {
        return super.isDataValid() && !haveInlineDelimeter(fullName);
    }


    public Object parse (final String value) {
        return parser.parse(value);
    }


    abstract public    boolean haveInlineValue    (final String option);
    abstract protected String  extractInlineValue (final String option);

}
