package dao.dal;

import dao.entities.Currency;
import dao.entities.Rate;
import factory.CurrencyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Admin on 22.04.2017.
 */
@Repository
@Scope(scopeName = "singleton")
public class CurrencyDal {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private CurrencyFactory currencyFactory;

    @Transactional(propagation = Propagation.REQUIRED)
    public void checkAndUpdateCurrencies(List<Rate> rates) {
        rates.forEach(k -> {
            String name = k.getName();
            Currency currency = getCurrencyById(name);
            if( currency == null ) {
                currency = currencyFactory.create();
                currency.setId(name);
                em.persist(currency);
            }
        });
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Currency getCurrencyById(String id) {
        return em.find(Currency.class, id);
    }

    private boolean isValidCurrency(Currency currency) {
        boolean notNullId = currency.getId() != null;
        boolean notEmptyId = notNullId && !currency.getId().isEmpty();
        return notEmptyId;
    }
}
