package controllers;

import dao.entities.Users;
import dao.repositories.CurrencyRepository;
import dao.repositories.UserRepository;
import exceptions.UserNotFoundException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

/**
 * Created by Admin on 29.04.2017.
 */
@Controller
@Secured({"ROLE_USER", "ROLE_ADMIN"})
public class DepositController {

    private UserRepository userRepository;
    private CurrencyRepository currencyRepository;

    public DepositController(UserRepository userRepository, CurrencyRepository currencyRepository) {
        this.userRepository = userRepository;
        this.currencyRepository = currencyRepository;
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
        model.addAttribute("currencies", currencyRepository.loadAllCurrencies());

        return "index";
    }
}
