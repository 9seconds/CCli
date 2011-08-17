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



package org.aerialsounds.ccli.datacontainer;



import static org.aerialsounds.ccli.datacontainer.DataContainer.DataContainerException;
import static org.aerialsounds.ccli.datacontainer.DataContainer.OverrideValueType;



final class SyncValueType
    implements SyncStrategies {



// ===============================================================================================================
// P U B L I C   M E T H O D S
// ===============================================================================================================



    @Override
    public void
    sync (final DataContainer first, final DataContainer second)
    throws DataContainerException {
        if ( first.valueType == null )
            first.valueType = second.valueType;
        else if ( second.valueType == null )
            second.valueType = first.valueType;
        else if ( !first.valueType.equals(second.valueType) )
            throw new OverrideValueType();
    } /* sync */


} /* class SyncValueType */

