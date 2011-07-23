package org.aerialsounds.nanocli;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;



public class CliParser {

    final protected String[] args;
    final protected String OPTION_SIGN;
    
    final static public String DEFAULT_OPTION_SIGN = "-";
    
    private boolean parsed = false;
    
    private Map<optionTypes,Map<String,Option>> options = new TreeMap<optionTypes,Map<String,Option>>();
    
    public CliParser(String[] args) {
        this(args, DEFAULT_OPTION_SIGN);
    }
    
    public CliParser(String[] args, String optionSign) {
        this.args = args;
        OPTION_SIGN = optionSign;
    }
    
    public Option createOption(String name, optionTypes type) {
        registerType(type);
        return null;
    }
    
    public boolean hasOption(String name) {
        return ( getOption(name) != null );
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
    
    public Option getOption(String name, optionTypes type) {
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
    
    private void registerType(optionTypes type) {
        if ( options.containsKey(type) )
            options.put(type, new TreeMap<String,Option>());
    }
    
}
