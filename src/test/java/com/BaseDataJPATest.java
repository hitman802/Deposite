package com;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import lombok.Getter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 12.08.2017.
 */
/*
@DataJpaTest - for testing spring data repositories. only SPRING-DATA!!!
 */
/* to configure h2 in memmory db
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
to configure h2 in memmory db*/
@TestPropertySource("classpath:test-application.properties")
@TestExecutionListeners({DbUnitTestExecutionListener.class}) //for working @DatabaseSetup
/*
AbstractTransactionalTestNGSpringContextTests - FOR TESTS WITH TRANSACTION!!!
http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#testcontext-support-classes-testng
*/
@DataJpaTest
public abstract class BaseDataJPATest<T, ID extends Serializable> extends AbstractTransactionalTestNGSpringContextTests {
    @Autowired
    private TestEntityManager entityManager;
    private Session session;
    @Autowired
    @Getter private CrudRepository<T, ID> repository;
    private int collectionSize;

    @BeforeMethod
    public void dbAllSet() {
        session = entityManager.getEntityManager().unwrap(Session.class);
    }

    public abstract int getCollectionSize();

    public abstract T createNewEntity();

    @BeforeClass
    public void beforeClass() {
        collectionSize = getCollectionSize();
    }

    private List<T> iterableToArrayList(Iterable<T> iterable) {
        List<T> list = new ArrayList<>();
        for (T anIterable : iterable) {
            list.add(anIterable);
        }
        return list;
    }

    //basic crud operations test
    @Test
    public void testFindAll() {
        Assert.assertEquals(iterableToArrayList(repository.findAll()).size(), collectionSize);
    }

    @Test
    public void testSave() {
        T entity = createNewEntity();
        Assert.assertEquals(iterableToArrayList(repository.findAll()).size(), collectionSize);
        repository.save(entity);
        List<T> entities = iterableToArrayList(repository.findAll());
        Assert.assertEquals(entities.size(), collectionSize+1);
        Assert.assertTrue(entities.contains(entity));
    }

    @Test
    public void testDelete() {
        List<T> entities = iterableToArrayList(repository.findAll());
        Assert.assertEquals(entities.size(), collectionSize);
        T entity = entities.get(0);
        Assert.assertNotNull(entity);
        repository.delete(entity);
        Assert.assertEquals(iterableToArrayList(repository.findAll()).size(), collectionSize-1);
    }

    @Test
    public void testDeleteAll() {
        Assert.assertEquals(iterableToArrayList(repository.findAll()).size(), collectionSize);
        repository.deleteAll();
        Assert.assertTrue(iterableToArrayList(repository.findAll()).isEmpty());
    }
    //basic crud operations test
}
