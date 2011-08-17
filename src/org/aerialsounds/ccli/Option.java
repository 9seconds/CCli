/*
 * Copyright (C) 2011 by Serge Arkhipov <serge@aerialsounds.org>
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



/**
 * <p>This interface defines option objects that created by {@link CCli} repository using
 * {@link CCli#createOption createOption()} method. Every created option is registered for
 * parsing. Options are not explicit created by repository are interpreted as
 * {@linkplain CCli#getApplicationArguments application arguments} or as
 * {@linkplain CCli#parseUnknownOption joined} ones if possible.</p>
 *
 * <p>Let's assume that we create {@code "-z"} and {@code "--verbose"} options. Application
 * is started with following command line:</p>
 *
 * <pre><code>
 *     java app.jar -z 10 --verbose=false -t file.txt
 * </code></pre>
 *
 * <p>Options {@code "-t"} and {@code file.txt} will be interpreted during parsing as application
 * arguments. {@code 10} will be set as the value of {@code "-z"} option and {@code false} as the value
 * of {@code "--verbose"} (if value types are appropriate). You can get these values with
 * {@link Option#getValue getValue()} method. Please note that {@code getValue()} method <strong>always
 * return not {@code null} value</strong> with appropriate type!</p>
 *
 * <p>Name of option should be set according option type, but you can get full name (with prefix) with
 * {@link Option#getFullName getFullName()} method.</p>
 *
 * <p>Also you can use {@link Option#isParsed isParsed()} method for checking parsed state of option. If
 * option is not parsed, default value should be returned.</p>
 *
 * @see CCli
 * @see ValueTypes
 * @see OptionTypes
 * @see CCli#parsed parse()
 *
 * @since 1.0
 *
 * @author Serge Arkhipov &lt;<a href="mailto:serge@aerialsounds.org">serge@aerialsounds.org</a>&gt;
 *
 */
public interface Option {


    /**
     * <p>This method should be used to bind one option to another.</p>
     *
     * <p>When options are binded, they will have the same values. If value for one option is set,
     * value of another option will be set as well. You can suppose binding as creating <em>aliases</em> for option.</p>
     *
     * @param other - option to bind.
     *
     * @throws CannotBind if binding is impossible.
     *
     * @since 1.0
     *
     * @author Serge Arkhipov &lt;<a href="mailto:serge@aerialsounds.org">serge@aerialsounds.org</a>&gt;
     *
     */
    void
    bind (final Option other)
    throws CannotBind;


    /**
     * <p>This method should be used to get full name of option.</p>
     *
     * <p>For example, you created option in following way:</p>
     *
     * <pre><code>
     *     Option opt = repository.createOption(SHORT, "z", false, BOOLEAN, "Simple option");
     * </code></pre>
     *
     * <p>Then invokation of method is give you {@code "-z"}. Interpret full name as prefixed name to
     * avoid misunderstanding.</p>
     *
     * @return Full name of option.
     *
     * @since 1.0
     *
     * @author Serge Arkhipov &lt;<a href="mailto:serge@aerialsounds.org">serge@aerialsounds.org</a>&gt;
     *
     */
    String
    getFullName ();


    /**
     * <p>This method should be used to get help for option.</p>
     *
     * @return Help for option.
     *
     * @since 1.0
     *
     * @author Serge Arkhipov &lt;<a href="mailto:serge@aerialsounds.org">serge@aerialsounds.org</a>&gt;
     *
     */
    String
    getHelp ();


    /**
     * <p>This method should be used to get short name of option.</p>
     *
     * <p>For example, you created option in following way:</p>
     *
     * <pre><code>
     *     Option opt = repository.createOption(SHORT, "z", false, BOOLEAN, "Simple option");
     * </code></pre>
     *
     * <p>Then invokation of method is give you {@code "z"}. Interpret short name as unprefixed name to
     * avoid misunderstanding.</p>
     *
     * @return Short name of option.
     *
     * @since 1.0
     *
     * @author Serge Arkhipov &lt;<a href="mailto:serge@aerialsounds.org">serge@aerialsounds.org</a>&gt;
     *
     */
    String
    getName ();


    /**
     * <p>This method should be used to obtain {@linkplain OptionTypes type of option}.</p>
     *
     * @return Option type
     *
     * @see OptionTypes
     *
     * @since 1.0
     *
     * @author Serge Arkhipov &lt;<a href="mailto:serge@aerialsounds.org">serge@aerialsounds.org</a>&gt;
     *
     */
    OptionTypes
    getType ();


    /**
     * <p>This method should be used to obtain option value. Not that value is always have the same
     * value as defined by its {@linkplain ValueTypes value type}, therefore you do not need to implement
     * additional validation.</p>
     *
     * @return Option value
     *
     * @see ValueTypes
     *
     * @since 1.0
     *
     * @author Serge Arkhipov &lt;<a href="mailto:serge@aerialsounds.org">serge@aerialsounds.org</a>&gt;
     *
     */
    Object
    getValue ();


    /**
     * <p>This method should be used to obtain option {@linkplain ValueTypes value type}.</p>
     *
     * @return Option value type
     *
     * @see ValueTypes
     *
     * @since 1.0
     *
     * @author Serge Arkhipov &lt;<a href="mailto:serge@aerialsounds.org">serge@aerialsounds.org</a>&gt;
     *
     */
    ValueTypes
    getValueType ();


    /**
     * <p>This method should be used to check that option was parsed. If it is not parsed, default value
     * should be used as result of {@link Option#getValue getValue()} method.</p>
     *
     * @return {@code true} if parsed, {@code false} otherwise.
     *
     * @see ValueTypes
     * @see CCli#parse parse
     *
     * @since 1.0
     *
     * @author Serge Arkhipov &lt;<a href="mailto:serge@aerialsounds.org">serge@aerialsounds.org</a>&gt;
     *
     */
    boolean
    isParsed ();

} /* interface Option */

