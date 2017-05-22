package factory;

import dao.entities.Users;
import org.springframework.stereotype.Component;

/**
 * Created by Admin on 23.05.2017.
 */
@Component
public class UserFactory {
    public Users create() {
        return new Users();
    }
}
