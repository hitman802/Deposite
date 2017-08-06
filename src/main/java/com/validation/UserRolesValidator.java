package com.validation;

import com.exceptions.UserRoleNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Created by SHonchar on 5/23/2017.
 */
@Component
public class UserRolesValidator {

    public void validateRoles(String[] roles) {
        if( roles.length <1 ) {
            throw new UserRoleNotFoundException();
        }
    }

}
