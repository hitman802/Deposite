package com.factory;

import com.dao.entities.Role;
import org.springframework.stereotype.Component;

/**
 * Created by Admin on 30.04.2017.
 */
@Component
public class UserRoleFactory {
    public Role create() {
        return new Role();
    }
}
