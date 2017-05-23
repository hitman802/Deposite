package dao.repositories;

import dao.entities.Role;
import dao.entities.Users;
import exceptions.UniqueUserEmailException;
import exceptions.UniqueUserNameException;
import exceptions.UserNotFoundException;
import factory.UserFactory;
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
import java.util.Collection;
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
    @Autowired
    private UserFactory userFactory;

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

    @Transactional
    public void addUser(String name, String password, String email, List<String> roles) {
        Users user = userFactory.create();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setRoles(roleRepository.findRoles(roles));
        saveUser(user);
    }

    @Transactional
    public Users findUserByEmail(String email) {
     try {
            return (Users) em.createQuery("SELECT u from Users u WHERE u.email = :useremail")
                    .setParameter("useremail", email)
                    .getSingleResult();
        } catch( NoResultException e ) {
            return null;
        }
    }

    @Transactional
    public Users findUserByNameWithDifferentId(Long id, String name) {
        try {
            return (Users) em.createQuery("SELECT u from Users u WHERE u.id != :userid AND u.name = :username")
                    .setParameter("userid", id)
                    .setParameter("username", name)
                    .getSingleResult();
        } catch( NoResultException e ) {
            return null;
        }
    }

    @Transactional
    public Users findUserByEmailWithDifferentId(Long id, String email) {
        try {
            return (Users) em.createQuery("SELECT u from Users u WHERE u.id != :userid AND u.email = :useremail")
                    .setParameter("userid", id)
                    .setParameter("useremail", email)
                    .getSingleResult();
        } catch( NoResultException e ) {
            return null;
        }
    }
}
