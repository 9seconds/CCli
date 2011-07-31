package org.aerialsounds.ccli;

public enum OptionTypes {

    SHORT("-"),
    LONG("--"),
    CUSTOM("");
    
    private String prefix;
    
    OptionTypes(String prefix) {
        this.prefix = prefix;
    }
    
    public String getPrefix() {
        return prefix;
    }
    
}
