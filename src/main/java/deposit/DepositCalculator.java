package deposit;

import dao.entities.Deposite;
import factory.DepositOperationsFactory;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.*;

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

    public void calculateDeposit(Deposite deposit) {
        TreeSet<DepositOperation> depositOperations = new TreeSet<DepositOperation>();

        generateDepositOperationsByMonth(deposit, depositOperations);
        generateDepositOperationsByReplenishments(deposit, depositOperations);

        calculateSums(deposit, depositOperations);
        deposit.setDepositOperations(depositOperations);
    }

    @SneakyThrows
    private void calculateSums(Deposite deposit, TreeSet<DepositOperation> depositOperations) {

        DepositOperation previousOperation = depositOperations.first();

        Calendar prevCalendar = Calendar.getInstance();
        prevCalendar.setTime(previousOperation.getDateOfOperation());
        previousOperation.setSum(deposit.getSum());

        double monthPercentsWoTax = 0;
        double sumAllPeriodWithPercents = previousOperation.getSum();
        boolean monthChanged = false;

        for( DepositOperation depositOperation : depositOperations ) {

            Calendar curCalendar = Calendar.getInstance();
            curCalendar.setTime(depositOperation.getDateOfOperation());

            switch( depositOperation.getOperation() ) {

                case calcProcents: {
                    long daysDiff = Math.abs(ChronoUnit.DAYS.between(
                            prevCalendar.getTime().toInstant(), curCalendar.getTime().toInstant()));
                    double calculatedSum = calcProcentsForGivenPeriod(deposit, daysDiff, sumAllPeriodWithPercents);

                    if( !monthChanged ) {
                        monthPercentsWoTax += calculatedSum;
                    }

                    sumAllPeriodWithPercents += calculatedSum;
                    depositOperation.setSum(calculatedSum);
                    break;
                }

                case removeTax: {
                    double monthlyTax = monthPercentsWoTax * deposit.getTaxOnPercents() / 100;
                    sumAllPeriodWithPercents -= monthlyTax;
                    depositOperation.setSum(monthlyTax);
                    break;
                }

                default: break;
            }
            if( monthChanged ) {
                monthPercentsWoTax = 0;
            }
            monthChanged = prevCalendar.get(Calendar.MONTH) != curCalendar.get(Calendar.MONTH);
            prevCalendar = curCalendar;
        }
        deposit.setFinalSum(sumAllPeriodWithPercents);
    }

    private double calcProcentsForGivenPeriod(Deposite deposite, long days, double prevPeriodSum) {
       return prevPeriodSum * (deposite.getDepositeRate()/100) * days / 365;
    }

    private void generateDepositOperationsByReplenishments(Deposite deposit, SortedSet<DepositOperation> operations) {

    }

    @SneakyThrows
    private void generateDepositOperationsByMonth(Deposite deposit, SortedSet<DepositOperation> operations) {

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

            operations.add(depositOperationsFactory.createOnDateAndOperation(
                    calendarCurrent.getTime(), DepositOperations.calcProcents));

            operations.add(depositOperationsFactory.createOnDateAndOperation(
                    calendarCurrent.getTime(), DepositOperations.removeTax));

            calendarCurrent.set(Calendar.MONTH, calendarCurrent.get(Calendar.MONTH)+1);
            calendarCurrent.set(Calendar.DAY_OF_MONTH, 1);
        }

        operations.add(depositOperationsFactory.createOnDateAndOperation(
                deposit.getEndDate(), DepositOperations.calcProcents));

        operations.add(depositOperationsFactory.createOnDateAndOperation(
                deposit.getEndDate(), DepositOperations.removeTax));

    }

}
