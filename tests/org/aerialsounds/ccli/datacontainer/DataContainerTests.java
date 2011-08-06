package org.aerialsounds.ccli.datacontainer;


import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.aerialsounds.ccli.CCli;
import org.aerialsounds.ccli.ValueTypes;
import org.aerialsounds.ccli.datacontainer.DataContainer.DataContainerException;
import org.aerialsounds.ccli.datacontainer.DataContainer.OverrideDefaultValue;
import org.aerialsounds.ccli.datacontainer.DataContainer.OverrideHelp;
import org.aerialsounds.ccli.datacontainer.DataContainer.OverrideRepository;
import org.aerialsounds.ccli.datacontainer.DataContainer.OverrideValue;
import org.aerialsounds.ccli.datacontainer.DataContainer.OverrideValueType;
import org.junit.Test;



public class DataContainerTests {

    @Test
    public void create() {
        DataContainer withoutRepository = new DataContainer();
        DataContainer withNullRepository = new DataContainer(null);
        DataContainer withRepository = new DataContainer(new CCli(null));
        DataContainer other = new DataContainer() {

        };

        assertEquals(withoutRepository, withNullRepository);
        assertEquals(withoutRepository, withNullRepository);
        assertFalse(withoutRepository.equals(withRepository));

        assertNull(withoutRepository.getHelp());
        assertNull(withNullRepository.getHelp());
        assertNull(withRepository.getHelp());

        assertNull(withoutRepository.getValueType());
        assertNull(withNullRepository.getValueType());
        assertNull(withRepository.getValueType());

        assertNull(withoutRepository.getValue());
        assertNull(withNullRepository.getValue());
        assertNull(withRepository.getValue());

        assertNull(withoutRepository.getDefaultValue());
        assertNull(withNullRepository.getDefaultValue());
        assertNull(withRepository.getDefaultValue());

        assertNull(withoutRepository.getRepository());
        assertNull(withNullRepository.getRepository());
        assertNotNull(withRepository.getRepository());

        assertFalse(withoutRepository.isDefined());
        assertFalse(withNullRepository.isDefined());
        assertFalse(withRepository.isDefined());

        assertFalse(withRepository.equals(other));
        assertFalse(withoutRepository.equals(other));
    }

    @Test
    public void settingHelp() {
        DataContainer container = new DataContainer();

        assertNull(container.getHelp());

        container.setHelp("help");
        assertEquals("help", container.getHelp());

        container.setHelp(null);
        assertNull(container.getHelp());
    }

    @Test
    public void settingDefaultValue() {
        DataContainer container = new DataContainer();

        assertNull(container.getDefaultValue());
        assertNull(container.getValue());

        container.setDefaultValue(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, container.getDefaultValue());
        assertNull(container.getValue());

        container.setDefaultValue(Integer.MIN_VALUE);
        assertEquals(Integer.MIN_VALUE, container.getDefaultValue());
        assertNull(container.getValue());

        container.setDefaultValue(null);
        assertNull(container.getDefaultValue());
        assertNull(container.getValue());
    }

    @Test
    public void settingValue() {
        DataContainer container = new DataContainer();

        assertNull(container.getValue());
        assertFalse(container.isDefined());

        container.setValue(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, container.getValue());
        assertTrue(container.isDefined());

        container.setValue(Integer.MIN_VALUE);
        assertEquals(Integer.MIN_VALUE, container.getValue());
        assertTrue(container.isDefined());

        container.setValue(null);
        assertNull(container.getValue());
        assertFalse(container.isDefined());
    }

    @Test
    public void settingValueType() {
        DataContainer container = new DataContainer();

        assertNull(container.getValueType());

        container.setValueType(ValueTypes.INTEGER);
        assertEquals(ValueTypes.INTEGER, container.getValueType());

        container.setValueType(null);
        assertNull(container.getValueType());
    }

    @Test
    public void checkDefined() {
        DataContainer container = new DataContainer();

        assertNull(container.getValue());
        assertFalse(container.isDefined());

        container.setValue(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, container.getValue());
        assertTrue(container.isDefined());

        container.dropDefined();
        assertFalse(container.isDefined());
        assertNull(container.getValue());

        container.setValue(null);
        assertFalse(container.isDefined());
    }

    @Test
    public void simpleSynchronization() {
        DataContainer one = new DataContainer();
        DataContainer another = new DataContainer();

        DataContainer.synchronize(one, one);
        assertEquals(one, another);

        one.setHelp("help");
        DataContainer.synchronize(one, one);
        assertFalse(one.equals(another));

        DataContainer.synchronize(one, another);
        assertEquals(one, another);
        assertEquals("help", another.getHelp());

        another.setDefaultValue(Integer.MAX_VALUE);
        DataContainer.synchronize(one, another);
        assertEquals(one, another);
        assertEquals(Integer.MAX_VALUE, one.getDefaultValue());
        assertEquals("help", one.getHelp());

        one.setValue(Double.POSITIVE_INFINITY);
        DataContainer.synchronize(one, another);
        assertEquals(one, another);
        assertEquals(Double.POSITIVE_INFINITY, another.getValue());
        assertTrue(another.isDefined());

        another.setValueType(ValueTypes.INTEGER);
        DataContainer.synchronize(one, another);
        assertEquals(one, another);
        assertEquals(ValueTypes.INTEGER, one.getValueType());
        assertNull(one.getRepository());
    }

    @Test(expected = OverrideRepository.class)
    public void synchronizationWithDifferentRepositories() {
        DataContainer one = new DataContainer(new CCli(null));
        DataContainer another = new DataContainer(new CCli(null));

        DataContainer.synchronize(one, another);
    }

    @Test(expected = OverrideHelp.class)
    public void synchronizationWithDifferentHelps() {
        DataContainer one = new DataContainer();
        DataContainer another = new DataContainer();
        one.setHelp("111");
        another.setHelp("222");

        DataContainer.synchronize(one, another);
    }

    @Test(expected = OverrideValue.class)
    public void synchronizationWithDifferentValues() {
        DataContainer one = new DataContainer();
        DataContainer another = new DataContainer();
        one.setValue("111");
        another.setValue("222");

        DataContainer.synchronize(one, another);
    }

    @Test(expected = OverrideDefaultValue.class)
    public void synchronizationWithDifferentDefaultValues() {
        DataContainer one = new DataContainer();
        DataContainer another = new DataContainer();
        one.setDefaultValue("111");
        another.setDefaultValue("222");

        DataContainer.synchronize(one, another);
    }

    @Test(expected = OverrideValueType.class)
    public void synchronizationWithDifferentValueTypes() {
        DataContainer one = new DataContainer();
        DataContainer another = new DataContainer();
        one.setValueType(ValueTypes.INTEGER);
        another.setValueType(ValueTypes.BIG_INTEGER);

        DataContainer.synchronize(one, another);
    }

    @Test
    public void synchronizationWithRollback() {
        DataContainer one = new DataContainer(new CCli(null));
        DataContainer another = new DataContainer(new CCli(null));

        one.setHelp("111");
        one.setValueType(ValueTypes.INTEGER);
        one.setValue(BigDecimal.ONE);
        one.setDefaultValue(BigDecimal.TEN);

        another.setHelp("222");
        another.setValueType(ValueTypes.BIG_INTEGER);
        another.setDefaultValue(BigInteger.TEN);

        try {
            DataContainer.synchronize(one, another);
            fail();
        }
        catch ( DataContainerException e ) {

        }

        assertFalse(one.equals(another));

        assertEquals("111", one.getHelp());
        assertEquals(ValueTypes.INTEGER, one.getValueType());
        assertEquals(BigDecimal.ONE, one.getValue());
        assertEquals(BigDecimal.TEN, one.getDefaultValue());
        assertTrue(one.isDefined());

        assertEquals("222", another.getHelp());
        assertEquals(ValueTypes.BIG_INTEGER, another.getValueType());
        assertEquals(BigInteger.TEN, another.getDefaultValue());
        assertNull(another.getValue());
        assertFalse(another.isDefined());
    }

    @Test
    public void equal() {
        DataContainer one = new DataContainer(null);
        DataContainer another = new DataContainer();

        assertEquals(one, one);
        assertEquals(another, another);
        assertEquals(one, another);
        assertEquals(another, one);

        one.setHelp("111");
        one.setDefaultValue(BigInteger.ZERO);
        one.setValue(BigInteger.TEN);
        one.setValueType(ValueTypes.BIG_INTEGER);

        DataContainer.synchronize(another, one);

        assertEquals(one, one);
        assertEquals(another, another);
        assertEquals(one, another);
        assertEquals(another, one);
    }

    @Test
    public void checkConsistency() {
        CCli repo = new CCli(null);
        DataContainer one = new DataContainer(repo);

        assertFalse(one.isConsistent());

        one.setHelp("111");
        assertFalse(one.isConsistent());

        one.setDefaultValue(BigInteger.ZERO);
        assertFalse(one.isConsistent());

        one.setValue(BigInteger.TEN);
        assertFalse(one.isConsistent());

        one.setValueType(ValueTypes.BIG_DECIMAL);
        assertFalse(one.isConsistent());

        one.setValueType(ValueTypes.BIG_INTEGER);
        assertTrue(one.isConsistent());

        DataContainer another = new DataContainer(repo);

        DataContainer.synchronize(one, another);
        assertTrue(another.isConsistent());

        another.setHelp(null);
        assertFalse(another.isConsistent());
        another.setHelp("111");
        assertTrue(another.isConsistent());

        another.setDefaultValue(null);
        assertFalse(another.isConsistent());
        another.setDefaultValue(BigInteger.ZERO);
        assertTrue(another.isConsistent());

        another.setValue(null);
        assertTrue(another.isConsistent());
        another.setValue(BigInteger.TEN);
        assertTrue(another.isConsistent());

        another.setValueType(null);
        assertFalse(another.isConsistent());
        another.setValueType(ValueTypes.INTEGER);
        assertFalse(another.isConsistent());
        another.setValueType(ValueTypes.BIG_INTEGER);
        assertTrue(another.isConsistent());
    }

}
