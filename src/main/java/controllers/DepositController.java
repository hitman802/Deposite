package controllers;

import dao.entities.Users;
import dao.repositories.UserRepository;
import exceptions.UserNotFoundException;
import factory.UserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import service.interfaces.ISecurityService;
import service.interfaces.IUserService;
import validation.UserValidator;

import java.security.Principal;
import java.util.Optional;

/**
 * Created by Admin on 29.04.2017.
 */
@Controller
@Secured({"ROLE_USER", "ROLE_ADMIN"})
public class DepositController {

    private UserRepository userRepository;

    public DepositController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = {"", "/index"}, method = RequestMethod.GET)
    public String registration(Model model, Principal principal) {
        String userName = principal.getName();
        if ( userName == null ) {
            throw new UserNotFoundException();
        }

        Users user = userRepository.findUserByName(userName);
        if( user == null ) {
            throw new UserNotFoundException();
        }
        model.addAttribute("user", user);
        return "index";
    }
}
