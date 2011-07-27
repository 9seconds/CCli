package org.aerialsounds.nanocli;

import org.aerialsounds.nanocli.options.OptionTypes;
import org.aerialsounds.nanocli.options.AbstractOption.CannotBind;



public interface Option {

    String getHelp();
    String getName();
    OptionTypes getType();
    Object getValue();
    ValueTypes getValueType();
    boolean isParsed();
    void bind(Option other) throws CannotBind;
    
}
