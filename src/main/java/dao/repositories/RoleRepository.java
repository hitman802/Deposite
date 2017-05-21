package dao.repositories;

import dao.entities.Users;
import dao.entities.Role;
import factory.UserRoleFactory;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;
import java.util.stream.Collectors;

import static org.apache.coyote.http11.Constants.a;

/**
 * Created by Admin on 22.04.2017.
 */
@Log4j
@Repository
@Scope(scopeName = "singleton")
public class RoleRepository {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private UserRoleFactory userRoleFactory;

    @Transactional(propagation = Propagation.REQUIRED)
    public Role addRole(String name) {
        Role role = findRoleByName(name);
        if( role != null ) {
            return role;
        }
        role = userRoleFactory.create();
        role.setName(name);
        em.persist(role);
        return role;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateRole(String name, Collection<Users> users) {
        Role role = findRoleByName(name);
        if( role == null ) {
            return;
        }
        role.getUsers().addAll(users);
        em.merge(role);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Role> loadAll() {
        Query query = em.createQuery("SELECT e FROM Role e");
        return query.getResultList();
    }

    public Set<Role> findRoles(List<String> roles) {
        return roles
                .stream()
                .map(this::findRoleByName)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    private Role findRoleByName(String name) {
        return em.createQuery("SELECT r FROM Role r WHERE r.name = :rolename", Role.class)
                .setParameter("rolename", name).getSingleResult();
    }
}
