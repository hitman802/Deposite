package com.dao.repositories;

import com.dao.entities.Users;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Admin on 22.04.2017.
 */
public interface UserRepository extends CrudRepository<Users, Long> {
    Users findByName(String name);
    Users findByNameAndIdNot(String name, Long id);
    Users findByEmailAndIdNot(String email, Long id);
}
