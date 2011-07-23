


package org.aerialsounds.nanocli.valueparsers;



class BooleanConverter {


    protected class CannotConvert
        extends Exception {
        private static final long serialVersionUID = 5652929665240085597L;
    }


    protected class ValuePair {
        final public boolean value;
        final public boolean converted;

        public ValuePair (final boolean value, final boolean converted) {
            this.value = value;
            this.converted = converted;
        }
    }


    final static private String[] positive             = {"true", "y", "yes"};
    final static private String[] negative             = {"false", "n", "no"};
    final static private byte     NUMERICAL_SIGN_TRUE  = 1;
    final static private byte     NUMERICAL_SIGN_FALSE = 0;


    final boolean convert (final String value) throws CannotConvert {
        ValuePair result;

        result = interpretAsNumber(value);
        if ( result.converted ) return result.value;

        String s = value.toLowerCase();
        if ( interpretAsString(s, positive) )
            return true;
        else if ( interpretAsString(s, negative) ) return false;

        throw new CannotConvert();
    }


    final private ValuePair interpretAsNumber (final String value) {
        try {
            byte result = Byte.parseByte(value);
            if ( result == NUMERICAL_SIGN_TRUE )
                return new ValuePair(true, true);
            else if ( result == NUMERICAL_SIGN_FALSE ) return new ValuePair(false, true);
            return new ValuePair(false, false);
        }
        catch ( Exception e ) {
            return new ValuePair(false, false);
        }
    }


    final private boolean interpretAsString (final String value, final String[] values) {
        for ( String s : values )
            if ( value.equals(s) ) return true;
        return false;
    }

}
