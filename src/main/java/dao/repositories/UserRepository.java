package dao.repositories;

import dao.entities.Role;
import dao.entities.Users;
import exceptions.UniqueUserEmailException;
import exceptions.UniqueUserNameException;
import exceptions.UserNotFoundException;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Admin on 22.04.2017.
 */
@Log4j
@Repository
@Scope(scopeName = "singleton")
public class UserRepository {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private RoleRepository roleRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public Users loadUserByName(String name) {
        return findUserByName(name);
    }

    @Transactional
    public List<Users> loadAllUsers() {
        return em.createQuery( "SELECT c FROM Users c")
                .getResultList()
                ;
    }

    @Transactional
    public Users findUserById(Long id) {
        try {
            return (Users) em.createQuery("SELECT c FROM Users c WHERE c.id = :userid")
                    .setParameter("userid", id)
                    .getSingleResult()
                    ;
        }catch( NoResultException e ) {
            return null;
        }
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

    @Transactional
    public void validateUserNameAndEmail(Long id, String name, String email) {
        validateUserName(id, name);
        validateUserEmail(id, email);
    }

    /**
     * throws runtime exception in case if username not unique
     * @param id - user id
     * @param name - user name to check
     */
    @Transactional
    private void validateUserName(Long id, String name){
        if( !em.createQuery("SELECT u from Users u WHERE u.id != :userid AND u.name = :username")
                .setParameter("userid", id)
                .setParameter("username", name)
                .getResultList()
                .isEmpty() ) {
            throw new UniqueUserNameException();
        }
    }

    /**
     * throws runtime exception in case if user email not unique
     * @param id - user id
     * @param email - user email to check
     */
    @Transactional
    private void validateUserEmail(Long id, String email){
         if( !em.createQuery("SELECT u from Users u WHERE u.id != :userid AND u.email = :useremail")
                .setParameter("userid", id)
                .setParameter("useremail", email)
                .getResultList()
                .isEmpty() ) {
             throw new UniqueUserEmailException();
         }
    }

    @Transactional
    public void deleteUser(Long id) {
        Users user = findUserById(id);
        if( user == null ) {
            throw new UserNotFoundException();
        }
        em.remove(user);
    }

    @Transactional
    public void updateUser(Long id, String name, String email, List<String> roles) {
        Users user = findUserById(id);
        if( user == null ) {
            throw new UserNotFoundException();
        }
        user.setName(name);
        user.setEmail(email);
        user.setRoles(roleRepository.findRoles(roles));
    }
}
