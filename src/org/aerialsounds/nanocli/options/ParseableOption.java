package org.aerialsounds.nanocli.options;

import java.util.regex.Pattern;

import org.aerialsounds.nanocli.datacontainer.DataContainer;
import org.aerialsounds.nanocli.valueparsers.ValueParser;
import org.aerialsounds.nanocli.valueparsers.ValueParserFactory;



abstract class ParseableOption
    extends AbstractOption {
    
    final protected ValueParser parser;
    
    static final private Pattern numericalRegexp = Pattern.compile("\\d+");
    
    public ParseableOption (OptionTypes optionType, String name, DataContainer container) {
        super(optionType, name, container);
        parser = createParser();
    }

    public ParseableOption (OptionTypes optionType, String customPrefix, String name, DataContainer container) {
        super(optionType, customPrefix, name, container);
        parser = createParser();
    }
    
    public Object parse(String value) {
        return parser.parse(value);
    }
    
    public String getInlineValue(String option) {
        String parsedValue = ( haveInlineValue(option) )
            ? extractInlineValue(option)
            : null;
        
        return ( parsedValue == null && getValueType().isBoolean() )
            ? Boolean.TRUE.toString()
            : parsedValue;
    }
    
    abstract public boolean haveInlineValue(String option);
    abstract protected String extractInlineValue(String option);

    private ValueParser createParser() {
        return ValueParserFactory.create(getValueType());
    }
    
    static public boolean isPureNumerical(String option) {
        return numericalRegexp.matcher(option).matches();
    }
    
}
