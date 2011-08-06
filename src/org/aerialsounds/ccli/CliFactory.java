


package org.aerialsounds.ccli;



import org.aerialsounds.ccli.datacontainer.DataContainer;
import org.aerialsounds.ccli.options.LongOption;
import org.aerialsounds.ccli.options.NumericalOption;
import org.aerialsounds.ccli.options.ParseableOption;
import org.aerialsounds.ccli.options.ShortOption;
import org.aerialsounds.ccli.options.AbstractOption.DataIsNotValid;



class CliFactory {


    private final CCli repository;


    public CliFactory (final CCli repository) {
        this.repository = repository;
    }


    public DataContainer createDataContainer (final Object defaultValue, final ValueTypes valueType, final String help) {
        final DataContainer container = new DataContainer(repository);

        container.setDefaultValue(defaultValue);
        container.setValueType(valueType);
        container.setHelp(help);

        return container;
    }


    public ParseableOption createOption (final OptionTypes type, final String name, final DataContainer container)
        throws DataIsNotValid {
        if ( type == OptionTypes.SHORT && ParseableOption.isPureNumerical(name) )
            return new NumericalOption(type, name, container);

        switch ( type ) {
            case SHORT:
                return new ShortOption(type, name, container);

            default:
                return new LongOption(type, name, container);
        }
    }

}
