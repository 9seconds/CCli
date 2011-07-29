package org.aerialsounds.nanocli.options;

import org.aerialsounds.nanocli.datacontainer.DataContainer;



public class LongOption
    extends ParseableOption {
    
    static public final String INLINE_DELIMETER = "=";
    
    public LongOption (OptionTypes optionType, String name, DataContainer container) {
        super(optionType, name, container);
    }

    public LongOption (OptionTypes optionType, String customPrefix, String name, DataContainer container) {
        super(optionType, customPrefix, name, container);
    }
    
    @Override
    public boolean haveInlineValue(String option) {
        return ( option.indexOf(INLINE_DELIMETER) >= fullName.length() );
    }
    
    @Override
    protected String extractInlineValue(String option) {
        return ( !option.endsWith(INLINE_DELIMETER) )
            ? option.substring(fullName.length() + 1)
            : "";
    }

}
