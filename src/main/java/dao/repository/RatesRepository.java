package dao.repository;

import dao.entities.Currency;
import dao.entities.Rate;
import dao.entities.RateSource;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 22.04.2017.
 */
@Log4j
@Repository
@Scope(scopeName = "singleton")
public class RatesRepository {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private CurrencyRepository currencyRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateRates(List<Rate> rates, RateSource rateSource) {
        rates.forEach( rate -> {

            String currencyId = rate.getName();
            Date date = new Date();

            //check if rate already exist on given date, if yes - skip
            if( isExistRateByCurrencyNameAndDate(currencyId, date) ) {
                return;
            }

            Currency currency = currencyRepository.getCurrencyById(currencyId);
            if( currency == null ) {
                log.error("Cant find currency in db " + currencyId);
                return;
            }
            rate.setCurrency(currency);
            rate.setDate(date);
            rate.setSource(rateSource);
            log.info("Processing " + rate);
            em.persist(rate);
        });
    }


    @Transactional(propagation = Propagation.REQUIRED)
    private boolean isExistRateByCurrencyNameAndDate(String currencyName, Date date) {

         List result = em.createQuery(
                "SELECT c FROM Rate c WHERE c.currency.id = :currencyName AND c.date = :date")
                .setParameter("currencyName", currencyName)
                 .setParameter("date", date)
                .getResultList();
        return !result.isEmpty();
    }
}
