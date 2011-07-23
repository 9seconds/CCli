package org.aerialsounds.nanocli.options;

import java.util.Observable;

import org.aerialsounds.nanocli.Option;
import org.aerialsounds.nanocli.OptionTypes;
import org.aerialsounds.nanocli.ValueTypes;
import org.aerialsounds.nanocli.CliParser.OverrideDefaultValue;
import org.aerialsounds.nanocli.CliParser.OverrideHelp;
import org.aerialsounds.nanocli.CliParser.OverrideRepository;
import org.aerialsounds.nanocli.CliParser.OverrideValue;
import org.aerialsounds.nanocli.CliParser.OverrideValueType;



abstract public class GenericOption
    extends Observable
    implements Option {
    
    protected String name;
    protected OptionTypes optionType;
    protected DataContainer container = null;
    
    public GenericOption(String name, OptionTypes optionType) {
        this.name = name;
        this.optionType = optionType;
    }

    public void setContainer(DataContainer container) {
        this.container = container;
    }
    
    public DataContainer getContainer() {
        return container;
    }

    @Override
    public String getHelp () {
        return ( container != null ) ? container.getHelp() : null;
    }


    @Override
    public String getName () {
        return name;
    }


    @Override
    public OptionTypes getType () {
        return optionType;
    }


    @Override
    public Object getValue () {
        return ( container != null ) ? container.getValue() : null;
    }


    @Override
    public ValueTypes getValueType () {
        return ( container != null ) ? container.getValueType() : null;
    }

    @Override
    public boolean isParsed () {
        return ( container != null ) ? container.isDefined() : false;
    }

    @Override
    public void bind (Option other) throws OverrideHelp, OverrideValueType, OverrideDefaultValue, OverrideValue, OverrideRepository {
        if ( other instanceof GenericOption && this != other ) {
            DataContainer.synchronize(container, ((GenericOption) other).getContainer());
            setChanged();
            notifyObservers(other);            
        }
    }

    @Override
    public boolean equals (Object obj) {
        if ( this == obj )
            return true;
        else if ( !(obj instanceof GenericOption) )
            return false;
        
        return container.equals(((GenericOption) obj).getContainer());
    }

}
