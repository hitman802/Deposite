package com.dao.repositories;

import com.dao.entities.Rate;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;

/**
 * Created by Admin on 22.04.2017.
 */
public interface RatesRepository extends CrudRepository<Rate, Long> {
    Rate findByCurrency_Name_AndDate(String name, Date date);
}
