package com.dao.repositories;

import com.dao.entities.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.*;

/**
 * Created by Admin on 22.04.2017.
 */
public interface RoleRepository extends CrudRepository<Role, Long> {
    Set<Role> findByNameIn(Collection<String> names);
}
