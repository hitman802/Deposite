package dao.dal;

import dao.entities.Currency;
import dao.entities.Rate;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 22.04.2017.
 */
@Log4j
@Repository
@Scope(scopeName = "singleton")
public class RatesDal {

    @Autowired
    CurrencyDal currencyDal;
    @PersistenceContext
    private EntityManager em;

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateRates(List<Rate> rates) {
        rates.forEach( rate -> {
            String currencyId = rate.getName();
            Currency currency = currencyDal.getCurrencyById(currencyId);
            if( currency == null ) {
                log.error("Cant find currency in db " + currencyId);
                return;
            }
            rate.setCurrency(currency);
            rate.setDate(new Date());
            log.info("Processing " + rate);
            em.persist(rate);
        });
    }
}
