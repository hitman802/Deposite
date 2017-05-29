package deposit;

import dao.entities.Deposite;
import factory.DepositOperationsFactory;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.TreeSet;

/**
 * Created by SHonchar on 5/29/2017.
 */
@Component
public class DepositCalculator {

    private DepositOperationsFactory depositOperationsFactory;

    public DepositCalculator(DepositOperationsFactory depositOperationsFactory) {
        this.depositOperationsFactory = depositOperationsFactory;
    }

    private TreeSet<DepositOperation> calcDeposite(Deposite deposit) {
        TreeSet<DepositOperation> operations = new TreeSet<>();

        //start from start date
        Date depositStartDate = deposit.getStartDate();
        Date depositEndDate = deposit.getEndDate();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(depositStartDate);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.after()

        return operations;
    }

}
