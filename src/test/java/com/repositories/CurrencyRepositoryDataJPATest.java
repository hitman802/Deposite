package com.repositories;

import com.BaseDataJPATest;
import com.dao.entities.Currency;
import com.dao.repositories.CurrencyRepository;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by Admin on 12.08.2017.
 */
@DatabaseSetup("/data.xml")
public class CurrencyRepositoryDataJPATest extends BaseDataJPATest {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Test
    public void findByName() {
        String currencyName = "UAH";
        Currency currency = currencyRepository.findByName(currencyName);
        Assert.assertNotNull(currency);
        Assert.assertEquals(currencyName, currency.getName());

        Assert.assertNull(currencyRepository.findByName("NOTEXISTING"));
    }

    @Test(expectedExceptions = RuntimeException.class, expectedExceptionsMessageRegExp = "not implemented")
    public void testCustomMethod() {
        currencyRepository.customMethod();
    }
}
