package com.dao.repositories;

import com.dao.entities.Currency;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Admin on 22.04.2017.
 */
public interface CurrencyRepository extends CrudRepository<Currency, Long> {
    Currency findByName(String name);
}
