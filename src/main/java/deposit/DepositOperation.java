package deposit;

import dao.entities.Deposite;
import factory.DepositOperationsFactory;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.TreeSet;

/**
 * Created by SHonchar on 5/29/2017.
 */
public class DepositOperation implements Comparable{

    @Getter @Setter private DepositOperations operation;
    @Getter @Setter private Date dateOfOperation;
    @Getter @Setter private Double sum;

    @Override
    public int compareTo(Object o) {
        if( !(o instanceof DepositOperation) ) {
            return -1;
        }
        return ((DepositOperation)o).dateOfOperation.compareTo(dateOfOperation);
    }
}
