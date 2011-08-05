package org.aerialsounds.ccli.options;

import org.aerialsounds.ccli.Option;
import org.aerialsounds.ccli.OptionTypes;
import org.aerialsounds.ccli.ValueTypes;
import org.aerialsounds.ccli.datacontainer.DataContainer;
import org.aerialsounds.ccli.datacontainer.DataContainer.DataContainerException;
import org.aerialsounds.ccli.optionobservable.Observable;



abstract public class AbstractOption
    extends Observable
    implements Option {

    protected final String name;
    protected final String fullName;
    protected final OptionTypes optionType;
    protected DataContainer container;

    public AbstractOption(final OptionTypes optionType, final String name, final DataContainer container) {
        this.name = name;
        this.optionType = optionType;
        this.fullName = optionType.getPrefix() + name;

        setContainer(container);
    }

    public void setContainer(final DataContainer container) {
        this.container = container;
        registerObserver(container.getRepository());
    }

    public DataContainer getContainer() {
        return container;
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
        return ( isParsed() )
            ? container.getValue()
            : container.getDefaultValue();
    }

    public void setValue(final Object value) {
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
    public void bind (final Option other) throws CannotBind {
        if ( other instanceof AbstractOption ) {
            final AbstractOption another = (AbstractOption) other;
            if ( !equals(another) ) {
                try {
                    DataContainer.synchronize(container, another.getContainer());
                    registerObserver(container.getRepository());
                    another.registerObserver(another.container.getRepository());
                }
                catch (DataContainerException e) {
                    throw generateBindException(e);
                }
                notifyObserver(another);
            }
        } else
            throw generateBindException(new NotCompatibleClasses());
    }

    private CannotBind generateBindException (final Exception e) {
        return (CannotBind) new CannotBind().initCause(e);
    }

    public void dispose() {
        deleteObserver();
    }

    @Override
    public boolean equals (final Object obj) {
        if ( this == obj)
            return true;
        else if ( !(obj instanceof AbstractOption) )
            return false;

        return (
                ( super.equals(obj) )
             && ( container.equals(((AbstractOption) obj).getContainer()) )
        );
    }

    static public class CannotBind extends RuntimeException {
        private static final long serialVersionUID = 9035409865724452061L;
    }

    static public class NotCompatibleClasses extends RuntimeException {
        private static final long serialVersionUID = -1357939518814763047L;
    }

}
