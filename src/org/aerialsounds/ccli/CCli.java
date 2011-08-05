package org.aerialsounds.ccli;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.aerialsounds.ccli.datacontainer.DataContainer;
import org.aerialsounds.ccli.optionobservable.Observable;
import org.aerialsounds.ccli.optionobservable.Observer;
import org.aerialsounds.ccli.options.AbstractOption;
import org.aerialsounds.ccli.options.ParseableOption;
import org.aerialsounds.ccli.options.ShortOption;
import org.aerialsounds.ccli.options.AbstractOption.CannotBind;
import org.aerialsounds.ccli.valueparsers.BooleanConverter;



public class CCli implements Observer {

    final protected String[] args;
    final protected CliFactory factory;

    protected boolean parsed = false;

    final protected Map<DataContainer,Set<AbstractOption>> containers;
    final protected LinkedList<ParseableOption> options;
    final protected Collection<String> appArguments;

    public CCli(String[] args) {
        this.args = args;
        factory = new CliFactory(this);
        containers = new HashMap<DataContainer,Set<AbstractOption>>();
        options = new LinkedList<ParseableOption>();
        appArguments = new LinkedList<String>();
    }

    public boolean isParsed() {
        return parsed;
    }

    public void parse() throws CannotParse {
        if ( args == null )
            throw new CannotParse();

        if ( !parsed ) {
            clearParsedData();

            String inlineValue = null;
            Object parsedValue = null;
            String current = null;
            ParseableOption option = null;
            for ( int i = 0; i < args.length; ++i ) {
                current = args[i];
                option = findOption(current);

                if ( option != null && !appArguments.isEmpty() )
                    interruptParsing();

                if ( option != null ) {
                    if ( option.getValueType().isBoolean() )
                        setBooleanTrue(option);

                    inlineValue = option.getInlineValue(current);
                    parsedValue = null;
                    if ( inlineValue == null )
                        if ( i < args.length - 1 ) {
                            parsedValue = option.parse(args[i+1]);
                            if ( parsedValue != null )
                                ++i;
                        } else
                            interruptParsing();
                    else
                        parsedValue = option.parse(inlineValue);

                    if ( parsedValue == null )
                        interruptParsing();
                    else
                        option.setValue(parsedValue);
                } else if ( !parseUnknownOption(current) )
                    appArguments.add(current);
            }
            if ( isContainersConsistent() )
                throw new CannotParse();

            parsed = true;
        }
    }

    private boolean isContainersConsistent () {
        final Set<DataContainer> conainersSet = containers.keySet();
        for ( DataContainer container : conainersSet )
            if ( !container.isConsistent() )
                return false;
        return true;
    }

    protected void setBooleanTrue (final ParseableOption option) {
        option.setValue(option.parse(BooleanConverter.TRUE));
    }

    protected boolean parseUnknownOption (final String current) {
        final Iterable<ParseableOption> joined = findJoinedOptions(current);
        if ( joined != null ) {
            confimJoin(joined);
            return true;
        }
        return false;
    }

    protected void confimJoin (final Iterable<ParseableOption> joined) {
        for ( ParseableOption opt : joined )
            setBooleanTrue(opt);
    }

    protected Iterable<ParseableOption> findJoinedOptions (final String current) {
        final String prefix = OptionTypes.SHORT.getPrefix();
        if ( current.startsWith(prefix) && !ShortOption.haveNumbers(current) ) {
            final char[] currentLine = current.substring(prefix.length()).toCharArray();
            final Collection<ParseableOption> list = new LinkedList<ParseableOption>();
            for ( char currentChar : currentLine ) {
                ParseableOption opt = findOption(prefix + Character.toString(currentChar));
                if ( opt != null && opt.getValueType().isBoolean() )
                    list.add(opt);
                else
                    return null;
            }
            return list;
        } else
            return null;
    }

    protected void interruptParsing() throws CannotParse {
        clearParsedData();
        throw new CannotParse();
    }

    protected void clearParsedData() {
        appArguments.clear();
        final Set<DataContainer> conainersSet = containers.keySet();
        for ( DataContainer container : conainersSet )
            container.dropDefined();
        parsed = false;
    }

    protected ParseableOption findOption(final String option) {
        final Iterator<ParseableOption> it = options.iterator();
        while ( it.hasNext() ) {
            ParseableOption entry = it.next();
            if ( entry.appropriate(option) )
                return entry;
        }
        return null;
    }

    public Iterator<String> getApplicationArguments() {
        return appArguments.iterator();
    }

    public void remove(final Option option) {
        if ( option instanceof ParseableOption ) {
            final ParseableOption removed = (ParseableOption) option;
            if ( options.contains(removed) ) {
                options.remove(removed);
                removeContainer(removed);
                removed.dispose();
            }
        }
    }

    protected void removeContainer (final AbstractOption option) {
        final DataContainer container = option.getContainer();
        final Set<AbstractOption> containerSet = containers.get(container);
        if ( containerSet != null ) {
            containerSet.remove(option);
            if ( containerSet.isEmpty() )
                containers.remove(container);
        }
    }

    protected void register (final DataContainer container, final ParseableOption opt) {
        registerContainer(container, opt);
        options.add(opt);
        parsed = false;
    }

    protected void registerContainer (final DataContainer container, final AbstractOption opt) {
        final Set<AbstractOption> containerSet = new HashSet<AbstractOption>();
        containerSet.add(opt);
        containers.put(container, containerSet);
    }

    public Option createOption(final OptionTypes type, final String name, final Object defaultValue, final ValueTypes valueType, final String help) throws CannotCreateThisOption {
        final DataContainer container = factory.createDataContainer(defaultValue, valueType, help);
        ParseableOption opt;
        try {
            opt = factory.createOption(type, name, container);
        }
        catch ( ParseableOption.CannotCreateSuchOption e ) {
            throw new CannotCreateThisOption();
        }
        register(container, opt);
        return opt;
    }

    public static void bind(final Option one, final Option another) throws CannotBind {
        one.bind(another);
    }

    static public class CannotCreateThisOption extends RuntimeException {
        private static final long serialVersionUID = -5248825722401579070L;
    }

    static public class CannotParse extends RuntimeException {
        private static final long serialVersionUID = 6228517677445066958L;
    }

    @Override
    public void update (Observable initiator, Object initiated) {
        if ( initiator instanceof AbstractOption && initiated instanceof AbstractOption && initiator != initiated ) {
            DataContainer firstContainer = ((AbstractOption) initiator).getContainer();
            DataContainer secondContainer = ((AbstractOption) initiated).getContainer();

            Set<AbstractOption> firstSet = containers.get(firstContainer);
            Set<AbstractOption> secondSet = containers.get(secondContainer);

            if ( firstSet != null && secondSet != null ) {
                firstSet.addAll(secondSet);
                for ( AbstractOption opt : secondSet )
                    opt.setContainer(firstContainer);
                containers.remove(secondContainer);
            }
        }
    }

}
