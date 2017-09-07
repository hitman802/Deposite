package com.repositories;

import com.BaseDataJPATest;
import com.dao.entities.Deposite;
import com.dao.entities.Role;
import com.dao.repositories.DepositeRepository;
import com.dao.repositories.RoleRepository;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by Admin on 12.08.2017.
 */
@DatabaseSetup("/deposit_data.xml")
public class DepositRepositoryDataJPATest extends BaseDataJPATest<Deposite, Long> {

    private final List<String> names = Arrays.asList("deposit1", "deposit2", "deposit3", "deposit4");
    private final List<String> userNames = Arrays.asList("sergeyg", "sergeygg", "sergeyggg", "sergeygggg");
    private final List<Long> ids = Arrays.asList(1L, 2L, 3L, 4L);

    @Autowired
    private DepositeRepository repository;

    @Override
    public int getCollectionSize() {
        return names.size();
    }

    @Override
    public Deposite createNewEntity() {
        Deposite entity = new Deposite();
        entity.setName(UUID.randomUUID().toString());
        return entity;
    }

    @Test
    public void testFindByNameIn() {
        List<Long> idsToGet = ids.stream().limit(ids.size()-1).collect(Collectors.toList());
        Assert.assertEquals(repository.findByIdIn(idsToGet).size(), idsToGet.size());
    }

    @Test
    public void testFindByUser_Name() {
        Assert.assertFalse(userNames.isEmpty());
        Assert.assertNotNull(repository.findByUser_Name(userNames.get(0)));
    }

}
