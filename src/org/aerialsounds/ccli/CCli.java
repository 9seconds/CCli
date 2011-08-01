package org.aerialsounds.ccli;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import org.aerialsounds.ccli.datacontainer.DataContainer;
import org.aerialsounds.ccli.options.AbstractOption;
import org.aerialsounds.ccli.options.ParseableOption;
import org.aerialsounds.ccli.options.ShortOption;
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
        Set<DataContainer> conainersSet = containers.keySet();
        for ( DataContainer container : conainersSet )
            if ( !container.isConsistent() )
                return false;
        return true;
    }

    protected void setBooleanTrue (ParseableOption option) {
        option.setValue(option.parse(BooleanConverter.TRUE));
    }

    protected boolean parseUnknownOption (String current) {
        Iterable<ParseableOption> joined = findJoinedOptions(current);
        if ( joined != null ) {
            confimJoin(joined);
            return true;
        }
        return false;
    }

    protected void confimJoin (Iterable<ParseableOption> joined) {
        for ( ParseableOption opt : joined )
            setBooleanTrue(opt);
    }

    protected Iterable<ParseableOption> findJoinedOptions (String current) {
        final String prefix = OptionTypes.SHORT.getPrefix();
        if ( current.startsWith(prefix) && !ShortOption.haveNumbers(current) ) {
            char[] currentLine = current.substring(prefix.length()).toCharArray();
            Collection<ParseableOption> list = new LinkedList<ParseableOption>();
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
        Set<DataContainer> conainersSet = containers.keySet();
        for ( DataContainer container : conainersSet )
            container.dropDefined();
        parsed = false;
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

    protected void removeContainer (AbstractOption option) {
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
