package dao.repositories;

import dao.entities.Currency;
import dao.entities.Deposite;
import dao.entities.Users;
import exceptions.OperationNotAllowedException;
import factory.DepositeFactory;
import lombok.extern.log4j.Log4j;
import org.hibernate.cfg.beanvalidation.GroupsPerOperation;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by Admin on 22.04.2017.
 */
@Log4j
@Repository
@Scope(scopeName = "singleton")
public class DepositeRepository {

    @PersistenceContext
    private EntityManager em;
    private DepositeFactory depositeFactory;
    private UserRepository userRepository;
    private CurrencyRepository currencyRepository;

    public DepositeRepository(DepositeFactory depositeFactory, UserRepository userRepository,
                              CurrencyRepository currencyRepository) {
        this.depositeFactory = depositeFactory;
        this.userRepository = userRepository;
        this.currencyRepository = currencyRepository;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Deposite> findDepositesByUserName(String userName) {
        return em.createQuery("SELECT d FROM Deposite d WHERE d.user.name = :userName")
                .setParameter("userName", userName)
                .getResultList();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    private Deposite findDepositesByIdAmdUserName(Long id, String userName) {
        try {
            return (Deposite) em.createQuery("SELECT d FROM Deposite d WHERE d.user.name = :userName AND d.id = :id")
                    .setParameter("userName", userName)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch( NoResultException e ) {
            return null;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateDeposite(Long depositeId, String userName, String deposite_name, Date date_start, Date date_finish,
                               Double sum, Double rate, String currencyName, Double tax_on_percent) {
        Deposite deposite = findDepositesByIdAmdUserName(depositeId, userName);
        if( deposite == null ) {
            throw new OperationNotAllowedException();
        }
        Optional.ofNullable(deposite_name).ifPresent(deposite::setName);
        Optional.ofNullable(date_start).ifPresent(deposite::setStartDate);
        Optional.ofNullable(date_finish).ifPresent(deposite::setEndDate);
        Optional.ofNullable(sum).ifPresent(deposite::setSum);
        Optional.ofNullable(rate).ifPresent(deposite::setDepositeRate);
        Optional.ofNullable(tax_on_percent).ifPresent(deposite::setTaxOnPercents);
        Optional.ofNullable(currencyName).ifPresent(currName -> {
            deposite.setCurrency(currencyRepository.getCurrencyByName(currencyName));
        });
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public Deposite findDepositeById(Long id) {
        Deposite deposite = em.find(Deposite.class, id);
        if( deposite == null ) {
            log.error("Cant find deposites by id " + id);
        }
        return deposite;
    }

    @Transactional
    public void addDeposite(String userName, String deposite_name, Date date_start, Date date_finish,
                            Double sum, Double rate, String currencyName, Double tax_on_percent) {
        Deposite deposite = depositeFactory.create();
        Users user = userRepository.findUserByName(userName);
        Currency currency = currencyRepository.getCurrencyByName(currencyName);

        deposite.setName(deposite_name);
        deposite.setStartDate(date_start);
        deposite.setEndDate(date_finish);
        deposite.setSum(sum);
        deposite.setDepositeRate(rate);
        deposite.setTaxOnPercents(tax_on_percent);
        deposite.setCurrency(currency);
        deposite.setUser(user);
        em.persist(deposite);
    }
}
