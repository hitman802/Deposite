package dao.repositories;

import dao.entities.Users;
import dao.entities.Role;
import factory.UserRoleFactory;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.CrudRepository;
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
public interface RoleRepository extends CrudRepository<Role, Long> {
    Set<Role> findByNameIn(Collection<String> names);
}
