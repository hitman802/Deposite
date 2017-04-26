package dao.dal;

import dao.entities.Currency;
import dao.entities.Deposites;
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
public class DepositeDal {

    @PersistenceContext
    private EntityManager em;

    @Transactional(propagation = Propagation.REQUIRED)
    public Deposites findDeposite(Long id) {
        Deposites deposites = em.find(Deposites.class, id);
        if( deposites == null ) {
            log.error("Cant find deposites by id " + id);
        }
        return deposites;
    }

    @Transactional
    public void addDeposite(Currency currency, Date startDate, Date endDate,
                            double depositeRate, double taxOnPercents) {
        Deposites deposites = createDeposites(currency, startDate,
                endDate, depositeRate, taxOnPercents);
        em.persist(deposites);
    }

    public Deposites createDeposites(Currency currency, Date startDate, Date endDate,
                                     double depositeRate, double taxOnPercents) {
        Deposites deposites = new Deposites();
        deposites.setCurrency(currency);
        deposites.setStartDate(startDate);
        deposites.setEndDate(endDate);
        deposites.setDepositeRate(depositeRate);
        deposites.setTaxOnPersents(taxOnPercents);
        return deposites;
    }
}
