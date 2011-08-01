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




public enum ValueTypes {
    ATOMIC_BOOLEAN(AtomicBoolean.class),
    ATOMIC_INTEGER(AtomicInteger.class),
    ATOMIC_LONG(AtomicLong.class),
    BIG_DECIMAL(BigDecimal.class),
    BIG_INTEGER(BigInteger.class),
    BOOLEAN(Boolean.class),
    BYTE(Byte.class),
    CHAR(Character.class),
    DOUBLE(Double.class),
    FLOAT(Float.class),
    INTEGER(Integer.class),
    LONG(Long.class),
    SHORT(Short.class),
    STRING(String.class);

    private boolean booleanType;
    private boolean stringType;
    private boolean intType;
    private boolean floatType;
    private Class<?> type;

    private ValueTypes(Class<?> type) {
        booleanType = checkBoolean();
        stringType = checkString();
        intType = checkInt();
        floatType = checkFloat();
        this.type = type;
    }

    public Class<?> getType() {
        return type;
    }

    public boolean isBoolean() {
        return booleanType;
    }

    public boolean isString() {
        return stringType;
    }

    public boolean isInt() {
        return intType;
    }

    public boolean isFloat() {
        return floatType;
    }

    public boolean isNumber() {
        return ( intType || floatType );
    }

    public ValueParser createParser() {
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
                return new StringParser();

            default:
                return null;
        }
    }

    private boolean checkBoolean() {
        return ( this == BOOLEAN || this == ATOMIC_BOOLEAN );
    }

    private boolean checkString() {
        return ( this == STRING || this == CHAR );
    }

    private boolean checkInt() {
        return ( this == INTEGER || this == BYTE || this == SHORT || this == LONG || this == BIG_INTEGER || this == ATOMIC_INTEGER || this == ATOMIC_LONG );
    }

    private boolean checkFloat() {
        return ( this == DOUBLE || this == FLOAT || this == BIG_DECIMAL );
    }
}
