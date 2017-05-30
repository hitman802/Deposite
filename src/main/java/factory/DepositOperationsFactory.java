package factory;

import deposit.DepositOperation;
import org.springframework.stereotype.Component;

/**
 * Created by SHonchar on 5/29/2017.
 */
@Component
public class DepositOperationsFactory {
    public DepositOperation create() {
        return new DepositOperation();
    }
}
