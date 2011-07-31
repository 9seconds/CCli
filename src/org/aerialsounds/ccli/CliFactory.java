package org.aerialsounds.ccli;

import org.aerialsounds.ccli.datacontainer.DataContainer;
import org.aerialsounds.ccli.datacontainer.DataContainerFactory;
import org.aerialsounds.ccli.options.OptionFactory;
import org.aerialsounds.ccli.options.ParseableOption;
import org.aerialsounds.ccli.options.ParseableOption.CannotCreateSuchOption;



final class CliFactory {

    private CliParser repository;

    public CliFactory(CliParser repository) {
        this.repository = repository;
    }

    public ParseableOption createOption(OptionTypes type, String customPrefix, String name, DataContainer container) throws CannotCreateSuchOption {
        return OptionFactory.create(type, customPrefix, name, container);
    }

    public ParseableOption createOption(OptionTypes type, String name, DataContainer container) throws CannotCreateSuchOption {
        return OptionFactory.create(type, name, container);
    }

    public DataContainer createDataContainer(Object defaultValue, ValueTypes valueType, String help) {
        DataContainer container = DataContainerFactory.create(repository);

        container.setDefaultValue(defaultValue);
        container.setValueType(valueType);
        container.setHelp(help);

        return container;
    }

}