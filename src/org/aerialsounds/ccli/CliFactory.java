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



package org.aerialsounds.ccli;



import static org.aerialsounds.ccli.options.AbstractOption.DataIsNotValid;

import org.aerialsounds.ccli.datacontainer.DataContainer;
import org.aerialsounds.ccli.options.LongOption;
import org.aerialsounds.ccli.options.NumericalOption;
import org.aerialsounds.ccli.options.ParseableOption;
import org.aerialsounds.ccli.options.ShortOption;



/**
 * <p>This factory object is used by {@link CCli} repository to create {@link Option}-related
 * objects.</p>
 *
 * @see CCli
 * @see Option
 * @see org.aerialsounds.ccli.options.AbstractOption AbstractOption
 * @see org.aerialsounds.ccli.datacontainer.DataContainer DataContainer
 *
 * @since 1.0
 *
 * @author Serge Arkhipov &lt;<a href="mailto:serge@aerialsounds.org">serge@aerialsounds.org</a>&gt;
 *
 */
class CliFactory {



// ===============================================================================================================
// F I E L D S
// ===============================================================================================================



    /**
     * <p>This field is used to determine which repository is created current instance.</p>
     *
     * @since 1.0
     *
     * @author Serge Arkhipov &lt;<a href="mailto:serge@aerialsounds.org">serge@aerialsounds.org</a>&gt;
     *
     */
    private final CCli repository;



 // ===============================================================================================================
 // C O N S T R U C T O R S
 // ===============================================================================================================



    /**
     * <p>Constructor.</p>
     *
     * @param repository - CCli instance which is creating this object
     *
     * @since 1.0
     *
     * @author Serge Arkhipov &lt;<a href="mailto:serge@aerialsounds.org">serge@aerialsounds.org</a>&gt;
     *
     */
    public
    CliFactory (final CCli repository) {
        this.repository = repository;
    } /* CliFactory */



// ===============================================================================================================
// P U B L I C   M E T H O D S
// ===============================================================================================================



    /**
     * <p>This method should be used to create
     * {@link org.aerialsounds.ccli.datacontainer.DataContainer DataContainer} instances.</p>
     *
     * @param defaultValue - default value of {@link Option}
     * @param valueType - the type of associated values
     * @param help - help string for {@code Option}
     *
     * @return New instance of {@code DataContainer}.
     *
     * @see Option
     * @see ValueTypes
     * @see org.aerialsounds.ccli.datacontainer.DataContainer DataContainer
     *
     * @since 1.0
     *
     * @author Serge Arkhipov &lt;<a href="mailto:serge@aerialsounds.org">serge@aerialsounds.org</a>&gt;
     *
     */
    public DataContainer
    createDataContainer (final Object defaultValue, final ValueTypes valueType, final String help) {
        final DataContainer container = new DataContainer(repository);

        container.setDefaultValue(defaultValue);
        container.setValueType(valueType);
        container.setHelp(help);

        return container;
    } /* createDataContainer */


    /**
     * <p>This method should be used to create
     * {@link org.aerialsounds.ccli.options.ParseableOption ParseableOption} instances.</p>
     *
     * @param type - type of {@link Option}
     * @param name - short option name (e.g {@code "z"} in {@code "--z"} option)
     * @param container - {@link org.aerialsounds.ccli.datacontainer.DataContainer DataContainer}
     *                    for new {@code Option}.
     *
     * @return New instance of {@code ParseableOption}.
     *
     * @throws DataIsNotValid if option cannot be created with given parameters.
     *
     * @see Option
     * @see org.aerialsounds.ccli.options.ParseableOption ParseableOption
     * @see OptionTypes
     * @see org.aerialsounds.ccli.datacontainer.DataContainer DataContainer
     * @see DataIsNotValid
     *
     * @since 1.0
     *
     * @author Serge Arkhipov &lt;<a href="mailto:serge@aerialsounds.org">serge@aerialsounds.org</a>&gt;
     *
     */
    public ParseableOption
    createOption (final OptionTypes type, final String name, final DataContainer container)
    throws DataIsNotValid {
        if ( type == OptionTypes.SHORT && ParseableOption.isPureNumerical(name) )
            return new NumericalOption(type, name, container);

        switch ( type ) {
            case SHORT:
                return new ShortOption(type, name, container);

            default:
                return new LongOption(type, name, container);
        }
    } /* createOption */


} /* class CliFactory */

