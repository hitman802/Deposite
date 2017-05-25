package dao.repositories;

import dao.entities.Currency;
import dao.entities.Rate;
import factory.CurrencyFactory;
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
@Repository
@Scope(scopeName = "singleton")
public class CurrencyRepository {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private CurrencyFactory currencyFactory;

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Currency> loadAllCurrencies() {
        return em.createQuery("SELECT c FROM Currency c")
                .getResultList();
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public void checkAndUpdateCurrencies(List<Rate> rates) {
        rates.forEach(k -> {
            String name = k.getName();
            Currency currency = getCurrencyByName(name);
            if( currency == null ) {
                currency = currencyFactory.create();
                currency.setName(name);
                em.persist(currency);
            }
        });
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Currency getCurrencyByName(String name) {
        try {
            return (Currency) em.createQuery(
                    "SELECT c from Currency c WHERE c.name = :currencyName")
                    .setParameter("currencyName", name)
                    .getSingleResult();
        } catch(NoResultException e) {
            return null;
        }
    }
}
