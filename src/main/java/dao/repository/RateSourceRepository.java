package dao.repository;

import dao.entities.Rate;
import dao.entities.RateSource;
import factory.RateSourceFactory;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

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
        RateSource rateSource = findSource(sourceName);
        if( rateSource == null ) {
            rateSource = rateSourceFactory.create();
            rateSource.setName(sourceName);
            addSource(rateSource);
        }
        return rateSource;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public RateSource findSource(String name) {
        RateSource rs = em.find(RateSource.class, name);
        if( rs == null ) {
            log.error("Cant find rate source by id " + name);
        }
        return rs;
    }

    @Transactional
    public void addSource(RateSource rs) {
        em.persist(rs);
    }
}
