package org.aerialsounds.nanocli.valueparsers;

import org.aerialsounds.nanocli.ValueTypes;



public final class ValueParserFactory {
    
    static public ValueParser create(ValueTypes type) {
        switch ( type ) {
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
                
            case INT:
                return new IntParser();
                
            case LONG:
                return new LongParser();
                
            case SHORT:
                return new ShortParser();
            
            case STRING:
                return new StringParser();
                
            case NONE:
                return new NoneParser();
                
            default:
                return null;
        }
    }

}