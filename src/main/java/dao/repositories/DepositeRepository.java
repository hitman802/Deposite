package dao.repositories;

import dao.entities.Currency;
import dao.entities.Deposite;
import dao.entities.Users;
import exceptions.OperationNotAllowedException;
import factory.DepositeFactory;
import lombok.extern.log4j.Log4j;
import org.hibernate.cfg.beanvalidation.GroupsPerOperation;
import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.*;

/**
 * Created by Admin on 22.04.2017.
 */
public interface DepositeRepository extends CrudRepository<Deposite, Long> {
    List<Deposite> findByUser_Name(String userName);
    List<Deposite> findByIdIn(Collection<Long> ids);
}
