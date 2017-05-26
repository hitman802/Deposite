package dao.repositories;

import dao.entities.Currency;
import dao.entities.Deposite;
import dao.entities.Users;
import factory.DepositeFactory;
import lombok.extern.log4j.Log4j;
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
        return em.createQuery("SELECT d FROM Deposite d WHERE d.user = :userName")
                .setParameter("userName", userName)
                .getResultList();
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

        deposite.setStartDate(date_start);
        deposite.setEndDate(date_finish);
        deposite.setSum(sum);
        deposite.setDepositeRate(rate);
        deposite.setTaxOnPercents(tax_on_percent);
        deposite.setCurrency(currency);
        deposite.setUser(user);

        user.getDeposits().add(deposite);
    }
}
