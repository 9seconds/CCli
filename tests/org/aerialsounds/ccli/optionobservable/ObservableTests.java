package org.aerialsounds.ccli.optionobservable;

import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;




public class ObservableTests {

    class ReturnValue {
        public int value = 0;
        public void inc() {
            value++;
        }
    }

    private ReturnValue rv;
    private Observable observable;
    private Observer observer;

    @Before
    public void setUp() {
        rv = new ReturnValue();
        observable = new Observable() {};
        observer = new Observer() {

            @Override
            public void update (Observable initiator, Object initiated) {
                rv.inc();
            }
        };
    }

    @Test
    public void testObservable() {

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

    @Test
    public void testEqual() {
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

}
