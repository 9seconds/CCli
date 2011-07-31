package org.aerialsounds.ccli;

import org.aerialsounds.ccli.valueparsers.ValueParser;
import org.aerialsounds.ccli.valueparsers.ValueParserFactory;




public enum ValueTypes {
    ATOMIC_BOOLEAN,
    ATOMIC_INTEGER,
    ATOMIC_LONG,
    BIG_DECIMAL,
    BIG_INTEGER,
    BOOLEAN,
    BYTE,
    CHAR,
    DOUBLE,
    FLOAT,
    INTEGER,
    LONG,
    SHORT,
    STRING;

    private boolean booleanType;
    private boolean stringType;
    private boolean intType;
    private boolean floatType;

    private ValueTypes() {
        booleanType = checkBoolean();
        stringType = checkString();
        intType = checkInt();
        floatType = checkFloat();
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
        return ValueParserFactory.create(this);
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
