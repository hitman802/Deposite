package com.repositories;

import com.BaseDataJPATest;
import com.dao.entities.Replenishment;
import com.dao.entities.Role;
import com.dao.repositories.ReplenishmentRepository;
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
@DatabaseSetup("/replenishment_data.xml")
public class ReplenishmentRepositoryDataJPATest extends BaseDataJPATest<Replenishment, Long> {

    @Autowired
    private ReplenishmentRepository repository;

    @Override
    public int getCollectionSize() {
        return 1;
    }

    @Override
    public Replenishment createNewEntity() {
        return new Replenishment();
    }
}
