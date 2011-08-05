package org.aerialsounds.ccli.options;

import java.util.regex.Pattern;

import org.aerialsounds.ccli.OptionTypes;
import org.aerialsounds.ccli.datacontainer.DataContainer;
import org.aerialsounds.ccli.valueparsers.ValueParser;



abstract public class ParseableOption
    extends AbstractOption {

    protected final ValueParser parser;

    static protected final int patternFlags;
    static public final String STRING_INLINE_DELIMETER;
    static final private Pattern numericalRegexp;

    static {
        patternFlags = Pattern.UNICODE_CASE;
        STRING_INLINE_DELIMETER = "=".intern();
        numericalRegexp = Pattern.compile("^\\d+$", patternFlags);
    }


    public ParseableOption (final OptionTypes optionType, final String name, final DataContainer container) throws CannotCreateSuchOption {
        super(optionType, name, container);
        parser = getValueType().createParser();

        if ( !isDataValid() )
            throw new CannotCreateSuchOption();
    }

    protected boolean isDataValid () {
        return !haveInlineDelimeter(fullName);
    }

    public Object parse(final String value) {
        return parser.parse(value);
    }

    public String getInlineValue(final String option) {
        return ( haveInlineValue(option) )
            ? extractInlineValue(option)
            : null;
    }

    public boolean appropriate(final String value) {
        return value.equals(fullName);
    }

    abstract public boolean haveInlineValue(final String option);
    abstract protected String extractInlineValue(final String option);

    static public boolean isPureNumerical(final String option) {
        return numericalRegexp.matcher(option).matches();
    }

    static protected boolean haveInlineDelimeter(final String option) {
        return ( option.indexOf(STRING_INLINE_DELIMETER) != -1 );
    }


    static public class CannotCreateSuchOption extends RuntimeException {
        private static final long serialVersionUID = -3486882571119501655L;
    }

}
