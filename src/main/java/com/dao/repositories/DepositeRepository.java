package com.dao.repositories;

import com.dao.entities.Deposite;
import org.springframework.data.repository.CrudRepository;

import java.util.*;

/**
 * Created by Admin on 22.04.2017.
 */
public interface DepositeRepository extends CrudRepository<Deposite, Long> {
    List<Deposite> findByUser_Name(String userName);
    List<Deposite> findByIdIn(Collection<Long> ids);
}
