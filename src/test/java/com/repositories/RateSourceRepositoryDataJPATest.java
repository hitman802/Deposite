package com.repositories;

import com.BaseDataJPATest;
import com.dao.entities.RateSource;
import com.dao.entities.Role;
import com.dao.repositories.RateSourceRepository;
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
@DatabaseSetup("/ratesource_data.xml")
public class RateSourceRepositoryDataJPATest extends BaseDataJPATest<RateSource, Long> {

    private final List<String> names = Arrays.asList("NBY", "MEGBANK");
    @Autowired
    private RateSourceRepository repository;

    @Override
    public int getCollectionSize() {
        return names.size();
    }

    @Override
    public RateSource createNewEntity() {
        RateSource entity = new RateSource();
        entity.setName(UUID.randomUUID().toString());
        return entity;
    }

    @Test
    public void testFindByName() {
        Assert.assertFalse(names.isEmpty());
        Assert.assertNotNull(repository.findByName(names.get(0)));
    }

}
