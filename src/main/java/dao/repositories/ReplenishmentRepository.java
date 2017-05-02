package dao.repositories;

import dao.entities.Currency;
import dao.entities.Replenishment;
import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;

/**
 * Created by Admin on 22.04.2017.
 */
@Log4j
@Repository
@Scope(scopeName = "singleton")
public class ReplenishmentRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional(propagation = Propagation.REQUIRED)
    public Replenishment findReplenishment(Long id) {
        Replenishment replenishment = em.find(Replenishment.class, id);
        if( replenishment == null ) {
            log.error("Cant find rate source by id " + id);
        }
        return replenishment;
    }

    @Transactional
    public void addReplenishment(Long id, Currency currency, long sum) {
        Replenishment replenishment = createReplenishment(currency, sum);
        em.persist(replenishment);
    }

    public Replenishment createReplenishment(Currency currency, long sum) {
        Replenishment replenishment = new Replenishment();
        replenishment.setCurrency(currency);
        replenishment.setSum(sum);
        replenishment.setDate(new Date());
        return replenishment;
    }
}
