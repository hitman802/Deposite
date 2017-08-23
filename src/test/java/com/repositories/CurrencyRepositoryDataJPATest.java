package com.repositories;

import com.BaseDataJPATest;
import com.dao.entities.Currency;
import com.dao.repositories.CurrencyRepository;
import com.dao.repositories.ICurrencyRepository;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.testng.annotations.Test;

/**
 * Created by Admin on 12.08.2017.
 */
@DatabaseSetup("/data.xml")
public class CurrencyRepositoryDataJPATest extends BaseDataJPATest {

    @Autowired
    private ICurrencyRepository currencyRepository;

    @Test
    public void first() {
        Currency currency = currencyRepository.findByName("UAH");
    }

}
