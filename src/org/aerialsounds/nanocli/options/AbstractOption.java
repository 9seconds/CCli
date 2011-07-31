package org.aerialsounds.nanocli.options;

import java.util.Observable;

import org.aerialsounds.nanocli.Option;
import org.aerialsounds.nanocli.ValueTypes;
import org.aerialsounds.nanocli.datacontainer.DataContainer;
import org.aerialsounds.nanocli.datacontainer.DataContainer.DataContainerException;



abstract public class AbstractOption
    extends Observable
    implements Option {

    protected String name;
    protected String customPrefix;
    protected String fullName;
    protected OptionTypes optionType;
    protected DataContainer container;

    static public final String STRING_INLINE_DELIMETER = "=".intern();
    static public final String DEFAULT_CUSTOM_PREFIX = "".intern();

    public AbstractOption(OptionTypes optionType, String customPrefix, String name, DataContainer container) {
        this.name = name;
        this.customPrefix = customPrefix;
        this.optionType = optionType;
        this.container = container;
        this.fullName = optionType.getPrefix() + customPrefix + name;
    }

    public void setContainer(DataContainer container) {
        this.container = container;
    }

    public DataContainer getContainer() {
        return container;
    }

    @Override
    public String getCustomPrefix() {
        return customPrefix;
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    @Override
    public String getHelp () {
        return container.getHelp();
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
        return container.getValue();
    }

    public void setValue(Object value) {
        container.setValue(value);
    }


    @Override
    public ValueTypes getValueType () {
        return container.getValueType();
    }

    @Override
    public boolean isParsed () {
        return container.isDefined();
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

    public void dispose() {
        deleteObservers();
    }

    @Override
    public boolean equals (Object obj) {
        if ( this == obj)
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
