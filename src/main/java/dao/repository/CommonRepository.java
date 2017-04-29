package dao.repository;

import dao.entities.Common;
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
public class CommonRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional(propagation = Propagation.REQUIRED)
    public Common findCommon(Long id) {
        Common common = em.find(Common.class, id);
        if( common == null ) {
            log.error("Cant find common by id " + id);
        }
        return common;
    }

    @Transactional
    public void addCommon(String name) {
        Common common = createCommon(name);
        em.persist(common);
    }

    public Common createCommon(String name) {
        Common common = new Common();
        return common;
    }
}
