package com.repositories;

import com.BaseDataJPATest;
import com.dao.entities.Users;
import com.dao.repositories.UserRepository;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.UUID;

/**
 * Created by Admin on 12.08.2017.
 */
@DatabaseSetup("/users_data.xml")
public class UsersRepositoryDataJPATest extends BaseDataJPATest<Users, Long> {

    private long id = 1L;

    @Autowired
    private UserRepository repository;

    @Override
    public int getCollectionSize() {
        return 3;
    }

    @Override
    public Users createNewEntity() {
        Users entity = new Users();
        entity.setName(UUID.randomUUID().toString());
        entity.setPassword(UUID.randomUUID().toString());
        entity.setEmail(UUID.randomUUID().toString());
        return entity;
    }

    @Test
    public void testFindByEmailAndIdNot() {
        String email = "sergeyg@fakemail.com";
        Assert.assertNull(repository.findByEmailAndIdNot(email, id));
        String email2 = "sergeygg@fakemail.com";
        Assert.assertNotNull(repository.findByEmailAndIdNot(email2, id));
    }

    @Test
    public void findByNameAndIdNot() {
        String name1 = "sergeyg";
        Assert.assertNull(repository.findByNameAndIdNot(name1, id));
        String name2 = "sergeygg";
        Assert.assertNotNull(repository.findByNameAndIdNot(name2, id));
    }
}
