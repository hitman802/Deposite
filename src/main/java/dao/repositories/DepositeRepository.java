package dao.repositories;

import dao.entities.Currency;
import dao.entities.Deposite;
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
public class DepositeRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional(propagation = Propagation.REQUIRED)
    public Deposite findDeposite(Long id) {
        Deposite deposite = em.find(Deposite.class, id);
        if( deposite == null ) {
            log.error("Cant find deposites by id " + id);
        }
        return deposite;
    }

    @Transactional
    public void addDeposite(Currency currency, Date startDate, Date endDate,
                            double depositeRate, double taxOnPercents) {
        Deposite deposite = createDeposites(currency, startDate,
                endDate, depositeRate, taxOnPercents);
        em.persist(deposite);
    }

    public Deposite createDeposites(Currency currency, Date startDate, Date endDate,
                                    double depositeRate, double taxOnPercents) {
        Deposite deposite = new Deposite();
        deposite.setCurrency(currency);
        deposite.setStartDate(startDate);
        deposite.setEndDate(endDate);
        deposite.setDepositeRate(depositeRate);
        deposite.setTaxOnPercents(taxOnPercents);
        return deposite;
    }
}
