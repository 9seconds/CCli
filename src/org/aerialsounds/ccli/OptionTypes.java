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



/**
 * <p>This class represents types of options.</p>
 *
 * <p>There are 3 main classes</p>
 *
 * <ul>
 *     <li>Short options,</li>
 *     <li>Long options,</li>
 *     <li>Custom options.</li>
 * </ul>
 *
 * <p>Each option classes can have its own method for inlining values. Not inline values should be set
 * as {@code "option value"} without quotes.</p>
 *
 * <p><strong>Example:</strong> {@code -z 9 -DINLINE=true --vbr-new true}.</p>
 *
 *
 *
 * <h2>Short options</h2>
 *
 * <p>Short options are options prefixed with single hypnen. They can be joined options (if their names
 * are not numbers) so their names should be 1 characters length.</p>
 *
 * <p><strong>Examples:</strong> {@code -z}, {@code -u}, {@code -9}, {@code -0}.</p>
 *
 * <p>Inline values is set in following format: {@code -oNUM}, where {@code "-o"} is option, {@code NUM} is
 * float or integer number.</p>
 *
 * <p><strong>Examples:</strong> {@code -V0}, {@code -d1.2004e-05f}.</p>
 *
 *
 *
 * <h2>Long options</h2>
 *
 * <p>Long options are options prefixed with double hypnen. They cannot be joined options.</p>
 *
 * <p><strong>Examples:</strong> {@code --zorro}, {@code --uboot}, {@code --vbr-new}.</p>
 *
 * <p>Inline values is set in following format: {@code --option=VALUE}, where {@code "--option"}
 * is option, {@code VALUE} is inlined value.</p>
 *
 * <p><strong>Examples:</strong> {@code --verbose=true}, {@code --name=serge}, {@code --quality=1}.</p>
 *
 *
 *
 * <h2>Custom options</h2>
 *
 * <p>Long options are options withoud implicit defined prefix. They cannot be joined options.
 * You must set prefix as a part of short name of option.</p>
 *
 * <p><strong>Examples:</strong> {@code -DINLINE}, {@code if}, {@code +j}.</p>
 *
 * <p>Inline values is set as well as long options'.</p>
 *
 * <p><strong>Examples:</strong> {@code --DINLINE=__inline__}, {@code if=/dev/urandom}, {@code +j=1}.</p>
 *
 * @see Option
 *
 * @since 1.0
 *
 * @author Serge Arkhipov &lt;<a href="mailto:serge@aerialsounds.org">serge@aerialsounds.org</a>&gt;
 *
 */
public enum OptionTypes {



// ===============================================================================================================
// D E F I N I T I O N S
// ===============================================================================================================



    /** Represents short options. */
    SHORT  ("-"),
    /** Represents long options. */
    LONG   ("--"),
    /** Represents custom options. */
    CUSTOM ("");



// ===============================================================================================================
// F I E L D S
// ===============================================================================================================



    /**
     * Own prefix of option type.
     *
     * @since 1.0
     *
     * @author Serge Arkhipov &lt;<a href="mailto:serge@aerialsounds.org">serge@aerialsounds.org</a>&gt;
     *
     */
    private final String prefix;



// ===============================================================================================================
// C O N S T R U C T O R S
// ===============================================================================================================



    /**
     * <p>Constructor.</p>
     *
     * <p>Initializes option type with prefix.</p>
     *
     * @param prefix - option type prefix.
     *
     * @since 1.0
     *
     * @author Serge Arkhipov &lt;<a href="mailto:serge@aerialsounds.org">serge@aerialsounds.org</a>&gt;
     *
     */
    private
    OptionTypes (final String prefix) {
        this.prefix = prefix;
    } /* OptionTypes */



// ===============================================================================================================
// P U B L I C   M E T H O D S
// ===============================================================================================================



    /**
     * <p>This method should be used to obtain implicit prefix of given option type.</p>
     *
     * @return Option type prefix.
     *
     * @since 1.0
     *
     * @author Serge Arkhipov &lt;<a href="mailto:serge@aerialsounds.org">serge@aerialsounds.org</a>&gt;
     *
     */
    final public String
    getPrefix () {
        return prefix;
    } /* getPrefix */


} /* enum OptionTypes */

