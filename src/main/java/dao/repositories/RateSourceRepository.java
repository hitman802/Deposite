package dao.repositories;

import dao.entities.RateSource;
import factory.RateSourceFactory;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Admin on 22.04.2017.
 */
@Log4j
@Repository
@Scope(scopeName = "singleton")
public class RateSourceRepository {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private RateSourceFactory rateSourceFactory;

    @Transactional(propagation = Propagation.REQUIRED)
    public RateSource updateSource(String sourceName) {
        RateSource rateSource = findSourceByName(sourceName);
        if( rateSource == null ) {
            rateSource = rateSourceFactory.create();
            rateSource.setName(sourceName);
            addSource(rateSource);
        }
        return rateSource;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public RateSource findSourceByName(String name) {

        try {
            return (RateSource) em.createQuery(
                    "SELECT c FROM RateSource c WHERE c.name = :rateSourceName")
                    .setParameter("rateSourceName", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    public void addSource(RateSource rs) {
        em.persist(rs);
    }
}
