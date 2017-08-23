package com;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import org.hibernate.Session;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;

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

    @BeforeMethod
    public void dbAllSet() {
        for (String line : DB_UNIT_SET_UP) {
            System.out.println(line);
        }
        System.out.println();
    }

//    @Autowired
    private TestEntityManager entityManager;

    protected Session getSession() {
        return entityManager.getEntityManager().unwrap(Session.class);
    }
}
