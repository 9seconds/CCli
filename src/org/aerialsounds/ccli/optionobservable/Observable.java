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



package org.aerialsounds.ccli.optionobservable;



public abstract class Observable {



// ===============================================================================================================
// F I E L D S
// ===============================================================================================================



    private Observer observer;



// ===============================================================================================================
// P U B L I C   M E T H O D S
// ===============================================================================================================



    @Override
    public boolean
    equals (final Object o) {
        if ( this == o )
            return true;
        else if ( !(o instanceof Observable) )
            return false;

        return ( observer == ((Observable)o).observer );
    } /* equals */



// ===============================================================================================================
// P R O T E C T E D   M E T H O D S
// ===============================================================================================================



    final protected void
    registerObserver (final Observer repository) {
        observer = repository;
    } /* registerObserver */


    final protected void
    notifyObserver (final Object initiated) {
        if ( observer != null )
            observer.update(this, initiated);
    } /* notifyObserver */


    final protected void
    deleteObserver () {
        observer = null;
    } /* deleteObserver */


} /* class Observable */

