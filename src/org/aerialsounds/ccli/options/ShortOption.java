


package org.aerialsounds.ccli.options;



import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.aerialsounds.ccli.OptionTypes;
import org.aerialsounds.ccli.datacontainer.DataContainer;



public class ShortOption
    extends ParseableOption {

    static protected final Pattern inlineRegexp;
    static protected final Pattern numbersRegexp;

    static {
        inlineRegexp = Pattern.compile(
            "^[\\D&&[^\\.]]+((?:(?:\\d*\\.?\\d+)|(?:\\d+\\.?\\d*))\\d*(?:[eE][\\+\\-]?\\d+)?)[fd]?$",
            patternFlags
        );
        numbersRegexp = Pattern.compile("\\D*(\\d+)\\D*", patternFlags);
    }


    static public boolean haveNumbers (final String option) {
        return numbersRegexp.matcher(option).matches();
    }


    public ShortOption (final OptionTypes optionType, final String name, final DataContainer container)
        throws DataIsNotValid {
        super(optionType, name, container);
    }


    @Override
    public boolean appropriate (final String value) {
        return super.appropriate(value) || (value.startsWith(fullName) && haveInlineValue(value));
    }


    @Override
    protected String extractInlineValue (final String option) {
        final Matcher match = inlineRegexp.matcher(option);
        return ( match.find() )
            ? match.group(1)
            : null;
    }


    @Override
    public boolean haveInlineValue (final String option) {
        return getValueType().isNumber() && !haveInlineDelimeter(option) && inlineRegexp.matcher(option).matches();
    }


    @Override
    protected boolean isDataValid () {
        return super.isDataValid() && optionType == OptionTypes.SHORT && name.length() == 1;
    }

}
