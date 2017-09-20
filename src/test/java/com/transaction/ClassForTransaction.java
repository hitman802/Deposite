package com.transaction;

import com.dao.entities.Users;
import com.dao.repositories.UserRepository;
import com.repositories.UsersRepositoryDataJPATest;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.UUID;

@Component
public class ClassForTransaction {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClassForTransaction classForTransaction;

    private Users createNewUser() {
        Users user = new Users();
        user.setName("Default name");
        user.setPassword(UUID.randomUUID().toString());
        user.setEmail(UUID.randomUUID().toString()+"@fakemail.com");
        return user;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void setToASuccess() {
        Users user = userRepository.findByName("Default name");
        user.setName("A");
        userRepository.save(user);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void setToBFailure() {

        Users user = userRepository.findByName("A");
        user.setName("B");
        userRepository.save(user);

        throw new RuntimeException("what to do");
    }

    public Users getUser() {
        Users user = userRepository.findByName("A");
        if( user == null ) {
            user = userRepository.findByName("Default name");
        }
        return user;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void setABSuccess() {
        userRepository.save(createNewUser());
        classForTransaction.setToASuccess();
        classForTransaction.setToBFailure();
    }
}
