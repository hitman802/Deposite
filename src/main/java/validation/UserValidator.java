package validation;

import dao.entities.Users;
import dao.repositories.UserRepository;
import exceptions.UniqueUserEmailException;
import exceptions.UniqueUserNameException;
import exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import service.UserServiceImpl;

import java.util.List;

/**
 * Created by Admin on 30.04.2017.
 */
@Component
public class UserValidator implements Validator {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private UserRepository userRepository;

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

    public void validateUserNameAndEmail(String name, String email) {
        validateUserName(name);
        validateUserEmail(email);
    }

    public void validateUserNameAndEmail(Long id, String name, String email) {
        validateUserName(id, name);
        validateUserEmail(id, email);
    }


    /**
     * throws runtime exception in case if username not unique
     * @param email - user name to check
     */
    private void validateUserEmail(String email){
        if( userRepository.findUserByEmail(email) != null ) {
            throw new UniqueUserEmailException();
        }
    }

    /**
     * throws runtime exception in case if username not unique
     * @param name - user name to check
     */
    @Transactional
    private void validateUserName(String name){
        if( userRepository.findUserByName(name) != null ) {
            throw new UniqueUserNameException();
        }
    }

    /**
     * throws runtime exception in case if username not unique
     * @param id - user id
     * @param name - user name to check
     */
    @Transactional
    private void validateUserName(Long id, String name){
        if( userRepository.findUserByNameWithDifferentId(id, name) != null ) {
            throw new UniqueUserNameException();
        }
    }

    /**
     * throws runtime exception in case if user email not unique
     * @param id - user id
     * @param email - user email to check
     */
    @Transactional
    private void validateUserEmail(Long id, String email){
        if( userRepository.findUserByEmailWithDifferentId(id, email) != null ) {
            throw new UniqueUserEmailException();
        }
    }

}
