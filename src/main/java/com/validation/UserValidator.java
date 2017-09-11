package com.validation;

import com.dao.entities.Users;
import com.dao.repositories.UserRepository;
import com.exceptions.UniqueUserEmailException;
import com.exceptions.UniqueUserNameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import com.service.UserServiceImpl;

import java.util.Optional;

/**
 * Created by Admin on 30.04.2017.
 */
@Component
public class UserValidator implements Validator {

    private final UserServiceImpl userService;
    private final UserRepository userRepository;

    @Autowired
    public UserValidator(UserServiceImpl userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Users.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Users user = (Users) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty");
        if (user.getName().length() < 6 || user.getName().length() > 32) {
            errors.rejectValue("name", "Size.userForm.name");
        }
        if (userService.findByUsername(user.getName()) != null) {
            errors.rejectValue("name", "Duplicate.userForm.name");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (user.getPasswordConfirm() != null && !user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }
    }

    @Transactional
    public void validateUserNameAndEmail(String name, String email) {
        validateUserName(name);
        validateUserEmail(email);
    }

    @Transactional
    public void validateUserNameAndEmail(Long id, String name, String email) {
        validateUserName(id, name);
        validateUserEmail(id, email);
    }


    /**
     * throws runtime exception in case if username not unique
     * @param email - user name to check
     */
    private void validateUserEmail(String email){
        if( userRepository.findByEmail(email) != null ) {
            throw new UniqueUserEmailException();
        }
    }

    /**
     * throws runtime exception in case if username not unique
     * @param name - user name to check
     */
    private void validateUserName(String name){
        if( userRepository.findByName(name) != null ) {
            throw new UniqueUserNameException();
        }
    }

    /**
     * throws runtime exception in case if username not unique
     * @param id - user id
     * @param name - user name to check
     */
    private void validateUserName(Long id, String name){
        if( userRepository.findByNameAndIdNot(name, id) != null ) {
            throw new UniqueUserNameException();
        }
    }

    /**
     * throws runtime exception in case if user email not unique
     * @param id - user id
     * @param email - user email to check
     */
    private void validateUserEmail(Long id, String email){
        if( userRepository.findByEmailAndIdNot(email, id) != null ) {
            throw new UniqueUserEmailException();
        }
    }

}
