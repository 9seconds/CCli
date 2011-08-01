package unittests;


import org.aerialsounds.ccli.datacontainer.DataContainerTest;
import org.aerialsounds.ccli.valueparsers.ValueParserTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
    ValueParserTest.class,
    DataContainerTest.class
})
public class AllTests {

}
