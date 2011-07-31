package org.aerialsounds.nanocli.options;

import org.aerialsounds.nanocli.datacontainer.DataContainer;



public class LongOption
    extends ParseableOption {

    static public final String EMPTY_STRING = "".intern();

    public LongOption (OptionTypes optionType, String customPrefix, String name, DataContainer container) {
        super(optionType, customPrefix, name, container);
    }

    @Override
    public boolean haveInlineValue(String option) {
        return ( option.indexOf(STRING_INLINE_DELIMETER) == fullName.length() );
    }

    @Override
    protected String extractInlineValue(String option) {
        return ( option.endsWith(STRING_INLINE_DELIMETER) )
            ? EMPTY_STRING
            : option.substring(fullName.length() + 1);
    }

}
