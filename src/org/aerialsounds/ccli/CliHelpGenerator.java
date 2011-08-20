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



import static java.util.Arrays.sort;

import java.util.Collection;
import java.util.Set;

import org.aerialsounds.ccli.options.AbstractOption;



/**
 * <p>This class presents default generator of help for {@link CCli} repositories.</p>
 *
 * <p>It generates help about all presented options according to bindings each to other</p>
 *
 * <p>Example output of help:</p>
 *
 * <pre><code>
 *     -z, --zip
 *         Use zip compression for application
 *     -q
 *         Removes all notifications about application work
 *     --verbose, +VERBOSE
 *         Use extended output of application
 * </code></pre>
 *
 * <p>You should use {@link CliHelpGenerator#generate generate()} method for generating
 * help for given collections of set of
 * {@linkplain org.aerialsounds.ccli.options.AbstractOption options}.</p>
 *
 * @see CCli
 *
 * @since 1.0
 *
 * @author Serge Arkhipov &lt;<a href="mailto:serge@aerialsounds.org">serge@aerialsounds.org</a>&gt;
 *
 */
public class CliHelpGenerator {



// ===============================================================================================================
// F I E L D S
// ===============================================================================================================



    /**
     * This is main accumulator of help output. After generating help, it flushes result.
     *
     * @since 1.0
     *
     * @author Serge Arkhipov &lt;<a href="mailto:serge@aerialsounds.org">serge@aerialsounds.org</a>&gt;
     *
     */
    protected StringBuilder builder;

    /**
     * This collection contains all sorted option names (full names) for given binded set. As a rule,
     * you should not think about which set does these names belong. Just think about current set.
     *
     * @since 1.0
     *
     * @author Serge Arkhipov &lt;<a href="mailto:serge@aerialsounds.org">serge@aerialsounds.org</a>&gt;
     *
     */
    protected String[] names;



// ===============================================================================================================
// P U B L I C   M E T H O D S
// ===============================================================================================================



    /**
     * <p>This method should be used for generating help for given collection
     * of {@linkplain org.aerialsounds.ccli.options.AbstractOption options}.</p>
     *
     * @param values - collection of binded sets of options.
     *
     * @return Generated help string.
     *
     * @see org.aerialsounds.ccli.options.AbstractOption AbstractOption
     *
     * @since 1.0
     *
     * @author Serge Arkhipov &lt;<a href="mailto:serge@aerialsounds.org">serge@aerialsounds.org</a>&gt;
     *
     */
    final public String
    generate (final Collection<Set<AbstractOption>> values) {
        builder = new StringBuilder();
        names   = null;

        for ( Set<AbstractOption> set : values )
            generateForSet(set);

        String ret     = builder.toString();
               builder = null;
               names   = null;

        return ret;
    } /* generate */



// ===============================================================================================================
// P R O T E C T E D   M E T H O D S
// ===============================================================================================================



    /**
     * <p>This method should be used for appending string of names to
     * the {@link CliHelpGenerator#builder builder}.</p>
     *
     * @since 1.0
     *
     * @author Serge Arkhipov &lt;<a href="mailto:serge@aerialsounds.org">serge@aerialsounds.org</a>&gt;
     *
     */
    protected void
    generateOptionList () {
        for ( int i = 0; i < names.length; ++i ) {
            if ( i > 0 )
                builder.append(", ");
            builder.append(names[i]);
        }
    } /* generateOptionList */


    /**
     * <p>This method should be used for appending help for given set to
     * {@link CliHelpGenerator#builder builder}.</p>
     *
     * @param set - a set of {@linkplain org.aerialsounds.ccli.options.AbstractOption options}.
     *
     * @since 1.0
     *
     * @author Serge Arkhipov &lt;<a href="mailto:serge@aerialsounds.org">serge@aerialsounds.org</a>&gt;
     *
     */
    protected void
    generateHelp (final Set<AbstractOption> set) {
        if ( !set.isEmpty() ) {
            builder.append("\n    ");
            builder.append(set.iterator().next().getHelp());
            builder.append("\n");
        }
    } /* generateHelp */


    /**
     * <p>This method should be invoked after generating help for set.</p>
     *
     * @param set - a set of processing {@linkplain org.aerialsounds.ccli.options.AbstractOption options}.
     *
     * @since 1.0
     *
     * @author Serge Arkhipov &lt;<a href="mailto:serge@aerialsounds.org">serge@aerialsounds.org</a>&gt;
     *
     */
    protected void
    after (final Set<AbstractOption> set) {

    } /* after */


    /**
     * <p>This method should be invoked before generating help for set.</p>
     *
     * @param set - a set of processing {@linkplain org.aerialsounds.ccli.options.AbstractOption options}.
     *
     * @since 1.0
     *
     * @author Serge Arkhipov &lt;<a href="mailto:serge@aerialsounds.org">serge@aerialsounds.org</a>&gt;
     *
     */
    protected void
    before (final Set<AbstractOption> set) {

    } /* before */



// ===============================================================================================================
// P R I V A T E   M E T H O D S
// ===============================================================================================================



    /**
     * <p>This method defines the sequence of actions which is needed for generating help for
     * each set.</p>
     *
     * @param set - a set of processing {@linkplain org.aerialsounds.ccli.options.AbstractOption options}.
     *
     * @since 1.0
     *
     * @author Serge Arkhipov &lt;<a href="mailto:serge@aerialsounds.org">serge@aerialsounds.org</a>&gt;
     *
     */
    private void
    generateForSet (final Set<AbstractOption> set) {
        before(set);
        retrieveOptionNames(set);
        generateOptionList();
        generateHelp(set);
        after(set);
    } /* generateForSet */


    /**
     * <p>This method should be used to retrieve option names from set to
     * {@linkplain CliHelpGenerator#names internal collection}.</p>
     *
     * @param set - a set of processing {@linkplain org.aerialsounds.ccli.options.AbstractOption options}.
     *
     * @since 1.0
     *
     * @author Serge Arkhipov &lt;<a href="mailto:serge@aerialsounds.org">serge@aerialsounds.org</a>&gt;
     *
     */
    private void
    retrieveOptionNames (final Set<AbstractOption> set) {
        names = new String[set.size()];

        int i = 0;
        for ( AbstractOption o : set )
            names[i++] = o.getFullName();

        sort(names);
    } /* retrieveOptionNames */


} /* class CliHelpGenerator */

