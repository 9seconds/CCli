package org.aerialsounds.ccli;

import org.aerialsounds.ccli.options.AbstractOption.CannotBind;



public interface Option {

    String getHelp();
    String getName();
    String getCustomPrefix();
    String getFullName();
    OptionTypes getType();
    Object getValue();
    ValueTypes getValueType();
    boolean isParsed();
    void bind(Option other) throws CannotBind;
    
}
