package deposit;

import dao.entities.Deposite;
import factory.DepositOperationsFactory;
import org.springframework.stereotype.Component;

import java.time.Month;
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

    public TreeSet<DepositOperation> generateDepositOperations(Deposite deposit) {

        TreeSet<DepositOperation> operations = new TreeSet<>();

        //start from start date
        Date depositStartDate = deposit.getStartDate();
        Date depositEndDate = deposit.getEndDate();

        Calendar calendarStart = Calendar.getInstance();
        calendarStart.setTime(depositStartDate);
        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(depositEndDate);


        return operations;
    }

}
