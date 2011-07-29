package org.aerialsounds.nanocli.options;

import org.aerialsounds.nanocli.datacontainer.DataContainer;



public class NumericalOption
    extends ParseableOption {


    public NumericalOption (OptionTypes optionType, String name, DataContainer container) {
        super(optionType, name, container);
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
