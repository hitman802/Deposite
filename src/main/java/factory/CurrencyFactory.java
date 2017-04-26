package factory;

import dao.entities.Currency;
import org.springframework.stereotype.Component;

/**
 * Created by Admin on 23.04.2017.
 */
@Component
public class CurrencyFactory {

    public Currency create() {
        return new Currency();
    }

}
