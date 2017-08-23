package com.dao.repositories;

import com.dao.entities.Currency;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

/**
 * Created by Admin on 22.04.2017.
 */
public interface ICurrencyRepository {
    Currency findByName(String name);
    Collection<Currency> findByNames(Collection<String> names);
    Collection<Currency> findAll();
    void save(Currency currency);
}
