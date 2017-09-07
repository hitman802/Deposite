package com.repositories;

import com.BaseDataJPATest;
import com.dao.entities.Rate;
import com.dao.entities.Role;
import com.dao.repositories.RatesRepository;
import com.dao.repositories.RoleRepository;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by Admin on 12.08.2017.
 */
@DatabaseSetup("/rates_data.xml")
public class RatesRepositoryDataJPATest extends BaseDataJPATest<Rate, Long> {

    @Autowired
    private RatesRepository repository;
    private final String currencyName1 = "UAH";
    private final String currencyName2 = "USD";

    @Override
    public int getCollectionSize() {
        return 2;
    }

    @Override
    public Rate createNewEntity() {
        return new Rate();
    }

    @Test
    public void testFindByCurrency_Name_AndDate() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Assert.assertNotNull(repository.findByCurrency_Name_AndDate(currencyName1, sdf.parse("2017-03-28")));
        Assert.assertNull(repository.findByCurrency_Name_AndDate(currencyName1, sdf.parse("2017-03-29")));
        Assert.assertNull(repository.findByCurrency_Name_AndDate(currencyName2, sdf.parse("2017-03-28")));
    }

}
