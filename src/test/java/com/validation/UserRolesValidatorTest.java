package com.validation;

import com.exceptions.UserRoleNotFoundException;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.testng.Assert.*;

public class UserRolesValidatorTest {

    private UserRolesValidator validator;

    @BeforeMethod
    public void setUp() throws Exception {
        validator = new UserRolesValidator();
    }

    @Test
    public void testValidateRolesPos() {
        validator.validateRoles(new String[] {"admin", "user"});
    }

    @Test(expectedExceptions = UserRoleNotFoundException.class)
    public void testValidateRolesNeg() {
        validator.validateRoles(new String[] {});
    }

}