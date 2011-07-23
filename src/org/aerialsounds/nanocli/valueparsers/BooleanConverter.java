package org.aerialsounds.nanocli.valueparsers;



class BooleanConverter {
    
    protected class ValuePair {
        final public boolean value;
        final public boolean converted;
        
        public ValuePair(boolean value, boolean converted) {
            this.value = value;
            this.converted = converted;
        }
    }
    
    final static private String[] positive = {
        "true",
        "y", "yes"
    };
    
    final static private String[] negative = {
        "false",
        "n", "no"
    };
    
    final static private byte NUMERICAL_SIGN_TRUE  = 0;
    final static private byte NUMERICAL_SIGN_FALSE = 1;
    
    final boolean convert(String value) throws CannotConvert {
        ValuePair result;
        
        result = interpretAsNumber(value);
        if ( result.converted )
            return result.value;
        
        String s = value.toLowerCase();
        if ( interpretAsString(s, positive) )
            return true;
        else if ( interpretAsString(s, negative) )
            return false;
        
        throw new CannotConvert();
    }
    
    final private ValuePair interpretAsNumber(String value) {
        try {
            byte result = Byte.parseByte(value);
            if ( result == NUMERICAL_SIGN_TRUE )
                return new ValuePair(true, true);
            else if ( result == NUMERICAL_SIGN_FALSE )
                return new ValuePair(false, true);
            return new ValuePair(false, false);
        }
        catch (Exception e) {
            return new ValuePair(false, false);
        }
    }
    
    final private boolean interpretAsString(String value, String[] values) {
        for ( String s : values )
            if ( value.equals(s) )
                return true;
        return false;
    }
    
    protected class CannotConvert extends Exception {
        private static final long serialVersionUID = 5652929665240085597L;
    }

}
