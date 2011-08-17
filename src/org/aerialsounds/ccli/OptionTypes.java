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



public enum OptionTypes {



// ===============================================================================================================
// D E F I N I T I O N S
// ===============================================================================================================



    SHORT  ("-"),
    LONG   ("--"),
    CUSTOM ("");



// ===============================================================================================================
// F I E L D S
// ===============================================================================================================



    private final String prefix;



// ===============================================================================================================
// C O N S T R U C T O R S
// ===============================================================================================================



    private
    OptionTypes (final String prefix) {
        this.prefix = prefix;
    } /* OptionTypes */



// ===============================================================================================================
// P U B L I C   M E T H O D S
// ===============================================================================================================



    final public String
    getPrefix () {
        return prefix;
    } /* getPrefix */


} /* enum OptionTypes */
