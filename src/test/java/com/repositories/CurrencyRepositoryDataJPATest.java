package com.repositories;

import com.BaseDataJPATest;
import com.dao.entities.Currency;
import com.dao.repositories.CurrencyRepository;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Created by Admin on 12.08.2017.
 */
@DatabaseSetup("/currency_data.xml")
public class CurrencyRepositoryDataJPATest extends BaseDataJPATest<Currency, Long> {

    private final List<String> names = Arrays.asList("UAH", "USD", "RUB");
    @Autowired
    private CurrencyRepository repository;

    @Override
    public int getCollectionSize() {
        return names.size();
    }

    @Override
    public Currency createNewEntity() {
        Currency currency = new Currency();
        currency.setName(UUID.randomUUID().toString());
        return currency;
    }

    @Test
    public void testFindByName() {
        Assert.assertFalse(names.isEmpty());
        String currencyName = names.get(0);
        Currency currency = repository.findByName(currencyName);
        Assert.assertNotNull(currency);
        Assert.assertEquals(currencyName, currency.getName());
        Assert.assertNull(repository.findByName("NOTEXISTING"));
    }

    @Test(expectedExceptions = RuntimeException.class, expectedExceptionsMessageRegExp = "not implemented")
    public void testCustomMethod() {
        repository.customMethod();
    }

}
