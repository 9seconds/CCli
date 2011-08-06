


package org.aerialsounds.ccli.options;



import org.aerialsounds.ccli.OptionTypes;
import org.aerialsounds.ccli.datacontainer.DataContainer;
import org.aerialsounds.ccli.valueparsers.BooleanConverter;



final public class NumericalOption
    extends ParseableOption {


    public NumericalOption (final OptionTypes optionType, final String name, final DataContainer container)
        throws DataIsNotValid {
        super(optionType, name, container);
    }


    @Override
    protected String extractInlineValue (final String option) {
        return BooleanConverter.TRUE;
    }


    @Override
    public boolean haveInlineValue (final String option) {
        return option.equals(fullName);
    }


    @Override
    protected boolean isDataValid () {
        return super.isDataValid() && optionType == OptionTypes.SHORT && getValueType().isBoolean();
    }

}
