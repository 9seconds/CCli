package org.aerialsounds.ccli;

import org.aerialsounds.ccli.datacontainer.DataContainer;
import org.aerialsounds.ccli.options.OptionFactory;
import org.aerialsounds.ccli.options.ParseableOption;
import org.aerialsounds.ccli.options.ParseableOption.CannotCreateSuchOption;



final class CliFactory {

    private final CCli repository;

    public CliFactory(final CCli repository) {
        this.repository = repository;
    }

    public ParseableOption createOption(final OptionTypes type, final String name, final DataContainer container) throws CannotCreateSuchOption {
        return OptionFactory.create(type, name, container);
    }

    public DataContainer createDataContainer(final Object defaultValue, final ValueTypes valueType, final String help) {
        final DataContainer container = new DataContainer(repository);

        container.setDefaultValue(defaultValue);
        container.setValueType(valueType);
        container.setHelp(help);

        return container;
    }

}
