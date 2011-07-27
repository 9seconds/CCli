package org.aerialsounds.nanocli;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.TreeMap;

import org.aerialsounds.nanocli.datacontainer.DataContainer;
import org.aerialsounds.nanocli.options.AbstractOption;



public class CliParser implements Observer {

    final protected String[] args;
    final protected CliFactory factory;
    
    private boolean parsed = false;
    
    final protected Map<OptionTypes,Map<String,Option>> options;
    final protected Map<DataContainer,Set<AbstractOption>> containers;
    
    public CliParser(String[] args) {
        this.args = args;
        factory = new CliFactory(this);
        options = new TreeMap<OptionTypes,Map<String,Option>>();
        containers = new HashMap<DataContainer,Set<AbstractOption>>();
    }
    
    public Option createOption(OptionTypes optionType, String name, String help, ValueTypes valueType, Object defaultValue, String prefix) throws HaveSuchName {
        if ( !hasOption(name) ) {
            registerType(optionType);
            return createAndRegisterOption(
                createAndRegisterContainer(defaultValue, valueType, help),
                optionType,
                name,
                prefix
            );
        }
        else
            throw new HaveSuchName();
    }
    
    public Option createOption(OptionTypes optionType, String name, String help, ValueTypes valueType, Object defaultValue) throws HaveSuchName {
        return createOption(
            optionType,
            name,
            help,
            valueType,
            defaultValue,
            factory.getDefaultPrefix(optionType)
        );
    }
    
    private AbstractOption createAndRegisterOption (DataContainer container, OptionTypes optionType, String name, String prefix) {
        AbstractOption opt = factory.createOption(optionType, name, prefix);

        opt.setContainer(container);
        containers.get(container).add(opt);
        opt.addObserver(this);
        
        return opt;
    }

    private DataContainer createAndRegisterContainer (Object defaultValue, ValueTypes valueType, String help) {
        DataContainer container = factory.createDataContainer(defaultValue, valueType, help);

        containers.put(container, new HashSet<AbstractOption>());
        
        return container;
    }

    public boolean hasOption(String name) {
        Iterator<Map<String,Option>> values = options.values().iterator();
        while ( values.hasNext() ) {
            if ( values.next().containsKey(name) )
                return true;
        }
        return false;
    }
    
    public Option getOption(String name) {
        Iterator<Map<String,Option>> values = options.values().iterator();
        while ( values.hasNext() ) {
            Option opt = values.next().get(name);
            if ( opt != null )
                return opt;
        }
        return null;
    }
    
    public Option getOption(String name, OptionTypes type) {
        Map<String,Option> container = options.get(type);
        return ( container != null )
            ? container.get(name)
            : null;
    }
    
    public boolean isParsed() {
        return parsed;
    }
    
    public void parse() {
        if ( !isParsed() ) {
            parsed = true;
        }
    }
    
    private void registerType(OptionTypes type) {
        if ( options.containsKey(type) )
            options.put(type, new TreeMap<String,Option>());
    }

    
    @Override
    public void update (Observable o, Object arg) {
        if ( o instanceof AbstractOption && arg instanceof AbstractOption ) {
            DataContainer firstContainer = ((AbstractOption) o).getContainer();
            DataContainer secondContainer = ((AbstractOption) arg).getContainer();

            Set<AbstractOption> firstSet = containers.get(firstContainer);
            Set<AbstractOption> secondSet = containers.get(secondContainer);

            if ( firstSet != null && secondSet != null ) {
                firstSet.addAll(secondSet);
                for ( AbstractOption opt : secondSet )
                    opt.setContainer(firstContainer);
                containers.remove(secondContainer);
            }
        }
    }
    
    static public class HaveSuchName extends RuntimeException {
        private static final long serialVersionUID = -5248825722401579070L;
    }
    
}
