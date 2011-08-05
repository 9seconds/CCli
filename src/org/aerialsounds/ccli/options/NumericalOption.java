package org.aerialsounds.ccli.options;

import org.aerialsounds.ccli.OptionTypes;
import org.aerialsounds.ccli.datacontainer.DataContainer;



public class NumericalOption
    extends ParseableOption {

    static protected final String trueValue;

    static {
        trueValue = Boolean.TRUE.toString();
    }


    public NumericalOption (final OptionTypes optionType, final String name, final DataContainer container) {
        super(optionType, name, container);
    }

    @Override
    protected boolean isDataValid () {
        return (
               super.isDataValid()
            && ( optionType == OptionTypes.SHORT )
            && getValueType().isBoolean()
        );
    }

    @Override
    protected String extractInlineValue (final String option) {
        return trueValue;
    }


    @Override
    public boolean haveInlineValue (final String option) {
        return option.equals(fullName);
    }

}
