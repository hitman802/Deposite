package dao.repositories;

import dao.entities.Users;
import lombok.extern.log4j.Log4j;
import org.apache.catalina.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
    public Users loadUserByName(String name) {
        return findUserByName(name);
    }

    @Transactional
    public Users findUserByName(String name) {
        try {
            return (Users) em.createQuery("SELECT c FROM Users c WHERE c.name = :username")
                    .setParameter("username", name)
                    .getSingleResult()
                    ;
        }catch( NoResultException e ) {
            return null;
        }
    }

    @Transactional
    public void saveUser(Users user) {
        em.persist(user);
    }
}
