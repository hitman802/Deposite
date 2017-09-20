package com;

import com.periodical.RatesUpdater;
import lombok.extern.log4j.Log4j;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.IObjectFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.ObjectFactory;

@Log4j
public class BaseUnitTest extends AbstractTestNGSpringContextTests {
    /*
    * for power mock working
    */
    @ObjectFactory
    public IObjectFactory getObjectFactory() {
        return new org.powermock.modules.testng.PowerMockObjectFactory();
    }

    public void prepareMocks() {
        log.warn("Prepare mock method not overridden");
    };

    @BeforeMethod
    public void setUp() throws Exception {
        prepareMocks();
    }

}
