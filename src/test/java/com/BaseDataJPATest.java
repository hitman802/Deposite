package com;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.annotations.BeforeMethod;

import java.util.Arrays;
import java.util.stream.Stream;

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
public abstract class BaseDataJPATest extends AbstractTransactionalTestNGSpringContextTests {
    private static final String[] DB_UNIT_SET_UP = {"",
            "****************************************************************",
            "*************** DATABASE HAS BEEN ALREADY SET UP ***************",
            "****************************************************************"
    };

    @Autowired
    private TestEntityManager entityManager;
    protected Session session;

    @BeforeMethod
    public void dbAllSet() {
        Stream.of(DB_UNIT_SET_UP).forEach(System.out::println);
        session = entityManager.getEntityManager().unwrap(Session.class);
    }
}
