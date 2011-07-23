package org.aerialsounds.nanocli;

import org.aerialsounds.nanocli.CliParser.OverrideDefaultValue;
import org.aerialsounds.nanocli.CliParser.OverrideHelp;
import org.aerialsounds.nanocli.CliParser.OverrideRepository;
import org.aerialsounds.nanocli.CliParser.OverrideValue;
import org.aerialsounds.nanocli.CliParser.OverrideValueType;



public interface Option {

    String getHelp();
    String getName();
    OptionTypes getType();
    Object getValue();
    ValueTypes getValueType();
    boolean isParsed();
    void bind(Option other) throws OverrideHelp, OverrideValueType, OverrideDefaultValue, OverrideValue, OverrideRepository;
    
}
