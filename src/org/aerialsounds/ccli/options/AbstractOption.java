


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


    static public class DataIsNotValid
        extends RuntimeException {
        private static final long serialVersionUID = -3486882571119501655L;
    }

    static public class CannotBind
        extends RuntimeException {
        private static final long serialVersionUID = 9035409865724452061L;
    }


    static public class NotCompatibleClasses
        extends RuntimeException {
        private static final long serialVersionUID = -1357939518814763047L;
    }


    protected final String        name;
    protected final String        fullName;
    protected final OptionTypes   optionType;
    protected       DataContainer container;
    protected       DataContainer backuped;


    public AbstractOption (final OptionTypes optionType, final String name, final DataContainer container)
        throws DataIsNotValid {
        this.name       = name;
        this.optionType = optionType;
        fullName        = optionType.getPrefix() + name;

        setContainer(container);

        if ( !isDataValid() )
            throw new DataIsNotValid();
    }


    protected boolean isDataValid () {
        return name != null && name != "" && container != null;
    }


    @Override
    final public void bind (final Option other) throws CannotBind {
        if ( other instanceof AbstractOption ) {
            if ( !equals(other) ) {
                final AbstractOption another = (AbstractOption)other;
                try {
                    DataContainer.synchronize(container, another.getContainer());
                    registerObserver(container.getRepository());
                    another.registerObserver(another.container.getRepository());
                }
                catch ( DataContainerException e ) {
                    throw generateBindException(e);
                }
                notifyObserver(another);
            }
        } else
            throw generateBindException(new NotCompatibleClasses());
    }


    final public void dispose () {
        deleteObserver();
        DataContainer disposed = (DataContainer) container.clone();
        disposed.dispose();
        container = disposed;
    }


    @Override
    public boolean equals (final Object obj) {
        if ( this == obj )
            return true;
        else if ( !(obj instanceof AbstractOption) )
            return false;

        return (
            super.equals(obj) && container == ((AbstractOption)obj).getContainer()
        );
    }


    final private CannotBind generateBindException (final Exception e) {
        return (CannotBind) new CannotBind().initCause(e);
    }


    final public DataContainer getContainer () {
        return container;
    }


    @Override
    final public String getFullName () {
        return fullName;
    }


    @Override
    final public String getHelp () {
        return container.getHelp();
    }


    @Override
    final public String getName () {
        return name;
    }


    @Override
    final public OptionTypes getType () {
        return optionType;
    }


    @Override
    final public Object getValue () {
        return ( isParsed() )
            ? container.getValue()
            : container.getDefaultValue();
    }


    @Override
    final public ValueTypes getValueType () {
        return container.getValueType();
    }


    @Override
    final public boolean isParsed () {
        return container.isDefined();
    }


    final public void setContainer (final DataContainer container) {
        this.container = container;
        registerObserver(container.getRepository());
    }


    final public void setValue (final Object value) {
        container.setValue(value);
    }

}
