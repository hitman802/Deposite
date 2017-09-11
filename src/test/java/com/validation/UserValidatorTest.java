package com.validation;

import com.BaseUnitTest;
import com.dao.entities.Users;
import com.dao.repositories.UserRepository;
import com.exceptions.UniqueUserEmailException;
import com.exceptions.UniqueUserNameException;
import com.service.UserServiceImpl;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.mockito.Mockito.when;

public class UserValidatorTest extends BaseUnitTest {

    private final String userNameValid = "valid_username";
    private final String userNameNotValid = "not_valid_username";
    private final String emailValid = "valid_email";
    private final String emailNotValid = "not_valid_email";
    private final Long idValid = 1L;
    private final Long idNotValid = 2L;

    private UserValidator userValidator;

    @Test
    public void testSupports() throws Exception {
        Assert.assertTrue(userValidator.supports(Users.class));
        Assert.assertFalse(userValidator.supports(UserValidator.class));
    }

    @Test
    public void testValidateUserNameAndEmail() throws Exception {
        userValidator.validateUserNameAndEmail(userNameValid, emailValid);
        Assert.expectThrows(UniqueUserNameException.class, ()->userValidator.validateUserNameAndEmail(userNameNotValid, emailNotValid));
        Assert.expectThrows(UniqueUserEmailException.class, ()->userValidator.validateUserNameAndEmail(userNameValid, emailNotValid));
    }

    @Test
    public void testValidateUserNameAndEmail1() throws Exception {
        userValidator.validateUserNameAndEmail(idValid, userNameValid, emailValid);
        Assert.expectThrows(UniqueUserNameException.class,
                ()->userValidator.validateUserNameAndEmail(idNotValid, userNameValid, emailValid));
        Assert.expectThrows(UniqueUserEmailException.class,
                ()->userValidator.validateUserNameAndEmail(idValid, userNameValid, emailNotValid));
    }

    @Override
    public void prepareMocks() {
        UserServiceImpl userService = Mockito.mock(UserServiceImpl.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        userValidator = new UserValidator(userService, userRepository);

        Users user = new Users();
        when(userRepository.findByName(userNameValid)).thenReturn(null);
        when(userRepository.findByName(userNameNotValid)).thenReturn(user);
        when(userRepository.findByNameAndIdNot(userNameValid, idValid)).thenReturn(null);
        when(userRepository.findByNameAndIdNot(userNameValid, idNotValid)).thenReturn(user);

        when(userRepository.findByEmail(emailNotValid)).thenReturn(user);
        when(userRepository.findByEmail(emailValid)).thenReturn(null);

        when(userRepository.findByEmailAndIdNot(emailValid, idValid)).thenReturn(null);
        when(userRepository.findByEmailAndIdNot(emailNotValid, idNotValid)).thenReturn(user);
        when(userRepository.findByEmailAndIdNot(emailNotValid, idValid)).thenReturn(user);

    }
}