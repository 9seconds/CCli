package org.aerialsounds.nanocli;

import org.aerialsounds.nanocli.datacontainer.DataContainer;
import org.aerialsounds.nanocli.datacontainer.DataContainerFactory;
import org.aerialsounds.nanocli.options.OptionFactory;
import org.aerialsounds.nanocli.options.OptionTypes;
import org.aerialsounds.nanocli.options.ParseableOption;
import org.aerialsounds.nanocli.options.ParseableOption.CannotCreateSuchOption;



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
