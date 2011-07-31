package org.aerialsounds.ccli.options;

import org.aerialsounds.ccli.OptionTypes;
import org.aerialsounds.ccli.datacontainer.DataContainer;
import org.aerialsounds.ccli.options.ParseableOption.CannotCreateSuchOption;




public final class OptionFactory {

    static public ParseableOption create(OptionTypes optionType, String name, DataContainer container) throws CannotCreateSuchOption {
        return create(optionType, AbstractOption.DEFAULT_CUSTOM_PREFIX, name, container);
    }

    static public ParseableOption create(OptionTypes optionType, String customPrefix, String name, DataContainer container) throws CannotCreateSuchOption {
        if ( ParseableOption.isPureNumerical(name) )
            return new NumericalOption(optionType, customPrefix, name, container);

        switch ( optionType ) {
            case SHORT:
                return new ShortOption(optionType, customPrefix, name, container);

            case LONG:
                return new LongOption(optionType, customPrefix, name, container);

            case CUSTOM:
            default:
                return new LongOption(optionType, customPrefix, name, container);
        }
    }

}
