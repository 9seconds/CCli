


package org.aerialsounds.ccli.datacontainer;



import java.util.Collection;
import java.util.LinkedList;

import org.aerialsounds.ccli.CCli;
import org.aerialsounds.ccli.ValueTypes;



public class DataContainer {


    static public class DataContainerException
        extends RuntimeException {
        private static final long serialVersionUID = -3906176569968438426L;
    }


    static public class OverrideDefaultValue
        extends DataContainerException {
        private static final long serialVersionUID = 1965993953899572949L;
    }


    static public class OverrideHelp
        extends DataContainerException {
        private static final long serialVersionUID = 760144351098670916L;
    }


    static public class OverrideRepository
        extends DataContainerException {
        private static final long serialVersionUID = -8077225949167364177L;
    }


    static public class OverrideValue
        extends DataContainerException {
        private static final long serialVersionUID = -738739296317104089L;
    }


    static public class OverrideValueType
        extends DataContainerException {
        private static final long serialVersionUID = -7375865139493740998L;
    }


    static protected Collection<SyncStrategies> syncStrategies;
    static private DataContainer                firstBackup;
    static private DataContainer                secondBackup;
    static protected Object                     DEFAULT_VALUE = null;

    static {
        initBackups();
        initStrategies();
    }

    static private void backup (final DataContainer first, final DataContainer second) {
        firstBackup.setFrom(first);
        secondBackup.setFrom(second);
    }


    static private void initBackups () {
        firstBackup = new DataContainer(null);
        secondBackup = new DataContainer(null);
    }


    static private void initStrategies () {
        syncStrategies = new LinkedList<SyncStrategies>();
        syncStrategies.add(new SyncValueType());
        syncStrategies.add(new SyncDefaultValue());
        syncStrategies.add(new SyncHelp());
        syncStrategies.add(new SyncValue());
        syncStrategies.add(new SyncRepository());
        syncStrategies.add(new SyncDefined());
    }


    static protected boolean isFieldsEqual (final Object one, final Object another) {
        return ((one == another) || (one == null && another == null) || (one != null && another != null && one.equals(another)));
    }


    static private void rollback (final DataContainer first, final DataContainer second) {
        first.setFrom(firstBackup);
        second.setFrom(secondBackup);
    }


    static public void synchronize (final DataContainer first, final DataContainer second)
        throws DataContainerException {
        if ( first != second ) {
            backup(first, second);
            try {
                for ( SyncStrategies strategy : syncStrategies )
                    strategy.sync(first, second);
            }
            catch ( DataContainerException e ) {
                rollback(first, second);
                throw e;
            }
        }
    }


    protected String     help;
    protected ValueTypes valueType;
    protected Object     value        = DEFAULT_VALUE;
    protected Object     defaultValue = DEFAULT_VALUE;
    protected CCli       repository;
    protected boolean    defined;


    public DataContainer () {
        this(null);
    }


    public DataContainer (final CCli repository) {
        this.repository = repository;
    }


    public void dropDefined () {
        value = DEFAULT_VALUE;
        defined = false;
    }


    @Override
    public boolean equals (final Object obj) {
        if ( this == obj )
            return true;
        else if ( getClass() != obj.getClass() )
            return false;

        DataContainer other = (DataContainer) obj;
        return (isFieldsEqual(help, other.help) && isFieldsEqual(valueType, other.valueType) &&
            isFieldsEqual(value, other.value) && isFieldsEqual(defaultValue, other.defaultValue) && isFieldsEqual(
            repository,
            other.repository));
    }


    public Object getDefaultValue () {
        return defaultValue;
    }


    public String getHelp () {
        return help;
    }


    public CCli getRepository () {
        return repository;
    }


    public Object getValue () {
        return value;
    }


    public ValueTypes getValueType () {
        return valueType;
    }


    public boolean isConsistent () {
        boolean correctValueType = (valueType != null);
        boolean correctDefaultValue = (
               correctValueType
            && ( defaultValue != DEFAULT_VALUE )
            && valueType.isInstancedBy(defaultValue)
        );
        boolean correctValue = (
               correctValueType
            && ( ( value == DEFAULT_VALUE ) || ( value != DEFAULT_VALUE && valueType.isInstancedBy(value) ) )
        );
        boolean correctDefined = (value == DEFAULT_VALUE ^ defined);

        return (
               (help != null)
            && (repository != null)
            && correctValueType
            && correctDefaultValue
            && correctValue
            && correctDefined
        );
    }


    public boolean isDefined () {
        return defined;
    }


    public void setDefaultValue (final Object defaultValue) {
        this.defaultValue = defaultValue;
    }


    protected void setFrom (final DataContainer container) {
        if ( container != this ) {
            help = container.help;
            valueType = container.valueType;
            value = container.value;
            defaultValue = container.defaultValue;
            repository = container.repository;
            defined = container.defined;
        }
    }


    public void setHelp (final String help) {
        this.help = help;
    }


    public void setValue (final Object value) {
        this.value = value;
        defined = (value != null);
    }


    public void setValueType (final ValueTypes valueType) {
        this.valueType = valueType;
    }

}
