package org.aerialsounds.ccli.options;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.aerialsounds.ccli.OptionTypes;
import org.aerialsounds.ccli.datacontainer.DataContainer;



public class ShortOption
    extends ParseableOption {

    static protected final Pattern inlineRegexp = Pattern.compile("\\D+((\\d+(\\.?\\d*[eE]?[\\+\\-]?0?\\d+)?)|(\\.?\\d*[eE]?[\\+\\-]?\\d+))$");
    static protected final Pattern numbersRegexp = Pattern.compile("\\D*(\\d+)\\D*");

    public ShortOption (OptionTypes optionType, String customPrefix, String name, DataContainer container) {
        super(optionType, customPrefix, name, container);
    }

    @Override
    protected String extractInlineValue (String option) {
        Matcher match = inlineRegexp.matcher(option);
        return ( match.groupCount() > 0 )
            ? match.group(1)
            : null;
    }

    @Override
    protected void checkCorrectness() throws CannotCreateSuchOption {
        super.checkCorrectness();
        if ( name.length() != 1 )
            throw new CannotCreateSuchOption();
    }

    @Override
    public boolean haveInlineValue (String option) {
        return ( getValueType().isNumber() && inlineRegexp.matcher(option).matches() );
    }

    static public boolean haveNumbers(String option) {
        return ( numbersRegexp.matcher(option).matches() );
    }

}
