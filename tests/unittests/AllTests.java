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



package unittests;



import org.aerialsounds.ccli.CCliTests;
import org.aerialsounds.ccli.CliFactoryTests;
import org.aerialsounds.ccli.OptionTypesTests;
import org.aerialsounds.ccli.ValueTypesTests;
import org.aerialsounds.ccli.datacontainer.DataContainerTests;
import org.aerialsounds.ccli.optionobservable.ObservableTests;
import org.aerialsounds.ccli.options.AbstractOptionsTests;
import org.aerialsounds.ccli.options.LongOptionTests;
import org.aerialsounds.ccli.options.NumericalOptionTests;
import org.aerialsounds.ccli.options.ParseableOptionTests;
import org.aerialsounds.ccli.options.ShortOptionTests;
import org.aerialsounds.ccli.valueparsers.ValueParserTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;



@RunWith (Suite.class)
@Suite.SuiteClasses ({
    ValueParserTests.class,
    DataContainerTests.class,
    AbstractOptionsTests.class,
    ParseableOptionTests.class,
    ShortOptionTests.class,
    NumericalOptionTests.class,
    LongOptionTests.class,
    ObservableTests.class,
    ValueTypesTests.class,
    OptionTypesTests.class,
    CliFactoryTests.class,
    CCliTests.class
})
public class AllTests {

}
