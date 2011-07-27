package org.aerialsounds.nanocli.options;

import org.aerialsounds.nanocli.datacontainer.DataContainer;
import org.aerialsounds.nanocli.valueparsers.ValueParser;
import org.aerialsounds.nanocli.valueparsers.ValueParserFactory;



class ParseableOption
    extends PrefixedOption {
    
    protected ValueParser parser = null;

    public ParseableOption (OptionTypes optionType, String name) {
        super(optionType, name);
    }

    @Override
    public void setContainer (DataContainer container) {
        super.setContainer(container);
        parser = ValueParserFactory.create(getValueType());
    }
    
    public void parseValue(String value) {
        if ( parser != null ) {
            if ( value != null )
                container.setValue(parser.parse(value));
            else
                container.setValue(container.getDefaultValue());
        }
        // TODO parser is not defined
    }

}
