package org.aerialsounds.ccli.options;

import org.aerialsounds.ccli.OptionTypes;
import org.aerialsounds.ccli.datacontainer.DataContainer;



public class LongOption
    extends ParseableOption {

    static public final String EMPTY_STRING = "".intern();

    protected final String fullNameWithDelimeter;

    public LongOption (final OptionTypes optionType, final String name, final DataContainer container) {
        super(optionType, name, container);
        fullNameWithDelimeter = fullName + STRING_INLINE_DELIMETER;
    }

    @Override
    public boolean appropriate(final String value) {
        return super.appropriate(value) || value.startsWith(fullNameWithDelimeter);
    }

    @Override
    public boolean haveInlineValue(final String option) {
        return ( option.indexOf(STRING_INLINE_DELIMETER) == fullName.length() );
    }

    @Override
    protected String extractInlineValue(final String option) {
        return ( option.endsWith(STRING_INLINE_DELIMETER) )
            ? EMPTY_STRING
            : option.substring(fullName.length() + 1);
    }

}
