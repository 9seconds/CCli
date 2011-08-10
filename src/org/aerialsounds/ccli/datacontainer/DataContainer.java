/*
 * Copyright (C) 2011 by Sergey Arkhipov <serge@aerialsounds.org>
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



package org.aerialsounds.ccli.datacontainer;



import java.util.Collection;
import java.util.LinkedList;

import org.aerialsounds.ccli.CCli;
import org.aerialsounds.ccli.ValueTypes;



public class DataContainer
    implements Cloneable {



// ===============================================================================================================
// E X C E P T I O N S
// ===============================================================================================================



    static public class DataContainerException
        extends RuntimeException {
        private static final long serialVersionUID = -3906176569968438426L;
    } /* class DataContainerException */


    static public class OverrideDefaultValue
        extends DataContainerException {
        private static final long serialVersionUID = 1965993953899572949L;
    } /* class OverrideDefaultValue */


    static public class OverrideHelp
        extends DataContainerException {
        private static final long serialVersionUID = 760144351098670916L;
    } /* class OverrideHelp */


    static public class OverrideRepository
        extends DataContainerException {
        private static final long serialVersionUID = -8077225949167364177L;
    } /* class OverrideRepository */


    static public class OverrideValue
        extends DataContainerException {
        private static final long serialVersionUID = -738739296317104089L;
    } /* class OverrideValue */


    static public class OverrideValueType
        extends DataContainerException {
        private static final long serialVersionUID = -7375865139493740998L;
    } /* class OverrideValueType */



// ===============================================================================================================
// S T A T I C   F I E L D S
// ===============================================================================================================



    static protected final Collection<SyncStrategies> syncStrategies;
    static private   final DataContainer              firstBackup;
    static private   final DataContainer              secondBackup;



// ===============================================================================================================
// S T A T I C   I N I T I A L I Z A T O R
// ===============================================================================================================



    static {
        firstBackup  = new DataContainer();
        secondBackup = new DataContainer();

        syncStrategies = new LinkedList<SyncStrategies>();
        syncStrategies.add(new SyncValueType());
        syncStrategies.add(new SyncDefaultValue());
        syncStrategies.add(new SyncHelp());
        syncStrategies.add(new SyncValue());
        syncStrategies.add(new SyncRepository());
        syncStrategies.add(new SyncDefined());
    } /* static */



// ===============================================================================================================
// S T A T I C   M E T H O D S
// ===============================================================================================================



    static public void
    synchronize (final DataContainer first, final DataContainer second)
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
    } /* synchronize */


    static protected boolean
    isFieldsEqual (final Object one, final Object another) {
        return (
               one == another
            || one == null && another == null
            || one != null && another != null && one.equals(another)
        );
    } /* isFieldsEqual */


    static private void
    backup (final DataContainer first, final DataContainer second) {
        firstBackup.setFrom(first);
        secondBackup.setFrom(second);
    } /* backup */


    static private void
    rollback (final DataContainer first, final DataContainer second) {
        first.setFrom(firstBackup);
        second.setFrom(secondBackup);
    } /* rollback */



// ===============================================================================================================
// F I E L D S
// ===============================================================================================================



    protected String     help;
    protected ValueTypes valueType;
    protected Object     value;
    protected Object     defaultValue;
    protected CCli       repository;
    protected boolean    defined;



// ===============================================================================================================
// C O N S T R U C T O R S
// ===============================================================================================================



    public
    DataContainer () {
        this(null);
    } /* DataContainer */


    public
    DataContainer (final CCli repository) {
        this.repository = repository;
    } /* DataContainer */



// ===============================================================================================================
// P U B L I C   M E T H O D S
// ===============================================================================================================



    final public CCli
    getRepository () {
        return repository;
    } /* getRepository */


    final public Object
    getDefaultValue () {
        return defaultValue;
    } /* getDefaultValue */


    public void
    setDefaultValue (final Object defaultValue) {
        this.defaultValue = defaultValue;
    } /* setDefaultValue */


    final public String
    getHelp () {
        return help;
    } /* getHelp */


    public void
    setHelp (final String help) {
        this.help = help;
    } /* setHelp */


    final public Object
    getValue () {
        return value;
    } /* getValue */


    public void
    setValue (final Object value) {
        this.value = value;
        defined = value != null;
    } /* setValue */


    final public ValueTypes
    getValueType () {
        return valueType;
    } /* getValueType */


    public void
    setValueType (final ValueTypes valueType) {
        this.valueType = valueType;
    } /* setValueType */


    final public boolean
    isDefined () {
        return defined;
    } /* isDefined */


    public boolean
    isConsistent () {
        final boolean correctValueType    = (valueType != null);
        final boolean correctDefaultValue = (
            correctValueType && defaultValue != null && valueType.isInstancedBy(defaultValue)
        );
        final boolean correctValue        = (
            correctValueType && ( value == null || value != null && valueType.isInstancedBy(value) )
        );

        return (
               help != null
            && repository != null
            && correctDefaultValue
            && correctValue
            && ( value == null ^ defined )
        );
    } /* isConsistent */


    public void
    dispose () {
        repository = null;
    } /* dispose */


    final public void
    dropDefined () {
        value   = null;
        defined = false;
    } /* dropDefined */


    @Override
    public boolean
    equals (final Object obj) {
        if ( this == obj )
            return true;
        else if ( getClass() != obj.getClass() )
            return false;

        final DataContainer other = (DataContainer) obj;
        return (
               isFieldsEqual(help,         other.help)
            && isFieldsEqual(valueType,    other.valueType)
            && isFieldsEqual(value,        other.value)
            && isFieldsEqual(defaultValue, other.defaultValue)
            && isFieldsEqual(repository,   other.repository)
        );
    } /* equals */


    @Override
    public Object
    clone () {
        DataContainer container = new DataContainer();
        container.setFrom(this);
        return container;
    } /* clone */



// ===============================================================================================================
// P R O T E C T E D   M E T H O D S
// ===============================================================================================================



    protected void
    setFrom (final DataContainer container) {
        if ( container != this ) {
            help         = container.help;
            valueType    = container.valueType;
            value        = container.value;
            defaultValue = container.defaultValue;
            repository   = container.repository;
            defined      = container.defined;
        }
    } /* setFrom */


} /* class DataContainer */

