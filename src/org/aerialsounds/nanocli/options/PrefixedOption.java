package org.aerialsounds.nanocli.options;

import org.aerialsounds.nanocli.OptionTypes;



abstract public class PrefixedOption
    extends AbstractOption {
    
    final protected String prefix;
    final protected String optionName;

    public PrefixedOption (OptionTypes optionType, String name, String prefix) {
        super(name, optionType);
        this.prefix = prefix;
        optionName = prefix + name;
    }
    
    public String getPrefix() {
        return prefix;
    }
    
    public boolean isPrefixed(String option) {
        return option.startsWith(prefix);
    }
    
    public boolean isOption(String option) {
        return option.equals(optionName);
    }

}
