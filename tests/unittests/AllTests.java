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


@RunWith(Suite.class)
@Suite.SuiteClasses({
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
