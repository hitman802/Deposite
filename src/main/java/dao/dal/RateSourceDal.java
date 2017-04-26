package dao.dal;

import dao.entities.RateSource;
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
public class RateSourceDal {

    @PersistenceContext
    private EntityManager em;

    @Transactional(propagation = Propagation.REQUIRED)
    public RateSource findSource(String name) {
        RateSource rs = em.find(RateSource.class, name);
        if( rs == null ) {
            log.error("Cant find rate source by id " + name);
        }
        return rs;
    }

    @Transactional
    public void addSource(String name) {
        RateSource rs = new RateSource();
        rs.setName(name);
        em.persist(rs);
    }
}
