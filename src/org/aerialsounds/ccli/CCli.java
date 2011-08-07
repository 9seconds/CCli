/*
 * Copyright (C) 2011 by Sergey Arkhipov <serge@aerialsounds.org>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */



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
import org.aerialsounds.ccli.options.AbstractOption.DataIsNotValid;
import org.aerialsounds.ccli.valueparsers.BooleanConverter;



public class CCli
    implements Observer {


    static public class CannotCreateOption
        extends RuntimeException {
        private static final long serialVersionUID = -5248825722401579070L;
    }

    static public class HaveSuchOption
        extends RuntimeException {
        private static final long serialVersionUID = 5086623510834120688L;
    }

    static public class CannotParse
        extends RuntimeException {
        private static final long serialVersionUID = 6228517677445066958L;
    }

    static public class NothingToParse
        extends RuntimeException {
        private static final long serialVersionUID = -6990780110727348311L;
    }

    static public class UnexpectedOption
        extends RuntimeException {

        private static final long serialVersionUID = -7689966940656377554L;

        public UnexpectedOption (final String currentArgument) {
            super(currentArgument);
        }

    }

    static public class UnexpectedEndOfArgumentList
        extends RuntimeException {
        private static final long serialVersionUID = -4104774535367707313L;
    }

    static public class IncorrectParsingOfOption
        extends RuntimeException {

        private static final long serialVersionUID = -7594488127011543528L;

        public IncorrectParsingOfOption (final String option) {
            super(option);
        }

    }

    static public class DataIsNotConsistent
        extends RuntimeException {
        private static final long serialVersionUID = 2822892301721580774L;
    }


    final public void bind (final Option one, final Option another) throws CannotBind {
        one.bind(another);
    }


    protected final String[]                               args;
    protected final CliFactory                             factory;
    protected final Map<DataContainer,Set<AbstractOption>> containers;
    protected final Collection<ParseableOption>            options;
    protected final Collection<String>                     appArguments;
    protected       boolean                                parsed;

    private   final CliHelpGenerator                       helpGenerator;


    public CCli (final String[] args) {
        this(args, new CliHelpGenerator());
    }


    public CCli (final String[] args, final CliHelpGenerator helpGenerator) {
        this.args          = args;
        this.helpGenerator = helpGenerator;

        factory      = new CliFactory(this);
        containers   = new HashMap<DataContainer,Set<AbstractOption>>();
        options      = new LinkedList<ParseableOption>();
        appArguments = new LinkedList<String>();
    }


    protected void clearParsedData () {
        appArguments.clear();

        final Iterable<DataContainer> conainersSet = containers.keySet();
        for ( DataContainer container : conainersSet )
            container.dropDefined();

        parsed = false;
    }


    protected void confimJoin (final Iterable<ParseableOption> joined) {
        for ( ParseableOption opt : joined )
            opt.setValue(opt.parse(BooleanConverter.TRUE));
    }

    public boolean isParsed () {
        return parsed;
    }

    public Option createOption (
        final OptionTypes type,
        final String      name,
        final Object      defaultValue,
        final ValueTypes  valueType,
        final String      help
    )
    throws CannotCreateOption {
        final DataContainer container = factory.createDataContainer(defaultValue, valueType, help);
        try {
            final ParseableOption opt = factory.createOption(type, name, container);
            register(container, opt);
            return opt;
        }
        catch ( DataIsNotValid e ) {
            throw generateOptionCreatingException(e);
        }
        catch ( HaveSuchOption e ) {
            throw generateOptionCreatingException(e);
        }
    }

    final protected CannotCreateOption generateOptionCreatingException (final Throwable e) {
        return (CannotCreateOption) new CannotCreateOption().initCause(e);
    }

    public void remove (final Option option) {
        if ( option instanceof ParseableOption ) {
            final ParseableOption removed = (ParseableOption)option;
            if ( options.contains(removed) ) {
                options.remove(removed);
                removeContainer(removed);
                removed.dispose();
            }
        }
    }

    public Iterator<String> getApplicationArguments () {
        return appArguments.iterator();
    }

    public void parse () throws CannotParse {
        if ( args == null )
            interruptParsing(new NothingToParse());

        if ( !parsed ) {
            clearParsedData();

            String          inlineValue = null;
            Object          parsedValue = null;
            String          current     = null;
            ParseableOption option      = null;
            for ( int i = 0; i < args.length; ++i ) {
                current = args[i];
                option  = findOption(current);

                if ( option != null ) {
                    if ( !appArguments.isEmpty() )
                        interruptParsing(new UnexpectedOption(current));

                    inlineValue = option.getInlineValue(current);
                    parsedValue = null;
                    if ( inlineValue == null )
                        if ( i < args.length - 1 ) {
                            parsedValue = option.parse(args[i + 1]);
                            if ( parsedValue != null )
                                ++i;
                        } else
                            interruptParsing(new UnexpectedEndOfArgumentList());
                    else
                        parsedValue = option.parse(inlineValue);

                    if ( option.getValueType().isBoolean() && parsedValue == null )
                        parsedValue = option.parse(BooleanConverter.TRUE);

                    if ( parsedValue == null )
                        interruptParsing(new IncorrectParsingOfOption(current));
                    else
                        option.setValue(parsedValue);
                } else if ( !parseUnknownOption(current) )
                    appArguments.add(current);
            }
            if ( !isContainersConsistent() )
                interruptParsing(new DataIsNotConsistent());

            parsed = true;
        }
    }


    protected Iterable<ParseableOption> findJoinedOptions (final String current) {
        final String prefix = OptionTypes.SHORT.getPrefix();

        if ( !current.startsWith(OptionTypes.LONG.getPrefix()) && current.startsWith(prefix) && !ShortOption.haveNumbers(current) ) {
            final char[]                      currentLine = current.substring(prefix.length()).toCharArray();
            final Collection<ParseableOption> list        = new LinkedList<ParseableOption>();

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


    protected ParseableOption findOption (final String option) {
        final Iterator<ParseableOption> it = options.iterator();
        while ( it.hasNext() ) {
            final ParseableOption entry = it.next();
            if ( entry.appropriate(option) )
                return entry;
        }

        return null;
    }


    protected void interruptParsing (final RuntimeException cause) throws CannotParse {
        clearParsedData();
        throw (CannotParse) new CannotParse().initCause(cause);
    }


    private boolean isContainersConsistent () {
        final Iterable<DataContainer> containersSet = containers.keySet();
        for ( DataContainer container : containersSet )
            if ( !container.isConsistent() )
                return false;
        return true;
    }


    protected boolean parseUnknownOption (final String current) {
        final Iterable<ParseableOption> joined = findJoinedOptions(current);
        if ( joined != null ) {
            confimJoin(joined);
            return true;
        }
        return false;
    }


    protected void register (final DataContainer container, final ParseableOption opt) throws HaveSuchOption {
        final String name = opt.getFullName();
        for ( Option o : options )
            if ( name.equals(o.getFullName()) )
                throw new HaveSuchOption();

            registerContainer(container, opt);
            options.add(opt);
            parsed = false;
    }


    protected void registerContainer (final DataContainer container, final AbstractOption opt) {
        final Set<AbstractOption> containerSet = new HashSet<AbstractOption>();
        containerSet.add(opt);
        containers.put(container, containerSet);
    }


    protected void removeContainer (final AbstractOption option) {
        final DataContainer container                 = option.getContainer();
        final Collection<AbstractOption> containerSet = containers.get(container);
        if ( containerSet != null ) {
            containerSet.remove(option);
            if ( containerSet.isEmpty() )
                containers.remove(container);
        }
    }


    @Override
    public void update (final Observable initiator, final Object initiated) {
        if ( initiator != initiated && initiator instanceof AbstractOption && initiated instanceof AbstractOption ) {
            final DataContainer              firstContainer  = ((AbstractOption)initiator).getContainer();
            final DataContainer              secondContainer = ((AbstractOption)initiated).getContainer();
            final Collection<AbstractOption> firstSet        = containers.get(firstContainer);
            final Collection<AbstractOption> secondSet       = containers.get(secondContainer);

            if ( firstSet != null && secondSet != null ) {
                firstSet.addAll(secondSet);
                for ( AbstractOption opt : secondSet )
                    opt.setContainer(firstContainer);
                containers.remove(secondContainer);
            }
        }
    }

    final public String generateHelp () {
        return helpGenerator.generate(containers.values());
    }

}
