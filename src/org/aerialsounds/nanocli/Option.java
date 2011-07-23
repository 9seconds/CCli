package org.aerialsounds.nanocli;



public interface Option {

    String getHelp();
    String getName();
    OptionTypes getType();
    Cloneable getValue();
    ValueTypes getValueType();
    
}
