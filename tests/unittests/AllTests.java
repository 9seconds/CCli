package unittests;


import org.aerialsounds.ccli.ValueTypesTests;
import org.aerialsounds.ccli.datacontainer.DataContainerTest;
import org.aerialsounds.ccli.optionobservable.ObservableTests;
import org.aerialsounds.ccli.options.AbstractOptionsTest;
import org.aerialsounds.ccli.options.NumericalOptionTests;
import org.aerialsounds.ccli.options.ParseableOptionTests;
import org.aerialsounds.ccli.options.ShortOptionTests;
import org.aerialsounds.ccli.valueparsers.ValueParserTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
    ValueParserTest.class,
    DataContainerTest.class,
    AbstractOptionsTest.class,
    ParseableOptionTests.class,
    ShortOptionTests.class,
    NumericalOptionTests.class,
    ObservableTests.class,
    ValueTypesTests.class
})
public class AllTests {

}
