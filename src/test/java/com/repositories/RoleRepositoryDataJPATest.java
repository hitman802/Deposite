package com.repositories;

import com.BaseDataJPATest;
import com.dao.entities.Currency;
import com.dao.entities.Role;
import com.dao.repositories.CurrencyRepository;
import com.dao.repositories.RoleRepository;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by Admin on 12.08.2017.
 */
@DatabaseSetup("/role_data.xml")
public class RoleRepositoryDataJPATest extends BaseDataJPATest<Role, Long> {

    private final List<String> names = Arrays.asList("admin", "user");
    @Autowired
    private RoleRepository repository;

    @Override
    public int getCollectionSize() {
        return names.size();
    }

    @Override
    public Role createNewEntity() {
        Role entity = new Role();
        entity.setName(UUID.randomUUID().toString());
        return entity;
    }

    @Test
    public void testFindByNameIn() {
        Assert.assertFalse(names.isEmpty());
        int size = names.size()-1;
        Assert.assertEquals(repository.findByNameIn(names.stream().limit(size).collect(Collectors.toList())).size(), size);
    }

}
