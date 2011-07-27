package org.aerialsounds.nanocli.options;

public enum OptionTypes {

    POSIX_SHORT("-", OptionDelimeter.SPACE),
    POSIX_LONG("-", OptionDelimeter.NONE),
    POSIX_ATTACHED("-", OptionDelimeter.NONE),
    JAVA("-D", OptionDelimeter.EQUAL),
    GNU_LONG("--", OptionDelimeter.EQUAL);
    
    private String prefix;
    private OptionDelimeter delimeter;
    
    OptionTypes(String prefix, OptionDelimeter delimeter) {
        this.prefix = prefix;
        this.delimeter = delimeter;
    }
    
    public String getPrefix() {
        return prefix;
    }
    
    public OptionDelimeter getDelimeter() {
        return delimeter;
    }
    
}
