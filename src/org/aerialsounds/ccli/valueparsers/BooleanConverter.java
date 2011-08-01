


package org.aerialsounds.ccli.valueparsers;

import java.text.Collator;



public class BooleanConverter {


    protected class CannotConvert
        extends RuntimeException {


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
    final static public byte      NUMERICAL_SIGN_TRUE  = 1;
    final static public byte      NUMERICAL_SIGN_FALSE = 0;

    final static public String TRUE = String.valueOf(NUMERICAL_SIGN_TRUE);
    final static public String FALSE = String.valueOf(NUMERICAL_SIGN_FALSE);

    final static public Collator comparator = Collator.getInstance();

    static {
        comparator.setStrength(Collator.PRIMARY);
    }


    final boolean convert (final String value) throws CannotConvert {
        if ( interpretAsString(value, positive) )
            return true;
        else if ( interpretAsString(value, negative) ) return false;

        ValuePair result = interpretAsNumber(value);
        if ( result.converted ) return result.value;

        throw new CannotConvert();
    }


    final private ValuePair interpretAsNumber (final String value) {
        try {
            byte result = Byte.valueOf(value);

            if ( result == NUMERICAL_SIGN_TRUE )
                return new ValuePair(true, true);
            else if ( result == NUMERICAL_SIGN_FALSE ) return new ValuePair(false, true);
        }
        catch ( Exception e ) {

        }

        return new ValuePair(false, false);
    }


    final private boolean interpretAsString (final String value, final String[] values) {
        for ( String s : values )
            if ( comparator.compare(value, s) == 0 )
                return true;

        return false;
    }

}
