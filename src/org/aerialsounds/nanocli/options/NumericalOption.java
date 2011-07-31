package org.aerialsounds.nanocli.options;

import org.aerialsounds.nanocli.datacontainer.DataContainer;



public class NumericalOption
    extends ParseableOption {


    public NumericalOption (OptionTypes optionType, String customPrefix, String name, DataContainer container) {
        super(optionType, customPrefix, name, container);
    }

    @Override
    protected void checkCorrectness () throws CannotCreateSuchOption {
        super.checkCorrectness();
        if ( optionType != OptionTypes.SHORT || !getValueType().isBoolean() || customPrefix != null || !customPrefix.equals(DEFAULT_CUSTOM_PREFIX) )
            throw new CannotCreateSuchOption();
    }

    @Override
    protected String extractInlineValue (String option) {
        return Boolean.TRUE.toString();
    }


    @Override
    public boolean haveInlineValue (String option) {
        return true;
    }

}
