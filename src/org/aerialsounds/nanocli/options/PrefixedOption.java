package org.aerialsounds.nanocli.options;




abstract class PrefixedOption
    extends AbstractOption {
    
    final protected String optionName;

    public PrefixedOption (OptionTypes optionType, String name) {
        super(name, optionType);
        optionName = getPrefix() + name;
    }

    public String getPrefix() {
        return optionType.getPrefix();
    }
    
    public boolean isPrefixed(String option) {
        return option.startsWith(getPrefix());
    }
    
    public boolean isOption(String option) {
        return option.equals(optionName);
    }
    
    protected OptionDelimeter getDelimeter() {
        return optionType.getDelimeter();
    }

}
