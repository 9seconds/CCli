package org.aerialsounds.nanocli;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.TreeMap;

import org.aerialsounds.nanocli.options.DataContainer;
import org.aerialsounds.nanocli.options.GenericOption;



public class CliParser implements Observer {

    final protected String[] args;
    final protected String OPTION_SIGN;
    final protected CliFactory factory;
    
    final static public String DEFAULT_OPTION_SIGN = "-";
    
    private boolean parsed = false;
    
    protected Map<OptionTypes,Map<String,Option>> options = new TreeMap<OptionTypes,Map<String,Option>>();
    protected Map<DataContainer,Set<GenericOption>> containers = new HashMap<DataContainer,Set<GenericOption>>();
    
    public CliParser(String[] args) {
        this(args, DEFAULT_OPTION_SIGN);
    }
    
    public CliParser(String[] args, String optionSign) {
        this.args = args;
        OPTION_SIGN = optionSign;
        factory = new CliFactory(this);
    }
    
    public Option createOption(OptionTypes optionType, ValueTypes valueType, Object defaultValue, String name, String help) throws HaveSuchName {
        if ( !hasOption(name) ) {
            registerType(optionType);
            DataContainer container = createAndRegisterContainer(defaultValue, valueType, help);
            return createAndRegisterOption(container, optionType, name);
        }
        else
            throw new HaveSuchName();

    }
    
    private GenericOption createAndRegisterOption (DataContainer container, OptionTypes optionType, String name) {
        GenericOption opt = factory.createOption(optionType, name);

        opt.setContainer(container);
        containers.get(container).add(opt);
        opt.addObserver(this);
        
        return opt;
    }

    private DataContainer createAndRegisterContainer (Object defaultValue, ValueTypes valueType, String help) {
        DataContainer container = factory.createDataContainer(defaultValue, valueType, help);

        containers.put(container, new HashSet<GenericOption>());
        
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
    
    static public class OverrideHelp extends Exception {
        private static final long serialVersionUID = 760144351098670916L;
    }
    static public class OverrideValue extends Exception {
        private static final long serialVersionUID = -738739296317104089L;
    }
    static public class OverrideDefaultValue extends Exception {
        private static final long serialVersionUID = 1965993953899572949L;
    }
    static public class OverrideValueType extends Exception {
        private static final long serialVersionUID = -7375865139493740998L;
    }
    static public class OverrideRepository extends Exception {
        private static final long serialVersionUID = -8077225949167364177L;
    }
    static public class HaveSuchName extends Exception {
        private static final long serialVersionUID = -5248825722401579070L;
    }
    
    @Override
    public void update (Observable o, Object arg) {
        if ( o instanceof GenericOption && arg instanceof GenericOption ) {
            DataContainer firstContainer = ((GenericOption) o).getContainer();
            DataContainer secondContainer = ((GenericOption) arg).getContainer();

            Set<GenericOption> firstSet = containers.get(firstContainer);
            Set<GenericOption> secondSet = containers.get(secondContainer);
            
            if ( firstSet != null && secondSet != null ) {
                firstSet.addAll(secondSet);
                for ( GenericOption opt : secondSet )
                    opt.setContainer(firstContainer);
                containers.remove(secondContainer);
            }
        }
    }
    
}
