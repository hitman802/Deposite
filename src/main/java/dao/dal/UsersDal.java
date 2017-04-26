package dao.dal;

import dao.entities.Users;
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
public class UsersDal {

    @PersistenceContext
    private EntityManager em;

    @Transactional(propagation = Propagation.REQUIRED)
    public Users findUser(String name) {
        Users user = em.find(Users.class, name);
        if( user == null ) {
            log.error("Cant find user by id " + name);
        }
        return user;
    }

    @Transactional
    public void addDeposite(String name, String email, String additional) {
        Users user = createUsers(name, email, additional);
        em.persist(user);
    }

    public Users createUsers(String name, String email, String additional) {
        Users user = new Users();
        user.setName(name);
        user.setEmail(email);
        user.setAdditional(additional);
        return user;
    }
}
