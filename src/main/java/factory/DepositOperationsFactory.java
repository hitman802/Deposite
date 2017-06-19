package factory;

import deposit.DepositOperation;
import deposit.DepositOperations;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by SHonchar on 5/29/2017.
 */
@Component
public class DepositOperationsFactory {
    public DepositOperation create() {
        return new DepositOperation();
    }

    public DepositOperation createOnDateAndOperation(Date date, DepositOperations depositOperations) {
        DepositOperation depositOperation = create();
        depositOperation.setDateOfOperation(date);
        depositOperation.setOperation(depositOperations);
        return depositOperation;
    }
}
