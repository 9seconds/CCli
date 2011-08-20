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



import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.aerialsounds.ccli.valueparsers.AtomicBooleanParser;
import org.aerialsounds.ccli.valueparsers.AtomicIntegerParser;
import org.aerialsounds.ccli.valueparsers.AtomicLongParser;
import org.aerialsounds.ccli.valueparsers.BigDecimalParser;
import org.aerialsounds.ccli.valueparsers.BigIntegerParser;
import org.aerialsounds.ccli.valueparsers.BooleanParser;
import org.aerialsounds.ccli.valueparsers.ByteParser;
import org.aerialsounds.ccli.valueparsers.CharParser;
import org.aerialsounds.ccli.valueparsers.DoubleParser;
import org.aerialsounds.ccli.valueparsers.FloatParser;
import org.aerialsounds.ccli.valueparsers.IntegerParser;
import org.aerialsounds.ccli.valueparsers.LongParser;
import org.aerialsounds.ccli.valueparsers.ShortParser;
import org.aerialsounds.ccli.valueparsers.StringParser;
import org.aerialsounds.ccli.valueparsers.ValueParser;



/**
 * <p>This enumeration represents different value types which can be parsed with {@link CCli} class.
 * Each enumeration value encapsulates creating of parser for such types and some additional
 * features.</p>
 *
 * <p>You should use this enumeration to define which type of values should be associated
 * with {@link Option}.</p>
 *
 * @see CCli
 * @see Option
 *
 * @since 1.0
 *
 * @author Serge Arkhipov &lt;<a href="mailto:serge@aerialsounds.org">serge@aerialsounds.org</a>&gt;
 *
 */
public enum ValueTypes {



// ===============================================================================================================
// D E F I N I T I O N S
// ===============================================================================================================



    /**
     * <p>This value types should be used if {@link Option} associated with AtomicBoolean type.</p>
     *
     * @see Option
     *
     * @since 1.0
     *
     * @author Serge Arkhipov &lt;<a href="mailto:serge@aerialsounds.org">serge@aerialsounds.org</a>&gt;
     *
     */
    ATOMIC_BOOLEAN(AtomicBoolean.class),

    /**
     * <p>This value types should be used if {@link Option} associated with AtomicInteger type.</p>
     *
     * @see Option
     *
     * @since 1.0
     *
     * @author Serge Arkhipov &lt;<a href="mailto:serge@aerialsounds.org">serge@aerialsounds.org</a>&gt;
     *
     */
    ATOMIC_INTEGER(AtomicInteger.class),

    /**
     * <p>This value types should be used if {@link Option} associated with AtomicLong type.</p>
     *
     * @see Option
     *
     * @since 1.0
     *
     * @author Serge Arkhipov &lt;<a href="mailto:serge@aerialsounds.org">serge@aerialsounds.org</a>&gt;
     *
     */
    ATOMIC_LONG(AtomicLong.class),

    /**
     * <p>This value types should be used if {@link Option} associated with BigDecimal type.</p>
     *
     * @see Option
     *
     * @since 1.0
     *
     * @author Serge Arkhipov &lt;<a href="mailto:serge@aerialsounds.org">serge@aerialsounds.org</a>&gt;
     *
     */
    BIG_DECIMAL(BigDecimal.class),

    /**
     * <p>This value types should be used if {@link Option} associated with BigInteger type.</p>
     *
     * @see Option
     *
     * @since 1.0
     *
     * @author Serge Arkhipov &lt;<a href="mailto:serge@aerialsounds.org">serge@aerialsounds.org</a>&gt;
     *
     */
    BIG_INTEGER(BigInteger.class),

    /**
     * <p>This value types should be used if {@link Option} associated with Boolean type.</p>
     *
     * @see Option
     *
     * @since 1.0
     *
     * @author Serge Arkhipov &lt;<a href="mailto:serge@aerialsounds.org">serge@aerialsounds.org</a>&gt;
     *
     */
    BOOLEAN(Boolean.class),

    /**
     * <p>This value types should be used if {@link Option} associated with Byte type.</p>
     *
     * @see Option
     *
     * @since 1.0
     *
     * @author Serge Arkhipov &lt;<a href="mailto:serge@aerialsounds.org">serge@aerialsounds.org</a>&gt;
     *
     */
    BYTE(Byte.class),

    /**
     * <p>This value types should be used if {@link Option} associated with Character type.</p>
     *
     * @see Option
     *
     * @since 1.0
     *
     * @author Serge Arkhipov &lt;<a href="mailto:serge@aerialsounds.org">serge@aerialsounds.org</a>&gt;
     *
     */
    CHAR(Character.class),

    /**
     * <p>This value types should be used if {@link Option} associated with Double type.</p>
     *
     * @see Option
     *
     * @since 1.0
     *
     * @author Serge Arkhipov &lt;<a href="mailto:serge@aerialsounds.org">serge@aerialsounds.org</a>&gt;
     *
     */
    DOUBLE(Double.class),

    /**
     * <p>This value types should be used if {@link Option} associated with Float type.</p>
     *
     * @see Option
     *
     * @since 1.0
     *
     * @author Serge Arkhipov &lt;<a href="mailto:serge@aerialsounds.org">serge@aerialsounds.org</a>&gt;
     *
     */
    FLOAT(Float.class),

    /**
     * <p>This value types should be used if {@link Option} associated with Integer type.</p>
     *
     * @see Option
     *
     * @since 1.0
     *
     * @author Serge Arkhipov &lt;<a href="mailto:serge@aerialsounds.org">serge@aerialsounds.org</a>&gt;
     *
     */
    INTEGER(Integer.class),

    /**
     * <p>This value types should be used if {@link Option} associated with Long type.</p>
     *
     * @see Option
     *
     * @since 1.0
     *
     * @author Serge Arkhipov &lt;<a href="mailto:serge@aerialsounds.org">serge@aerialsounds.org</a>&gt;
     *
     */
    LONG(Long.class),

    /**
     * <p>This value types should be used if {@link Option} associated with Short type.</p>
     *
     * @see Option
     *
     * @since 1.0
     *
     * @author Serge Arkhipov &lt;<a href="mailto:serge@aerialsounds.org">serge@aerialsounds.org</a>&gt;
     *
     */
    SHORT(Short.class),

    /**
     * <p>This value types should be used if {@link Option} associated with String type.</p>
     *
     * @see Option
     *
     * @since 1.0
     *
     * @author Serge Arkhipov &lt;<a href="mailto:serge@aerialsounds.org">serge@aerialsounds.org</a>&gt;
     *
     */
    STRING(String.class);



// ===============================================================================================================
// F I E L D S
// ===============================================================================================================



    /**
     * <p>This field stores original class of associated type.</p>
     *
     * @since 1.0
     *
     * @author Serge Arkhipov &lt;<a href="mailto:serge@aerialsounds.org">serge@aerialsounds.org</a>&gt;
     *
     */
    private final Class<?> type;



// ===============================================================================================================
// C O N S T R U C T O R S
// ===============================================================================================================



    /**
     * <p>This field stores original class of associated type.</p>
     *
     * @since 1.0
     *
     * @author Serge Arkhipov &lt;<a href="mailto:serge@aerialsounds.org">serge@aerialsounds.org</a>&gt;
     *
     */
    private ValueTypes (final Class<?> type) {
        this.type = type;
    }



// ===============================================================================================================
// P U B L I C   M E T H O D S
// ===============================================================================================================



    /**
     * <p>This method should be used to create
     * {@link org.aerialsounds.ccli.valueparsers.ValueParser ValueParser} which can parse string
     * value into object of associated type.</p>
     *
     * @return {@code ValueParser} for associated type.
     *
     * @see org.aerialsounds.ccli.valueparsers.ValueParser ValueParser
     *
     * @since 1.0
     *
     * @author Serge Arkhipov &lt;<a href="mailto:serge@aerialsounds.org">serge@aerialsounds.org</a>&gt;
     *
     */
    public ValueParser
    createParser () {
        switch ( this ) {
            case ATOMIC_BOOLEAN:
                return new AtomicBooleanParser();

            case ATOMIC_INTEGER:
                return new AtomicIntegerParser();

            case ATOMIC_LONG:
                return new AtomicLongParser();

            case BIG_DECIMAL:
                return new BigDecimalParser();

            case BIG_INTEGER:
                return new BigIntegerParser();

            case BOOLEAN:
                return new BooleanParser();

            case BYTE:
                return new ByteParser();

            case CHAR:
                return new CharParser();

            case DOUBLE:
                return new DoubleParser();

            case FLOAT:
                return new FloatParser();

            case INTEGER:
                return new IntegerParser();

            case LONG:
                return new LongParser();

            case SHORT:
                return new ShortParser();

            case STRING:
            default:
                return new StringParser();
        } // switch ( this )
    } /* createParser */


    /**
     * <p>This method should be check that given object is instance (or child) of
     * associated type.</p>
     *
     * @param o - object which is need to be checked.
     *
     * @return {@code true} if instance, {@code false} otherwise.
     *
     * @since 1.0
     *
     * @author Serge Arkhipov &lt;<a href="mailto:serge@aerialsounds.org">serge@aerialsounds.org</a>&gt;
     *
     */
    final public boolean
    isInstancedBy (final Object o) {
        return type.isInstance(o);
    } /* isInstancedBy */


    /**
     * <p>This method should be check associated type is atomic.</p>
     *
     * @return {@code true} if atomic, {@code false} otherwise.
     *
     * @since 1.0
     *
     * @author Serge Arkhipov &lt;<a href="mailto:serge@aerialsounds.org">serge@aerialsounds.org</a>&gt;
     *
     */
    public boolean
    isAtomic () {
        return (
               this == ATOMIC_BOOLEAN
            || this == ATOMIC_INTEGER
            || this == ATOMIC_LONG
        );
    } /* isAtomic */


    /**
     * <p>This method should be check associated type is boolean.</p>
     *
     * @return {@code true} if boolean, {@code false} otherwise.
     *
     * @since 1.0
     *
     * @author Serge Arkhipov &lt;<a href="mailto:serge@aerialsounds.org">serge@aerialsounds.org</a>&gt;
     *
     */
    public boolean
    isBoolean () {
        return (
               this == BOOLEAN
            || this == ATOMIC_BOOLEAN
        );
    } /* isBoolean */


    /**
     * <p>This method should be check associated type is float.</p>
     *
     * @return {@code true} if float, {@code false} otherwise.
     *
     * @since 1.0
     *
     * @author Serge Arkhipov &lt;<a href="mailto:serge@aerialsounds.org">serge@aerialsounds.org</a>&gt;
     *
     */
    public boolean
    isFloat () {
        return (
               this == DOUBLE
            || this == FLOAT
            || this == BIG_DECIMAL
        );
    } /* isFloat */


    /**
     * <p>This method should be check associated type is integer.</p>
     *
     * @return {@code true} if integer, {@code false} otherwise.
     *
     * @since 1.0
     *
     * @author Serge Arkhipov &lt;<a href="mailto:serge@aerialsounds.org">serge@aerialsounds.org</a>&gt;
     *
     */
    public boolean
    isInt () {
        return (
               this == INTEGER
            || this == BYTE
            || this == SHORT
            || this == LONG
            || this == BIG_INTEGER
            || this == ATOMIC_INTEGER
            || this == ATOMIC_LONG
        );
    } /* isInt */


    /**
     * <p>This method should be check associated type is numerical.</p>
     *
     * @return {@code true} if numerical, {@code false} otherwise.
     *
     * @since 1.0
     *
     * @author Serge Arkhipov &lt;<a href="mailto:serge@aerialsounds.org">serge@aerialsounds.org</a>&gt;
     *
     */
    public boolean
    isNumber () {
        return ( isInt() || isFloat() );
    } /* isNumber */


    /**
     * <p>This method should be check associated type is string.</p>
     *
     * @return {@code true} if string, {@code false} otherwise.
     *
     * @since 1.0
     *
     * @author Serge Arkhipov &lt;<a href="mailto:serge@aerialsounds.org">serge@aerialsounds.org</a>&gt;
     *
     */
    public boolean
    isString () {
        return (
               this == STRING
            || this == CHAR
        );
    } /* isString */


} /* enum ValueTypes */

