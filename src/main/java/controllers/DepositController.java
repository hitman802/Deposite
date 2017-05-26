package controllers;

import dao.entities.Users;
import dao.repositories.CurrencyRepository;
import dao.repositories.DepositeRepository;
import dao.repositories.UserRepository;
import exceptions.UserNotFoundException;
import factory.DepositeFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Date;

/**
 * Created by Admin on 29.04.2017.
 */
@Controller
@Secured({"ROLE_USER", "ROLE_ADMIN"})
public class DepositController {

    private UserRepository userRepository;
    private CurrencyRepository currencyRepository;
    private DepositeRepository depositeRepository;

    public DepositController(UserRepository userRepository, CurrencyRepository currencyRepository, DepositeRepository depositeRepository) {
        this.userRepository = userRepository;
        this.currencyRepository = currencyRepository;
        this.depositeRepository = depositeRepository;
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

    @RequestMapping(value = "/deposit/new", method = RequestMethod.GET)
    public String createDeposit(Principal principal,
        @RequestParam(value = "name") String name,
        @RequestParam(value = "date_start") Date date_start,
        @RequestParam(value = "date_finish") Date date_finish,
        @RequestParam(value = "sum") Double sum,
        @RequestParam(value = "rate") Double rate,
        @RequestParam(value = "currency") String currency,
        @RequestParam(value = "tax_on_percent") Double tax_on_percent
    ) {
        depositeRepository.addDeposite(principal.getName(), name, date_start,
                date_finish, sum, rate, currency, tax_on_percent);
        return "index";
    }
}
