package dao.repository;

import dao.entities.User;
import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Admin on 22.04.2017.
 */
@Log4j
@Repository
@Scope(scopeName = "singleton")
public class UserRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional(propagation = Propagation.REQUIRED)
    public User loadUserByName(String name) {
        User user = em.find(User.class, name);
        if( user == null ) {
            log.error("Cant find user by id " + name);
        }
        return user;
    }

    @Transactional
    public void addUser(String name, String email) {
        User user = createUser(name, email);
        em.persist(user);
    }

    @Transactional
    public void removeUserByName(String name) {
        User user = loadUserByName(name);
        if( user == null ) {
            log.error("Cant find user to delete it username" + name);
            return;
        }
        em.remove(user);
    }

    private User createUser(String name, String email) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        return user;
    }
}
