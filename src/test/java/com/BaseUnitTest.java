package com;

import com.periodical.RatesUpdater;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.IObjectFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.ObjectFactory;

public abstract class BaseUnitTest extends AbstractTestNGSpringContextTests {
    /*
    * for power mock working
    */
    @ObjectFactory
    public IObjectFactory getObjectFactory() {
        return new org.powermock.modules.testng.PowerMockObjectFactory();
    }

    public abstract void prepareMocks();

    @BeforeMethod
    public void setUp() throws Exception {
        prepareMocks();
    }

}
