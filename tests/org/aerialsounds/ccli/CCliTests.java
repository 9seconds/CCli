/*
 * Copyright (C) 2011 by Sergey Arkhipov <serge@aerialsounds.org>
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



package org.aerialsounds.ccli;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

import org.aerialsounds.ccli.CCli.CannotCreateOption;
import org.aerialsounds.ccli.CCli.CannotParse;
import org.aerialsounds.ccli.CCli.HaveSuchOption;
import org.aerialsounds.ccli.CCli.IncorrectParsingOfOption;
import org.aerialsounds.ccli.CCli.NothingToParse;
import org.aerialsounds.ccli.CCli.UnexpectedEndOfArgumentList;
import org.aerialsounds.ccli.CCli.UnexpectedOption;
import org.aerialsounds.ccli.datacontainer.DataContainer;
import org.aerialsounds.ccli.options.AbstractOption;
import org.aerialsounds.ccli.options.LongOption;
import org.aerialsounds.ccli.options.NumericalOption;
import org.aerialsounds.ccli.options.ShortOption;
import org.aerialsounds.ccli.options.AbstractOption.DataIsNotValid;

import org.junit.Test;



public class CCliTests {


    @Test
    public void
    create () {
        CCli rep = new CCli(null);
        assertFalse(rep.isParsed());
        Iterator<String> it = rep.getApplicationArguments();
        assertFalse(it.hasNext());
    }


    @Test (expected = HaveSuchOption.class)
    public void
    createDuplicateOption () throws Throwable {
        CCli rep = new CCli(null);
        @SuppressWarnings ("unused") Option o = rep.createOption(
            OptionTypes.SHORT,
            "D",
            Boolean.FALSE,
            ValueTypes.BOOLEAN,
            "Boolean opt");
        try {
            o = rep.createOption(OptionTypes.SHORT, "D", Boolean.FALSE, ValueTypes.BOOLEAN, "Boolean opt");
        }
        catch ( CannotCreateOption e ) {
            throw e.getCause();
        }
    }


    @Test (expected = HaveSuchOption.class)
    public void
    createDuplicateOptionWithOtherType () throws Throwable {
        CCli rep = new CCli(null);
        @SuppressWarnings ("unused") Option o = rep.createOption(
            OptionTypes.LONG,
            "vbr-new",
            Boolean.FALSE,
            ValueTypes.BOOLEAN,
            "Boolean opt");
        try {
            o = rep.createOption(OptionTypes.CUSTOM, "--vbr-new", Boolean.FALSE, ValueTypes.BOOLEAN, "Boolean opt");
        }
        catch ( CannotCreateOption e ) {
            throw e.getCause();
        }
    }


    @Test
    public void
    createOptions () {
        CCli rep = new CCli(null);

        Option o = rep.createOption(OptionTypes.SHORT, "9", Boolean.FALSE, ValueTypes.BOOLEAN, "Boolean opt");
        assertTrue(o instanceof NumericalOption);

        o = rep.createOption(OptionTypes.SHORT, "v", Boolean.FALSE, ValueTypes.BOOLEAN, "Boolean opt");
        assertTrue(o instanceof ShortOption);

        o = rep.createOption(OptionTypes.LONG, "vbr-new", Boolean.FALSE, ValueTypes.BOOLEAN, "Boolean opt");
        assertTrue(o instanceof LongOption);

        o = rep.createOption(OptionTypes.CUSTOM, "-DINLINE", Boolean.FALSE, ValueTypes.BOOLEAN, "Boolean opt");
        assertTrue(o instanceof LongOption);
    }


    @Test (expected = DataIsNotValid.class)
    public void
    failedCreateOptions () throws Throwable {
        CCli rep = new CCli(null);
        try {
            @SuppressWarnings ("unused") Option o = rep.createOption(
                OptionTypes.SHORT,
                "9",
                Boolean.FALSE,
                ValueTypes.INTEGER,
                "Int opt");
        }
        catch ( CannotCreateOption e ) {
            throw e.getCause();
        }

    }


    @Test (expected = IncorrectParsingOfOption.class)
    public void
    incorrectParsingOfOption () throws Throwable {
        String[] args = {"-ni", "-i", "-z", "awesome", "-z"};

        CCli rep = new CCli(args);
        @SuppressWarnings ("unused") Option opt1 = rep.createOption(
            OptionTypes.SHORT,
            "n",
            false,
            ValueTypes.BOOLEAN,
            "Stereo options");
        @SuppressWarnings ("unused") Option opt2 = rep.createOption(
            OptionTypes.SHORT,
            "i",
            false,
            ValueTypes.BOOLEAN,
            "Quality options");
        @SuppressWarnings ("unused") Option opt3 = rep.createOption(
            OptionTypes.SHORT,
            "z",
            10,
            ValueTypes.INTEGER,
            "Stereo options");
        try {
            rep.parse();
            fail();
        }
        catch ( CannotParse e ) {
            throw e.getCause();
        }
    }


    @Test
    public void
    joinedOption () {
        String[] args = {"-n", "-i", "-zxvcf", "-g.0002", "file", "foo.c"};

        CCli rep = new CCli(args);
        Option optSound = rep.createOption(OptionTypes.SHORT, "n", false, ValueTypes.BOOLEAN, "Stereo options");
        Option optQuality = rep.createOption(OptionTypes.SHORT, "i", false, ValueTypes.BOOLEAN, "Quality options");
        Option opt2 = rep.createOption(OptionTypes.SHORT, "z", false, ValueTypes.BOOLEAN, "Stereo options");
        Option opt3 = rep.createOption(OptionTypes.SHORT, "x", false, ValueTypes.BOOLEAN, "Quality options");
        Option opt4 = rep.createOption(OptionTypes.SHORT, "v", false, ValueTypes.BOOLEAN, "Stereo options");
        Option opt5 = rep.createOption(OptionTypes.SHORT, "f", false, ValueTypes.BOOLEAN, "Quality options");
        Option opt6 = rep.createOption(OptionTypes.SHORT, "c", false, ValueTypes.BOOLEAN, "Stereo options");
        Option opt7 = rep.createOption(OptionTypes.SHORT, "g", 0.0, ValueTypes.DOUBLE, "Quality options");
        rep.parse();

        String[] a = {"file", "foo.c"};
        int i = 0;
        Iterator<String> appArgs = rep.getApplicationArguments();
        while ( appArgs.hasNext() )
            assertTrue(appArgs.next().equals(a[i++ ]));

        assertEquals(true, optSound.getValue());
        assertEquals(true, optQuality.getValue());
        assertEquals(true, opt2.getValue());
        assertEquals(true, opt3.getValue());
        assertEquals(true, opt4.getValue());
        assertEquals(true, opt5.getValue());
        assertEquals(true, opt6.getValue());
        assertEquals(0.0002d, opt7.getValue());
    }


    @Test
    public void
    mixedOptions () {
        String[] args = {"-ni", "-i", "-DINLINE=false", "--vbr-new", "-zxvcf", "-DINLINE", "-f", "0", "--verbose",
            "10", "-g.0002", "file", "foo.c"};

        CCli rep = new CCli(args);
        Option opt1 = rep.createOption(OptionTypes.SHORT, "n", false, ValueTypes.BOOLEAN, "Stereo options");
        Option opt2 = rep.createOption(OptionTypes.SHORT, "i", false, ValueTypes.BOOLEAN, "Quality options");
        Option opt3 = rep.createOption(OptionTypes.SHORT, "z", false, ValueTypes.BOOLEAN, "Stereo options");
        Option opt4 = rep.createOption(OptionTypes.SHORT, "x", false, ValueTypes.BOOLEAN, "Quality options");
        Option opt5 = rep.createOption(OptionTypes.SHORT, "v", false, ValueTypes.BOOLEAN, "Stereo options");
        Option opt6 = rep.createOption(OptionTypes.SHORT, "f", false, ValueTypes.BOOLEAN, "Quality options");
        Option opt7 = rep.createOption(OptionTypes.SHORT, "c", false, ValueTypes.BOOLEAN, "Stereo options");
        Option opt8 = rep.createOption(OptionTypes.SHORT, "g", 0.0, ValueTypes.DOUBLE, "Quality options");
        Option opt9 = rep.createOption(OptionTypes.CUSTOM, "-DINLINE", false, ValueTypes.BOOLEAN, "Quality options");
        Option opt10 = rep.createOption(OptionTypes.LONG, "verbose", 13L, ValueTypes.LONG, "Quality options");
        Option opt11 = rep.createOption(OptionTypes.LONG, "filled", 11L, ValueTypes.LONG, "Quality options");
        Option opt12 = rep.createOption(
            OptionTypes.LONG,
            "vbr-new",
            new AtomicBoolean(false),
            ValueTypes.ATOMIC_BOOLEAN,
            "Quality options");
        rep.parse();

        String[] a = {"file", "foo.c"};
        int i = 0;
        Iterator<String> appArgs = rep.getApplicationArguments();
        while ( appArgs.hasNext() )
            assertTrue(appArgs.next().equals(a[i++ ]));

        assertEquals(true, opt1.getValue());
        assertEquals(true, opt2.getValue());
        assertEquals(true, opt3.getValue());
        assertEquals(true, opt4.getValue());
        assertEquals(true, opt5.getValue());
        assertEquals(false, opt6.getValue());
        assertEquals(true, opt7.getValue());
        assertEquals(0.0002d, opt8.getValue());
        assertEquals(true, opt9.getValue());
        assertEquals(10L, opt10.getValue());
        assertEquals(11L, opt11.getValue());
        assertEquals(true, ((AtomicBoolean)opt12.getValue()).get());
    }


    @Test (expected = NothingToParse.class)
    public void nothingToParse () throws Throwable {
        String[] args = null;

        CCli rep = new CCli(args);
        @SuppressWarnings ("unused") Option opt1 = rep.createOption(
            OptionTypes.SHORT,
            "n",
            false,
            ValueTypes.BOOLEAN,
            "Stereo options");
        @SuppressWarnings ("unused") Option opt2 = rep.createOption(
            OptionTypes.SHORT,
            "i",
            false,
            ValueTypes.BOOLEAN,
            "Quality options");
        @SuppressWarnings ("unused") Option opt3 = rep.createOption(
            OptionTypes.SHORT,
            "z",
            "10L",
            ValueTypes.STRING,
            "Stereo options");
        try {
            rep.parse();
            fail();
        }
        catch ( CannotParse e ) {
            throw e.getCause();
        }
    }


    @Test
    public void
    overrideValues () {
        String[] args = {"-v", "10", "--q=true", "-v0", "-v12", "--q", "file", "foo.c"};

        CCli rep = new CCli(args);
        Option optSound = rep.createOption(OptionTypes.SHORT, "v", 0, ValueTypes.INTEGER, "Stereo options");
        Option optQuality = rep.createOption(OptionTypes.LONG, "q", false, ValueTypes.BOOLEAN, "Quality options");
        rep.parse();

        String[] a = {"file", "foo.c"};
        int i = 0;
        Iterator<String> appArgs = rep.getApplicationArguments();
        while ( appArgs.hasNext() )
            assertTrue(appArgs.next().equals(a[i++ ]));

        assertEquals(12, optSound.getValue());
        assertEquals(true, optQuality.getValue());
    }


    @Test
    public void
    parsingCustomOptions () {
        String[] args = {"+X", "-DINLINE=__inline__", "if=/vobs/cello/", "&q", "123", "file", "foo.c"};

        CCli rep = new CCli(args);
        Option optSound = rep.createOption(OptionTypes.CUSTOM, "+X", true, ValueTypes.BOOLEAN, "Stereo options");
        Option optQuality = rep.createOption(
            OptionTypes.CUSTOM,
            "-DINLINE",
            "inline",
            ValueTypes.STRING,
            "Quality options");
        Option optZip = rep.createOption(OptionTypes.CUSTOM, "if", "/dev/eri0", ValueTypes.STRING, "ZIP options");
        Option optParam = rep.createOption(OptionTypes.CUSTOM, "&q", -100, ValueTypes.INTEGER, "Some param options");
        rep.parse();

        String[] a = {"file", "foo.c"};
        int i = 0;
        Iterator<String> appArgs = rep.getApplicationArguments();
        while ( appArgs.hasNext() )
            assertTrue(appArgs.next().equals(a[i++ ]));

        assertEquals(true, optSound.getValue());
        assertEquals("__inline__", optQuality.getValue());
        assertEquals("/vobs/cello/", optZip.getValue());
        assertEquals(123, optParam.getValue());
    }


    @Test
    public void
    parsingLongOptions () {
        String[] args = {"--verbose", "c", "--vbr-new", "--noreplaygain=no", "--t=1.0", "file", "foo.c"};

        CCli rep = new CCli(args);
        Option optSound = rep.createOption(OptionTypes.LONG, "verbose", 'q', ValueTypes.CHAR, "Stereo options");
        Option optQuality = rep.createOption(OptionTypes.LONG, "vbr-new", false, ValueTypes.BOOLEAN, "Quality options");
        Option optZip = rep.createOption(OptionTypes.LONG, "noreplaygain", false, ValueTypes.BOOLEAN, "ZIP options");
        Option optParam = rep.createOption(OptionTypes.LONG, "t", 0.0d, ValueTypes.DOUBLE, "Some param options");
        rep.parse();

        String[] a = {"file", "foo.c"};
        int i = 0;
        Iterator<String> appArgs = rep.getApplicationArguments();
        while ( appArgs.hasNext() )
            assertTrue(appArgs.next().equals(a[i++ ]));

        assertEquals('c', optSound.getValue());
        assertEquals(true, optQuality.getValue());
        assertEquals(false, optZip.getValue());
        assertEquals(1.0, optParam.getValue());
    }


    @Test
    public void
    parsingShortOptions () {
        String[] args = {"-m", "j", "-V0", "-z", "-q", "1.0e+05f", "-n", "0", "file"};

        CCli rep = new CCli(args);
        Option optSound = rep.createOption(OptionTypes.SHORT, "m", "s", ValueTypes.STRING, "Stereo options");
        Option optQuality = rep.createOption(OptionTypes.SHORT, "V", 2, ValueTypes.INTEGER, "Quality options");
        Option optZip = rep.createOption(OptionTypes.SHORT, "z", false, ValueTypes.BOOLEAN, "ZIP options");
        Option optParam = rep.createOption(OptionTypes.SHORT, "q", 0.0f, ValueTypes.FLOAT, "Some param options");
        Option optN = rep.createOption(OptionTypes.SHORT, "n", true, ValueTypes.BOOLEAN, "Some N options");
        rep.parse();

        Iterator<String> appArgs = rep.getApplicationArguments();
        while ( appArgs.hasNext() )
            assertTrue(appArgs.next().equals("file"));

        assertEquals("j", optSound.getValue());
        assertEquals(0, optQuality.getValue());
        assertEquals(true, optZip.getValue());
        assertEquals(1.0e+05f, optParam.getValue());
        assertEquals(false, optN.getValue());
    }


    @Test
    public void
    parsingWithoutOptions () {
        String[] args = {"-m", "j", "-V0", "--noreplaygain", "--vbr-new", "input.wav", "output.mp3"};

        CCli rep = new CCli(args);
        rep.parse();

        Iterator<String> appArgs = rep.getApplicationArguments();
        int i = 0;
        while ( appArgs.hasNext() )
            assertTrue(appArgs.next().equals(args[i++ ]));
    }


    @Test
    public void
    preparsedBinding () {
        CCli rep = new CCli(null);

        AbstractOption o10 = (AbstractOption)rep.createOption(OptionTypes.SHORT, "r", 0, ValueTypes.INTEGER, "Int opt");
        AbstractOption o11 = (AbstractOption)rep.createOption(
            OptionTypes.LONG,
            "recurse",
            0,
            ValueTypes.INTEGER,
            "Int opt");
        AbstractOption o12 = (AbstractOption)rep.createOption(
            OptionTypes.CUSTOM,
            "-Frec",
            0,
            ValueTypes.INTEGER,
            "Int opt");
        AbstractOption o20 = (AbstractOption)rep.createOption(OptionTypes.SHORT, "f", 0, ValueTypes.INTEGER, "Int opt");
        AbstractOption o21 = (AbstractOption)rep.createOption(
            OptionTypes.LONG,
            "flip",
            0,
            ValueTypes.INTEGER,
            "Int opt");
        AbstractOption o22 = (AbstractOption)rep.createOption(
            OptionTypes.CUSTOM,
            "-Fflp",
            0,
            ValueTypes.INTEGER,
            "Int opt");
        AbstractOption o30 = (AbstractOption)rep.createOption(
            OptionTypes.LONG,
            "vbr-new",
            0,
            ValueTypes.INTEGER,
            "Int opt");

        o10.setValue(10);
        o20.setValue(20);
        assertFalse(o10.equals(o11));
        assertFalse(o10.equals(o12));
        assertFalse(o10.equals(o20));
        assertFalse(o10.equals(o21));
        assertFalse(o10.equals(o22));
        assertFalse(o10.equals(o30));

        DataContainer c1 = o11.getContainer();
        DataContainer c2 = o12.getContainer();
        o11.bind(o12);
        assertTrue(o11.getContainer() == o12.getContainer());
        assertTrue(o11.getContainer() == c1 ^ o11.getContainer() == c2);

        assertTrue(o11.equals(o12));
        assertFalse(o10.equals(o11));
        assertFalse(o10.equals(o12));
        assertFalse(o10.equals(o20));
        assertFalse(o10.equals(o21));
        assertFalse(o10.equals(o22));
        assertFalse(o10.equals(o30));

        o22.bind(o21);
        assertFalse(o10.equals(o11));
        assertFalse(o10.equals(o12));
        assertFalse(o10.equals(o20));
        assertFalse(o10.equals(o21));
        assertFalse(o10.equals(o22));
        assertFalse(o10.equals(o30));

        assertEquals(0, o11.getValue());

        c1 = o10.getContainer();
        c2 = o12.getContainer();
        o11.bind(o10);
        assertTrue(o11.getContainer() == o10.getContainer());
        assertTrue(o11.getContainer() == c1 ^ o11.getContainer() == c2);
        assertTrue(o11.equals(o12));
        assertTrue(o10.equals(o12));
        assertEquals(10, o10.getValue());
        assertEquals(10, o11.getValue());
        assertEquals(10, o12.getValue());

        CCli.bind(o22, o20);

        try {
            o21.bind(o12);
            fail();
        }
        catch ( CannotBind e ) {}

        assertEquals(10, o10.getValue());
        assertEquals(20, o20.getValue());
        assertTrue(o10.getContainer() == o11.getContainer() && o10.getContainer() == o12.getContainer());
        assertTrue(o20.getContainer() == o21.getContainer() && o20.getContainer() == o22.getContainer());

        assertFalse(o30.equals(o10));
        assertFalse(o30.equals(o20));
    }


    @Test
    public void
    preparsedRemove () {
        CCli rep = new CCli(null);

        AbstractOption o10 = (AbstractOption)rep.createOption(OptionTypes.SHORT, "r", 0, ValueTypes.INTEGER, "Int opt");
        AbstractOption o11 = (AbstractOption)rep.createOption(
            OptionTypes.LONG,
            "recurse",
            0,
            ValueTypes.INTEGER,
            "Int opt");
        AbstractOption o12 = (AbstractOption)rep.createOption(
            OptionTypes.CUSTOM,
            "-Frec",
            0,
            ValueTypes.INTEGER,
            "Int opt");

        o11.bind(o12);
        o10.bind(o12);

        DataContainer container = o10.getContainer();

        rep.remove(o10);
        assertFalse(container == o10.getContainer());
        assertTrue(container == o11.getContainer());
        assertTrue(container == o12.getContainer());

        rep.remove(o10);
        assertFalse(container == o10.getContainer());
        assertTrue(container == o11.getContainer());
        assertTrue(container == o12.getContainer());

        rep.remove(o11);
        assertFalse(container == o10.getContainer());
        assertFalse(container == o11.getContainer());
        assertTrue(container == o12.getContainer());

        rep.remove(o12);
        assertFalse(container == o10.getContainer());
        assertFalse(container == o11.getContainer());
        assertFalse(container == o12.getContainer());

        o10 = (AbstractOption)rep.createOption(OptionTypes.SHORT, "r", 0, ValueTypes.INTEGER, "Int opt");
        o11 = (AbstractOption)rep.createOption(OptionTypes.LONG, "recurse", 0, ValueTypes.INTEGER, "Int opt");
        o12 = (AbstractOption)rep.createOption(OptionTypes.CUSTOM, "-Frec", 0, ValueTypes.INTEGER, "Int opt");

        o11.bind(o12);
        o10.bind(o12);

        assertTrue(container != o10.getContainer());
        assertTrue(container != o11.getContainer());
        assertTrue(container != o12.getContainer());
    }


    @Test
    public void
    testHelp () {
        String[] args = {"-v", "10", "--q=true", "-v0", "-v12", "--q", "file", "foo.c"};

        CCli rep = new CCli(args);

        @SuppressWarnings ("unused") AbstractOption o10 = (AbstractOption)rep.createOption(
            OptionTypes.SHORT,
            "r",
            0,
            ValueTypes.INTEGER,
            "Int opt");
        AbstractOption o11 = (AbstractOption)rep.createOption(
            OptionTypes.LONG,
            "recurse",
            0,
            ValueTypes.INTEGER,
            "Int opt");
        AbstractOption o12 = (AbstractOption)rep.createOption(
            OptionTypes.CUSTOM,
            "-Frec",
            0,
            ValueTypes.INTEGER,
            "Int opt");
        @SuppressWarnings ("unused") AbstractOption o20 = (AbstractOption)rep.createOption(
            OptionTypes.SHORT,
            "f",
            0,
            ValueTypes.INTEGER,
            "Int opt");
        AbstractOption o21 = (AbstractOption)rep.createOption(
            OptionTypes.LONG,
            "flip",
            0,
            ValueTypes.INTEGER,
            "Int opt");
        AbstractOption o22 = (AbstractOption)rep.createOption(
            OptionTypes.CUSTOM,
            "-Fflp",
            0,
            ValueTypes.INTEGER,
            "Int opt");
        @SuppressWarnings ("unused") AbstractOption o30 = (AbstractOption)rep.createOption(
            OptionTypes.LONG,
            "vbr-new",
            0,
            ValueTypes.INTEGER,
            "Int opt");

        o11.bind(o12);
        o22.bind(o21);

        assertNotNull(rep.help());
    }


    @Test (expected = UnexpectedEndOfArgumentList.class)
    public void
    unexpectedEndOfArgList () throws Throwable {
        String[] args = {"-ni", "-i", "-z", "awesome", "-z"};

        CCli rep = new CCli(args);
        @SuppressWarnings ("unused") Option opt1 = rep.createOption(
            OptionTypes.SHORT,
            "n",
            false,
            ValueTypes.BOOLEAN,
            "Stereo options");
        @SuppressWarnings ("unused") Option opt2 = rep.createOption(
            OptionTypes.SHORT,
            "i",
            false,
            ValueTypes.BOOLEAN,
            "Quality options");
        @SuppressWarnings ("unused") Option opt3 = rep.createOption(
            OptionTypes.SHORT,
            "z",
            "",
            ValueTypes.STRING,
            "Stereo options");
        try {
            rep.parse();
            fail();
        }
        catch ( CannotParse e ) {
            throw e.getCause();
        }
    }


    @Test (expected = UnexpectedOption.class)
    public void
    unexpectedOption () throws Throwable {
        String[] args = {"-ni", "-i", "unexpected", "-z", "true", "file", "foo.c"};

        CCli rep = new CCli(args);
        @SuppressWarnings ("unused") Option opt1 = rep.createOption(
            OptionTypes.SHORT,
            "n",
            false,
            ValueTypes.BOOLEAN,
            "Stereo options");
        @SuppressWarnings ("unused") Option opt2 = rep.createOption(
            OptionTypes.SHORT,
            "i",
            false,
            ValueTypes.BOOLEAN,
            "Quality options");
        @SuppressWarnings ("unused") Option opt3 = rep.createOption(
            OptionTypes.SHORT,
            "z",
            false,
            ValueTypes.BOOLEAN,
            "Stereo options");
        try {
            rep.parse();
            fail();
        }
        catch ( CannotParse e ) {
            Throwable cause = e.getCause();
            if ( !cause.getMessage().equals("-z") ) fail();
            throw cause;
        }
    }

}
