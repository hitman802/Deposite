package com.transaction;

import com.dao.entities.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.concurrent.locks.Lock;

import static java.lang.System.err;

@TestPropertySource("classpath:test-application.properties")
@AutoConfigureTestDatabase
@SpringBootTest
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
public class TransactionTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    ClassForTransaction classForTransaction;

    @Test
    @Transactional
    public void testTransaction() {
        classForTransaction.setToASuccess();
    }

    @Test
    public void testTransaction2() {
        int a = 1;
        try {
            classForTransaction.setABSuccess();
        } catch(Exception e ) {
        }
        Users val = classForTransaction.getUser();
        err.println();
    }
}
