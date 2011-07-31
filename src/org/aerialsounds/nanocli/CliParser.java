package org.aerialsounds.nanocli;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import org.aerialsounds.nanocli.datacontainer.DataContainer;
import org.aerialsounds.nanocli.options.AbstractOption;
import org.aerialsounds.nanocli.options.ParseableOption;
import org.aerialsounds.nanocli.options.ShortOption;



public class CliParser implements Observer {

    final protected String[] args;
    final protected CliFactory factory;

    protected boolean parsed = false;

    final protected Map<DataContainer,Set<AbstractOption>> containers;
    final protected LinkedList<ParseableOption> options;
    final protected Collection<String> appArguments;

    public CliParser(String[] args) {
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
        if ( !parsed ) {
            parsed = true;

            clearParsedData();
            for ( int i = 0; i < args.length; ++i ) {
                String current = args[i];
                ParseableOption option = findOption(current);

                if ( option != null && !appArguments.isEmpty() )
                    interruptParsing();

                if ( option == null )
                    parseUnknownOption(current);
                else {
                    // first of all, we need to check Boolean values because they are shitty
                    if ( option.getValueType().isBoolean() ) {
                        // OK, it is boolean and it happens.
                        option.setValue(option.parse(Boolean.TRUE.toString()));
                        // but we need to be sure that it is not redefined by some other values

                        String inlineValue = option.getInlineValue(current);
                        if ( inlineValue != null ) { // hmm, have inline value! check it
                            Object parsedValue = option.parse(inlineValue);
                            if ( parsedValue == null )
                                interruptParsing();
                            else
                                option.setValue(parsedValue);
                        } else { // check next argument
                            if ( i < args.length - 1 ) {
                                Object parsedValue = option.parse(args[i+1]);
                                if ( parsedValue != null ) {
                                    option.setValue(parsedValue);
                                    ++i;
                                }
                            }
                        }
                    } else { // it is really convenient to handle boolean values distinct
                        String inlineValue = option.getInlineValue(current);
                        Object parsedValue = null;
                        if ( inlineValue == null ) // sad, but it is not inlined
                            if ( i == args.length - 1 )
                                interruptParsing();
                            else {
                                parsedValue = option.parse(args[i+1]);
                                if ( parsedValue != null )
                                    ++i;
                            }
                        else
                            parsedValue = option.parse(inlineValue);

                        if ( parsedValue == null )
                            interruptParsing();
                        else
                            option.setValue(parsedValue);
                    }
                }
            }
        }
    }

    protected void parseUnknownOption (String current) {
        Iterable<AbstractOption> joined = findJoinedOptions(current);
        if ( joined == null )
            appArguments.add(current);
        else
            confimJoin(joined);
    }

    protected void confimJoin (Iterable<AbstractOption> joined) {
        for ( AbstractOption opt : joined )
            opt.setValue(Boolean.TRUE);
    }

    protected Iterable<AbstractOption> findJoinedOptions (String current) {
        final String prefix = OptionTypes.SHORT.getPrefix();
        if ( current.startsWith(prefix) && !ShortOption.haveNumbers(current) ) {
            char[] currentLine = current.substring(prefix.length()).toCharArray();
            Collection<AbstractOption> list = new LinkedList<AbstractOption>();
            for ( char currentChar : currentLine ) {
                AbstractOption opt = findOption(prefix + Character.toString(currentChar));
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
        Set<DataContainer> conainersSet = containers.keySet();
        for ( DataContainer container : conainersSet )
            container.dropDefined();
    }

    protected ParseableOption findOption(String option) {
        Iterator<ParseableOption> it = options.iterator();
        while ( it.hasNext() ) {
            ParseableOption entry = it.next();
            String currentName = entry.getFullName();
            boolean suspectInline = option.startsWith(currentName + AbstractOption.STRING_INLINE_DELIMETER);
            boolean suspectOption = option.equals(currentName);
            if ( suspectOption || suspectInline )
                return entry;
        }
        return null;
    }

    public Iterator<String> getApplicationArguments() {
        return appArguments.iterator();
    }

    public Option create(OptionTypes type, String customPrefix, String name, Object defaultValue, ValueTypes valueType, String help) throws CannotCreateSuchOption {
        DataContainer container = factory.createDataContainer(defaultValue, valueType, help);
        ParseableOption opt;
        try {
            opt = factory.createOption(type, customPrefix, name, container);
        }
        catch ( ParseableOption.CannotCreateSuchOption e ) {
            throw new CannotCreateSuchOption();
        }
        register(container, opt);
        return opt;
    }

    public void remove(Option option) {
        if ( option instanceof ParseableOption ) {
            ParseableOption removed = (ParseableOption) option;
            if ( options.contains(removed) ) {
                options.remove(removed);
                removeContainer(removed);
                removed.deleteObserver(this);
            }
        }
    }

    protected void removeContainer (ParseableOption option) {
        DataContainer container = option.getContainer();
        Set<AbstractOption> containerSet = containers.get(container);
        if ( containerSet != null ) {
            containerSet.remove(option);
            if ( containerSet.isEmpty() )
                containers.remove(container);
        }
    }

    protected void register (DataContainer container, ParseableOption opt) {
        registerContainer(container, opt);
        registerOption(opt);
        parsed = false;
    }

    protected void registerOption (ParseableOption opt) {
        options.add(opt);
        opt.addObserver(this);
    }

    protected void registerContainer (DataContainer container, AbstractOption opt) {
        Set<AbstractOption> containerSet = new HashSet<AbstractOption>();
        containerSet.add(opt);
        containers.put(container, containerSet);
    }

    public Option createOption(OptionTypes type, String name, Object defaultValue, ValueTypes valueType, String help) throws CannotCreateSuchOption {
        DataContainer container = factory.createDataContainer(defaultValue, valueType, help);
        ParseableOption opt;
        try {
            opt = factory.createOption(type, name, container);
        }
        catch ( ParseableOption.CannotCreateSuchOption e ) {
            throw new CannotCreateSuchOption();
        }
        register(container, opt);
        return opt;
    }

    @Override
    public void update (Observable o, Object arg) {
        if ( o != arg && o instanceof AbstractOption && arg instanceof AbstractOption ) {
            DataContainer firstContainer = ((AbstractOption) o).getContainer();
            DataContainer secondContainer = ((AbstractOption) arg).getContainer();

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

    static public class CannotCreateSuchOption extends RuntimeException {
        private static final long serialVersionUID = -5248825722401579070L;
    }

    static public class CannotParse extends RuntimeException {
        private static final long serialVersionUID = 6228517677445066958L;
    }

}
