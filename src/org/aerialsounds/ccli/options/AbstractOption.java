/*
 * Copyright (C) 2011 by Serge Arkhipov <serge@aerialsounds.org>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */



package org.aerialsounds.ccli.options;



import static org.aerialsounds.ccli.datacontainer.DataContainer.DataContainerException;

import org.aerialsounds.ccli.CannotBind;
import org.aerialsounds.ccli.Option;
import org.aerialsounds.ccli.OptionTypes;
import org.aerialsounds.ccli.ValueTypes;
import org.aerialsounds.ccli.datacontainer.DataContainer;
import org.aerialsounds.ccli.optionobservable.Observable;



abstract public class AbstractOption
    extends Observable
    implements Option {



// ===============================================================================================================
// E X C E P T I O N S
// ===============================================================================================================



    static public class DataIsNotValid
        extends RuntimeException {
        private static final long serialVersionUID = -3486882571119501655L;
    } /* class DataIsNotValid */


    static public class NotCompatibleClasses
        extends RuntimeException {
        private static final long serialVersionUID = -1357939518814763047L;
    } /* class NotCompatibleClasses */



// ===============================================================================================================
// F I E L D S
// ===============================================================================================================



    protected final String        name;
    protected final String        fullName;
    protected final OptionTypes   optionType;
    protected       DataContainer container;



// ===============================================================================================================
// C O N S T R U C T O R S
// ===============================================================================================================



    public
    AbstractOption (final OptionTypes optionType, final String name, final DataContainer container)
    throws DataIsNotValid {
        this.name       = name;
        this.optionType = optionType;
        fullName        = optionType.getPrefix() + name;

        setContainer(container);

        if ( !isDataValid() )
            throw new DataIsNotValid();
    } /* AbstractOption */



// ===============================================================================================================
// P U B L I C   M E T H O D S
// ===============================================================================================================



    final public DataContainer
    getContainer () {
        return container;
    } /* getContainer */


    final public void
    setContainer (final DataContainer container) {
        this.container = container;
        registerObserver(container.getRepository());
    } /* setContainer */


    @Override
    final public String
    getHelp () {
        return container.getHelp();
    } /* getHelp */


    @Override
    final public String
    getName () {
        return name;
    } /* getName */


    @Override
    final public String
    getFullName () {
        return fullName;
    } /* getFullName */


    @Override
    final public OptionTypes
    getType () {
        return optionType;
    } /* getType */


    @Override
    final public Object
    getValue () {
        return ( isParsed() )
            ? container.getValue()
            : container.getDefaultValue();
    } /* getValue */


    final public void
    setValue (final Object value) {
        container.setValue(value);
    } /* setValue */


    @Override
    final public ValueTypes
    getValueType () {
        return container.getValueType();
    } /* getValueType */


    @Override
    final public boolean
    isParsed () {
        return container.isDefined();
    } /* isParsed */


    final public void
    dispose () {
        deleteObserver();
        DataContainer disposed = (DataContainer) container.clone();
        disposed.dispose();
        container = disposed;
    } /* dispose */


    @Override
    final public void
    bind (final Option other)
    throws CannotBind {
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
            } // if ( !equals(other) )
        } else // if ( other instanceof AbstractOption )
            throw generateBindException(new NotCompatibleClasses());
    } /* bind */


    @Override
    public boolean
    equals (final Object obj) {
        if ( this == obj )
            return true;
        else if ( !(obj instanceof AbstractOption) )
            return false;

        return super.equals(obj) && container == ((AbstractOption)obj).getContainer();
    } /* equals */



// ===============================================================================================================
// P R O T E C T E D   M E T H O D S
// ===============================================================================================================



    protected boolean
    isDataValid () {
        return name != null && name != "" && container != null && container.isConsistent();
    } /* isDataValid */


    private CannotBind
    generateBindException (final Exception e) {
        return (CannotBind) new CannotBind().initCause(e);
    } /* generateBindException */


} /* class AbstractOption */

