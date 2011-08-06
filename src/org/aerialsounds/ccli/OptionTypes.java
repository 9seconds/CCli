


package org.aerialsounds.ccli;



public enum OptionTypes {

    SHORT  ("-"),
    LONG   ("--"),
    CUSTOM ("");


    private final String prefix;


    OptionTypes (final String prefix) {
        this.prefix = prefix;
    }


    final public String getPrefix () {
        return prefix;
    }

}
