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



/**
 * <p>This exception is raised if one option cannot bind to another. There are several reasons
 * for raising such exception:</p>
 *
 * <ol>
 *     <li>Different {@linkplain ValueTypes value types} are defined for these options,</li>
 *     <li>Different default values are defined for these options,</li>
 *     <li>Different help strings are defined for these options,</li>
 *     <li>Different value is set for these options (please mention that it is related only
 *         to case when values were explicit defined),</li>
 *     <li>Different {@code CCli} repositories are defined for these options.</li>
 * </ol>
 *
 * <p>Note that difference in {@linkplain OptionTypes option types} and names is transparent.</p>
 *
 * @see ValueTypes
 * @see OptionTypes
 *
 * @since 1.0
 *
 * @author Serge Arkhipov &lt;<a href="mailto:serge@aerialsounds.org">serge@aerialsounds.org</a>&gt;
 *
 */
public class CannotBind
    extends RuntimeException {
    private static final long serialVersionUID = 9035409865724452061L;
} /* class CannotBind */

