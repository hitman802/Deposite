package com.custom;

import com.dao.entities.Deposite;
import com.dao.entities.Users;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.sqltracker.AssertSqlCount;
import org.hibernate.Session;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.System.err;

@DataJpaTest
@DatabaseSetup("/whole_test.xml")
@TestPropertySource("classpath:test-application.properties")
@TestExecutionListeners({DbUnitTestExecutionListener.class})
public class TryingToDealWithHibernateTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private TestEntityManager entityManager;
    private Session session;

    @Test
    public void updateChildEntity() {
        AssertSqlCount.assertSelectCount(0);
        Users users = new Users();
        users.setId(new Random().nextLong());
        users.setName(UUID.randomUUID().toString());
        users.setEmail(UUID.randomUUID().toString()+"@fakemail.com");
        users.setPassword(UUID.randomUUID().toString());
        users.setRoles(Collections.emptySet());

        AssertSqlCount.assertSelectCount(0);
        Set<Deposite> deposits = Stream.of(1L, 2L, 3L)
                .map(id->session.get(Deposite.class, id))
                .collect(Collectors.toSet());
                ;
        //3 select for deposit
        AssertSqlCount.assertSelectCount(3);
        users.setDeposits(deposits);
        deposits.forEach(deposit->deposit.setUser(users));
        AssertSqlCount.reset();

        session.save(users);
        //insert user
        AssertSqlCount.assertInsertCount(1);
        AssertSqlCount.reset();

        session.flush();
        session.clear();

        //updates 3 deposites
        AssertSqlCount.assertUpdateCount(3);
        AssertSqlCount.reset();

        Deposite deposite = new Deposite();
        deposite.setUser(users);
        deposite.setId(new Random().nextLong());
        users.getDeposits().add(deposite);

        AssertSqlCount.assertSelectCount(0);
        AssertSqlCount.assertInsertCount(0);
        AssertSqlCount.assertUpdateCount(0);
        session.update(users);
        session.save(deposite);
        AssertSqlCount.assertSelectCount(0);
        AssertSqlCount.assertInsertCount(1);
        AssertSqlCount.assertUpdateCount(0);
        session.flush();
        session.clear();
        AssertSqlCount.assertUpdateCount(1);
        Assert.assertNotNull(session.get(Deposite.class, deposite.getId()));
        AssertSqlCount.assertSelectCount(1);
    }

    @Test
    public void updateEntityById() {
        AssertSqlCount.assertSelectCount(0);
        Users users = new Users();
        users.setId(new Random().nextLong());
        users.setName(UUID.randomUUID().toString());
        users.setEmail(UUID.randomUUID().toString()+"@fakemail.com");
        users.setPassword(UUID.randomUUID().toString());
        users.setRoles(Collections.emptySet());
        users.setDeposits(Collections.emptySet());

        session.update(users);
        session.evict(users);

        Users userGet = session.get(Users.class, users.getId());
        Assert.assertNull(userGet);
    }

    @Test
    public void saveEntityById() {
        AssertSqlCount.assertSelectCount(0);
        Users users = new Users();
        users.setId(new Random().nextLong());
        users.setName(UUID.randomUUID().toString());
        users.setEmail(UUID.randomUUID().toString()+"@fakemail.com");
        users.setPassword(UUID.randomUUID().toString());
        users.setRoles(Collections.emptySet());
        users.setDeposits(Collections.emptySet());

        err.println("before save");
        session.save(users);
        err.println("after save");
        AssertSqlCount.assertInsertCount(1);
        AssertSqlCount.reset();

        //clear session cache (1st lvl)
        session.flush();
        session.clear();

        err.println("before get");
        Users usersLoaded = session.get(Users.class, users.getId());
        err.println("after get");
        AssertSqlCount.assertSelectCount(1);
        Assert.assertEquals(usersLoaded, users);
        err.println("after equals");

    }

    @Test
    public void loadEntityById() {
        AssertSqlCount.assertSelectCount(0);
        Users users = session.load(Users.class, 1L);
        //since load is used - no select performed
        AssertSqlCount.assertSelectCount(0);
        users.getName();
        //after getName - select performed
        AssertSqlCount.assertSelectCount(1);

        AssertSqlCount.reset();
        Set<Deposite> deposits = users.getDeposits();
        //since load is used - no select performed
        AssertSqlCount.assertSelectCount(0);

        deposits.forEach(Deposite::getName);
        //1 select for 2 deposit names
        AssertSqlCount.assertSelectCount(1);
        AssertSqlCount.reset();
    }

    @Test
    public void getEntityById() {
        AssertSqlCount.assertSelectCount(0);
        Users users = session.get(Users.class, 1L);
        //since get is used - select performed
        AssertSqlCount.assertSelectCount(1);
        AssertSqlCount.reset();
        users.getName();
        //after getName - no select performed
        AssertSqlCount.assertSelectCount(0);

        AssertSqlCount.reset();
        Set<Deposite> deposits = users.getDeposits();
        //no select performed because of its loaded already
        AssertSqlCount.assertSelectCount(0);

        deposits.forEach(Deposite::getName);
        //1 select for 2 deposit names
        AssertSqlCount.assertSelectCount(1);
        AssertSqlCount.reset();
    }

    @BeforeMethod
    public void beforeEachMethod() {
        session = entityManager.getEntityManager().unwrap(Session.class);
        AssertSqlCount.reset();
    }
    @AfterMethod
    public void afterEachMethod() {
    }
}
