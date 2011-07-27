package org.aerialsounds.nanocli.datacontainer;

import java.util.Collection;
import java.util.LinkedList;

import org.aerialsounds.nanocli.CliParser;
import org.aerialsounds.nanocli.ValueTypes;



public class DataContainer {
    
    static protected Collection<SyncStrategies> syncStrategies;
    static private DataContainer firstBackup;
    static private DataContainer secondBackup;
    
    static private void initBackups() {
        firstBackup = new DataContainer(null);
        secondBackup = new DataContainer(null);
    }
    
    static private void initStrategies() {
        syncStrategies = new LinkedList<SyncStrategies>();
        syncStrategies.add(new SyncValueType());
        syncStrategies.add(new SyncDefaultValue());
        syncStrategies.add(new SyncHelp());
        syncStrategies.add(new SyncValue());
        syncStrategies.add(new SyncRepository());
    }
    
    static public void synchronize(DataContainer first, DataContainer second) throws DataContainerException {
        if ( first != second ) {
            boolean successful = false;
            backup(first, second);

            try {
                for ( SyncStrategies strategy : syncStrategies )
                    strategy.sync(first, second);
                successful = true;
            }
            finally {
                if ( !successful )
                    restore(first, second);
            }   
        }
    }
    
    static private void backup(DataContainer first, DataContainer second) {
        firstBackup.setFrom(first);
        secondBackup.setFrom(second);
    }
    
    static private void restore(DataContainer first, DataContainer second) {
        first.setFrom(firstBackup);
        second.setFrom(secondBackup);
    }
    
    static {
        initBackups();
        initStrategies();
    }
    
    protected String help = null;
    protected ValueTypes valueType = null;
    protected Object value = null;
    protected Object defaultValue = null;
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
        if ( container != this ) {
            help = container.help;
            valueType = container.valueType;
            value = container.value;
            defaultValue = container.defaultValue;
            repository = container.repository;            
        }
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
    
    static public class DataContainerException extends RuntimeException {
        private static final long serialVersionUID = -3906176569968438426L;
    }
    static public class OverrideHelp extends DataContainerException {
        private static final long serialVersionUID = 760144351098670916L;
    }
    static public class OverrideValue extends DataContainerException {
        private static final long serialVersionUID = -738739296317104089L;
    }
    static public class OverrideDefaultValue extends DataContainerException {
        private static final long serialVersionUID = 1965993953899572949L;
    }
    static public class OverrideValueType extends DataContainerException {
        private static final long serialVersionUID = -7375865139493740998L;
    }
    static public class OverrideRepository extends DataContainerException {
        private static final long serialVersionUID = -8077225949167364177L;
    }
    
    
}
