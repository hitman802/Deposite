package deposit;

import dao.entities.Deposite;
import factory.DepositOperationsFactory;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TreeSet;

/**
 * Created by SHonchar on 5/29/2017.
 */
@Component
public class DepositCalculator {

    private DepositOperationsFactory depositOperationsFactory;
    private SimpleDateFormat dateFormat;

    public DepositCalculator(DepositOperationsFactory depositOperationsFactory, @Qualifier("calculator") SimpleDateFormat dateFormat) {
        this.depositOperationsFactory = depositOperationsFactory;
        this.dateFormat = dateFormat;
    }

    @SneakyThrows
    public TreeSet<DepositOperation> generateDepositOperations(Deposite deposit) {

        TreeSet<DepositOperation> operations = new TreeSet<>();

        //start from start date
        Date depositStartDate = dateFormat.parse(deposit.getStartDate().toString());
        Date depositEndDate = dateFormat.parse(deposit.getEndDate().toString());
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.setTime(depositStartDate);
        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(depositEndDate);

        Calendar calendarCurrent = Calendar.getInstance();
        calendarCurrent.setTime(depositStartDate);
        while( calendarCurrent.before(calendarEnd) ) {
            DepositOperation depositOperation = depositOperationsFactory.create();
            depositOperation.setDateOfOperation(calendarCurrent.getTime());
            depositOperation.setOperation(DepositOperations.addProcents);
            operations.add(depositOperation);

            depositOperation = depositOperationsFactory.create();
            depositOperation.setDateOfOperation(calendarCurrent.getTime());
            depositOperation.setOperation(DepositOperations.removeTax);
            operations.add(depositOperation);

            calendarCurrent.set(Calendar.MONTH, calendarCurrent.get(Calendar.MONTH)+1);
        }

        return operations;
    }

}
