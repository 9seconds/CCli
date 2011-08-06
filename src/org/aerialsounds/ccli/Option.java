


package org.aerialsounds.ccli;



import org.aerialsounds.ccli.options.AbstractOption.CannotBind;



public interface Option {


    void bind (final Option other) throws CannotBind;


    String getFullName ();


    String getHelp ();


    String getName ();


    OptionTypes getType ();


    Object getValue ();


    ValueTypes getValueType ();


    boolean isParsed ();

}
