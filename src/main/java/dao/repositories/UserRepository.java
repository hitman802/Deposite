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
import org.springframework.data.repository.CrudRepository;
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
public interface UserRepository extends CrudRepository<Users, Long> {
    Users findByName(String name);
    Users findByNameAndIdNot(String name, Long id);
    Users findByEmailAndIdNot(String email, Long id);
}
