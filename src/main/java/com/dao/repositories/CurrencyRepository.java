package com.dao.repositories;

import com.dao.entities.Currency;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Admin on 12.08.2017.
 */
@Repository
@Transactional
public class CurrencyRepository implements ICurrencyRepository {

    private SessionFactory sessionFactory;

    public CurrencyRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Currency findByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Currency c WHERE c.name = :name");
        query.setParameter("name", name);
        return (Currency) query.list().get(0);
    }

    @Override
    public Collection<Currency> findByNames(Collection<String> names) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Currency c WHERE c.name in :name");
        query.setParameter("name", names.stream().collect(Collectors.joining(",", "(", ")")));
        return query.list();
    }

    @Override
    public Collection<Currency> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select Currency FROM Currency").list();
    }

    @Override
    public void save(Currency currency) {
        Session session = sessionFactory.getCurrentSession();
        session.save(currency);
    }
}
