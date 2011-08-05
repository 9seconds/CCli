package org.aerialsounds.ccli.options;

import org.aerialsounds.ccli.OptionTypes;
import org.aerialsounds.ccli.datacontainer.DataContainer;
import org.aerialsounds.ccli.options.ParseableOption.CannotCreateSuchOption;




public final class OptionFactory {

    static public ParseableOption create(final OptionTypes optionType, final String name, final DataContainer container) throws CannotCreateSuchOption {
        if ( optionType == OptionTypes.SHORT && ParseableOption.isPureNumerical(name) )
            return new NumericalOption(optionType, name, container);

        switch ( optionType ) {
            case SHORT:
                return new ShortOption(optionType, name, container);

            case LONG:
                return new LongOption(optionType, name, container);

            case CUSTOM:
            default:
                return new LongOption(optionType, name, container);
        }
    }

}
