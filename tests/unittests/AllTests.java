package unittests;


import org.aerialsounds.ccli.ValueTypesTests;
import org.aerialsounds.ccli.datacontainer.DataContainerTest;
import org.aerialsounds.ccli.optionobservable.ObservableTests;
import org.aerialsounds.ccli.options.AbstractOptionsTest;
import org.aerialsounds.ccli.valueparsers.ValueParserTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
    ValueParserTest.class,
    DataContainerTest.class,
    AbstractOptionsTest.class,
    ObservableTests.class,
    ValueTypesTests.class
})
public class AllTests {

}
