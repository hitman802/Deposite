package com.dao.repositories;

import com.dao.entities.RateSource;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Admin on 22.04.2017.
 */
public interface RateSourceRepository extends CrudRepository<RateSource, Long> {
    RateSource findByName(String name);
}
