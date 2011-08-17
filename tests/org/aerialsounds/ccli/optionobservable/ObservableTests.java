/*
 * Copyright (C) 2011 by Serge Arkhipov <serge@aerialsounds.org>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
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



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;



public class ObservableTests {


    class ReturnValue {


        public int value = 0;


        public void inc () {
            value++;
        }
    }


    private ReturnValue rv;
    private Observable  observable;
    private Observer    observer;


    @Before
    public void setUp () {
        rv = new ReturnValue();
        observable = new Observable() {};
        observer = new Observer() {

            @Override
            public void update (final Observable initiator, final Object initiated) {
                rv.inc();
            }

        };
    }


    @Test
    public void testEqual () {
        Observable ob = new Observable() {};
        assertEquals(ob, observable);
        assertEquals(observable, ob);
        assertEquals(ob, ob);

        ob.registerObserver(observer);
        assertFalse(ob.equals(observable));
        assertFalse(observable.equals(ob));

        observable.registerObserver(observer);
        assertEquals(ob, observable);
        assertEquals(observable, ob);
        assertEquals(ob, ob);

        ob.registerObserver(null);
        assertFalse(ob.equals(observable));
        assertFalse(observable.equals(ob));

        observable.registerObserver(null);
        assertEquals(ob, observable);
        assertEquals(observable, ob);
        assertEquals(ob, ob);

        HashSet<String> set = new HashSet<String>();
        assertFalse(ob.equals(set));
    }


    @Test
    public void testObservable () {

        observable.notifyObserver(null);
        assertEquals(0, rv.value);
        observable.deleteObserver();
        assertEquals(0, rv.value);

        observable.registerObserver(null);
        observable.notifyObserver(null);
        assertEquals(0, rv.value);

        observable.registerObserver(observer);
        assertEquals(0, rv.value);
        observable.notifyObserver(null);
        assertEquals(1, rv.value);

        observable.notifyObserver(null);
        assertEquals(2, rv.value);

        observable.deleteObserver();
        assertEquals(2, rv.value);

        observable.notifyObserver(null);
        assertEquals(2, rv.value);

        observable.registerObserver(observer);
        assertEquals(2, rv.value);
        observable.notifyObserver(null);
        assertEquals(3, rv.value);

        observable.registerObserver(observer);
        assertEquals(3, rv.value);

        observable.notifyObserver(null);
        assertEquals(4, rv.value);

        observable.deleteObserver();
        assertEquals(4, rv.value);

        observable.notifyObserver(null);
        assertEquals(4, rv.value);
    }

}
