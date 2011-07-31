package org.aerialsounds.nanocli.options;

import java.util.regex.Pattern;

import org.aerialsounds.nanocli.OptionTypes;
import org.aerialsounds.nanocli.datacontainer.DataContainer;
import org.aerialsounds.nanocli.valueparsers.ValueParser;
import org.aerialsounds.nanocli.valueparsers.ValueParserFactory;



abstract public class ParseableOption
    extends AbstractOption {

    final protected ValueParser parser;

    static final private Pattern numericalRegexp = Pattern.compile("^\\d+$");

    public ParseableOption (OptionTypes optionType, String customPrefix, String name, DataContainer container) throws CannotCreateSuchOption {
        super(optionType, customPrefix, name, container);
        parser = ValueParserFactory.create(getValueType());

        checkCorrectness();
    }

    protected void checkCorrectness () throws CannotCreateSuchOption {
        if ( name.indexOf(STRING_INLINE_DELIMETER) != -1 )
            throw new CannotCreateSuchOption();
    }

    public Object parse(String value) {
        return parser.parse(value);
    }

    public String getInlineValue(String option) {
        return ( haveInlineValue(option) )
            ? extractInlineValue(option)
            : null;
    }

    abstract public boolean haveInlineValue(String option);
    abstract protected String extractInlineValue(String option);

    static public boolean isPureNumerical(String option) {
        return numericalRegexp.matcher(option).matches();
    }


    static public class CannotCreateSuchOption extends RuntimeException {
        private static final long serialVersionUID = -3486882571119501655L;
    }

}
