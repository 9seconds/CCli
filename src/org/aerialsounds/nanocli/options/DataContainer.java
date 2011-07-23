package org.aerialsounds.nanocli.options;

import org.aerialsounds.nanocli.CliParser;
import org.aerialsounds.nanocli.ValueTypes;
import org.aerialsounds.nanocli.CliParser.OverrideHelp;
import org.aerialsounds.nanocli.CliParser.OverrideValue;
import org.aerialsounds.nanocli.CliParser.OverrideDefaultValue;
import org.aerialsounds.nanocli.CliParser.OverrideValueType;
import org.aerialsounds.nanocli.CliParser.OverrideRepository;



public class DataContainer {
    
    protected String help;
    protected ValueTypes valueType;
    protected Object value;
    protected Object defaultValue;
    protected CliParser repository;
    protected boolean defined = false;
    
    public DataContainer(CliParser repository) {
        this.repository = repository;
    }
    
    public String getHelp() {
        return help;
    }
    
    public ValueTypes getValueType() {
        return valueType;
    }
    
    public Object getValue() {
        return ( defined ) ? value : defaultValue;
    }
    
    public Object getDefaultValue() {
        return defaultValue;
    }
    
    public CliParser getRepository() {
        return repository;
    }
    
    public void setHelp(String help) {
        this.help = help;
    }
    
    public void setValueType(ValueTypes valueType) {
        this.valueType = valueType;
    }
    
    public void setValue(Object value) {
        this.value = value;
        defined = true;
    }
    
    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }
    
    public boolean isDefined() {
        return defined;
    }
    
    protected void setFrom(DataContainer container) {
        help = container.help;
        valueType = container.valueType;
        value = container.value;
        defaultValue = container.defaultValue;
        repository = container.repository;
    }
    
    static public void synchronize(DataContainer first, DataContainer second) throws OverrideHelp, OverrideValueType, OverrideDefaultValue, OverrideValue, OverrideRepository {
        synchronized(first) {
            synchronized(second) {
                boolean successful = false;
                
                DataContainer firstBackup = new DataContainer(first.repository);
                DataContainer secondBackup = new DataContainer(second.repository);
                firstBackup.setFrom(first);
                secondBackup.setFrom(second);
                
                try {
                    syncHelp(first, second);
                    syncValueType(first, second);
                    syncDefautValue(first, second);
                    syncValue(first, second);
                    syncRepository(first, second);                    
                    successful = true;
                }
                finally {
                    if ( !successful ) {
                        first.setFrom(firstBackup);
                        second.setFrom(secondBackup);
                    }
                }
            }
        }
    }


    static protected void syncHelp(DataContainer first, DataContainer second) throws OverrideHelp {
        if ( first.help == null )
            first.help = second.help;
        else if ( second.help == null )
            second.help = first.help;
        else if ( !first.help.equals(second.help) )
            throw new OverrideHelp();
    }
    
    static protected void syncValueType(DataContainer first, DataContainer second) throws OverrideValueType {
        if ( first.valueType == null )
            first.valueType = second.valueType;
        else if ( second.valueType == null )
            second.valueType = first.valueType;
        else if ( !first.valueType.equals(second.valueType) )
            throw new OverrideValueType();
    }

    private static void syncDefautValue (DataContainer first, DataContainer second) throws OverrideDefaultValue {
        if ( first.defaultValue == null )
            first.defaultValue = second.defaultValue;
        else if ( second.defaultValue == null )
            second.defaultValue = first.defaultValue;
        else if ( !first.defaultValue.equals(second.defaultValue) )
            throw new OverrideDefaultValue();
    }
    
    private static void syncValue (DataContainer first, DataContainer second) throws OverrideValue {
        if ( first.value == null )
            first.value = second.value;
        else if ( second.value == null )
            second.value = first.value;
        else if ( !first.value.equals(second.value) )
            throw new OverrideValue();
    }

    private static void syncRepository (DataContainer first, DataContainer second) throws OverrideRepository {
        if ( first.repository == null )
            first.repository = second.repository;
        else if ( second.repository == null )
            second.repository = first.repository;
        else if ( !first.repository.equals(second.repository) )
            throw new OverrideRepository();
    }

    @Override
    public boolean equals (Object obj) {
        if ( this == obj )
            return true;
        else if ( getClass() != obj.getClass() )
            return false;
        
        DataContainer other = (DataContainer) obj;
        return ( 
               help.equals(other.help)
            && valueType.equals(other.valueType)
            && value.equals(other.value)
            && defaultValue.equals(other.defaultValue)
            && repository.equals(other.repository)
        );
    }
    
    
}
