


package org.aerialsounds.ccli;



import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import org.aerialsounds.ccli.options.AbstractOption;



public class CliHelpGenerator {


    protected final StringBuilder builder = new StringBuilder();
    protected       String[]      names;


    final public String generate (final Collection<Set<AbstractOption>> values) {
        for ( Set<AbstractOption> set : values )
            generateForSet(set);

        return builder.toString();
    }


    private void generateForSet (final Set<AbstractOption> set) {
        before();
        retrieveOptionNames(set);
        generateOptionList();
        generateHelp(set);
        after();
    }

    protected void after () {

    }


    protected void before () {

    }


    protected void generateHelp (final Set<AbstractOption> set) {
        if ( !set.isEmpty() ) {
            builder.append("\n    ");
            builder.append(set.iterator().next().getFullName());
            builder.append("\n");
        }
    }


    protected void generateOptionList () {
        for ( int i = 0; i < names.length; ++i ) {
            if ( i > 0 )
                builder.append(", ");
            builder.append(names[i]);
        }
    }


    private void retrieveOptionNames (final Set<AbstractOption> set) {
        names = new String[set.size()];

        int i = 0;
        for ( AbstractOption o : set )
            names[i++] = o.getFullName();

        Arrays.sort(names);
    }
}
