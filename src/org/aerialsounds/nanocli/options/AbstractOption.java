package org.aerialsounds.nanocli.options;

import java.util.Observable;

import org.aerialsounds.nanocli.Option;
import org.aerialsounds.nanocli.OptionTypes;
import org.aerialsounds.nanocli.ValueTypes;
import org.aerialsounds.nanocli.datacontainer.DataContainer;
import org.aerialsounds.nanocli.datacontainer.DataContainer.DataContainerException;



abstract public class AbstractOption
    extends Observable
    implements Option {
    
    protected String name;
    protected OptionTypes optionType;
    protected DataContainer container = null;
    
    public AbstractOption(String name, OptionTypes optionType) {
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
    public void bind (Option other) throws CannotBind {
        if ( other instanceof AbstractOption && this != other ) {
            try {
                DataContainer.synchronize(container, ((AbstractOption) other).getContainer());
            }
            catch (DataContainerException e) {
                throw generateBindException(e);
            }
            setChanged();
            notifyObservers(other);            
        } else {
            throw generateBindException(new NotCompatibleClasses());
        }
    }

    private CannotBind generateBindException (Exception e) {
        CannotBind err = new CannotBind();
        err.initCause(e);
        return err;
    }

    @Override
    public boolean equals (Object obj) {
        if ( this == obj )
            return true;
        else if ( !(obj instanceof AbstractOption) )
            return false;
        
        return container.equals(((AbstractOption) obj).getContainer());
    }
    
    static public class CannotBind extends RuntimeException {
        private static final long serialVersionUID = 9035409865724452061L;
    }
    
    static public class NotCompatibleClasses extends RuntimeException {
        private static final long serialVersionUID = -1357939518814763047L;
    }

}
