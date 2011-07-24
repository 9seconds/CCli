package org.aerialsounds.nanocli;

import org.aerialsounds.nanocli.options.DataContainer;
import org.aerialsounds.nanocli.options.GenericOption;
import org.aerialsounds.nanocli.options.OptionFactory;
import org.aerialsounds.nanocli.valueparsers.ValueParser;
import org.aerialsounds.nanocli.valueparsers.ValueParserFactory;



final class CliFactory {
    
    private CliParser repository;
    
    public CliFactory(CliParser repository) {
        this.repository = repository;
    }
    
    public GenericOption createOption(OptionTypes type, String name) {
        return OptionFactory.create(type, name);
    }
    
    public ValueParser createValueParser(ValueTypes type) {
        return ValueParserFactory.create(type);
    }
    
    public DataContainer createDataContainer(Object defaultValue, ValueTypes valueType, String help) {
        DataContainer container = new DataContainer(repository);

        container.setDefaultValue(defaultValue);
        container.setValueType(valueType);
        container.setHelp(help);
     
        return container;
    }

}
