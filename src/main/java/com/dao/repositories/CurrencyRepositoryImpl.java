package com.dao.repositories;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

/**
 * Created by Admin on 12.08.2017.
 */
@Repository
public class CurrencyRepositoryImpl implements CurrencyRepositoryCustom {

    @Getter private final EntityManager em;
    @Getter @Setter private SessionFactory sf;

    public CurrencyRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @PostConstruct
    public void init() {
        sf = em.getEntityManagerFactory().unwrap(SessionFactory.class);
    }

    @Override
    public void customMethod() {
        throw new RuntimeException("not implemented");
    }
}
