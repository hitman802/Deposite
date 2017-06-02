package dao.repositories;

import dao.entities.Currency;
import dao.entities.Rate;
import dao.entities.RateSource;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.CrudRepository;
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
public interface RatesRepository extends CrudRepository<Rate, Long> {
    Rate findByCurrency_Name_AndDate(String name, Date date);
}
