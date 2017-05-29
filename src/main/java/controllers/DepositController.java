package controllers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.entities.Deposite;
import dao.entities.Users;
import dao.repositories.CurrencyRepository;
import dao.repositories.DepositeRepository;
import dao.repositories.UserRepository;
import exceptions.UserNotFoundException;
import lombok.SneakyThrows;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Admin on 29.04.2017.
 */
@Controller
@Secured({"ROLE_USER", "ROLE_ADMIN"})
public class DepositController {

    private UserRepository userRepository;
    private CurrencyRepository currencyRepository;
    private DepositeRepository depositeRepository;
    private SimpleDateFormat simpleDateFormat;

    public DepositController(UserRepository userRepository, CurrencyRepository currencyRepository,
                             DepositeRepository depositeRepository, SimpleDateFormat simpleDateFormat) {
        this.userRepository = userRepository;
        this.currencyRepository = currencyRepository;
        this.depositeRepository = depositeRepository;
        this.simpleDateFormat = simpleDateFormat;
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

    @RequestMapping(value = "/deposit/get", method = RequestMethod.GET)
    @ResponseBody
    @SneakyThrows
    public String getDepositsForUser(Principal principal) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setDateFormat(simpleDateFormat);

        List<Deposite> deposites = depositeRepository.findDepositesByUserName(principal.getName());
        Map<String, Object> deps = new HashMap<>();
        deps.put("total", deposites.size());
        deps.put("rows", deposites);
        return mapper.writeValueAsString(deps);
    }

    @RequestMapping(value = "/deposit/update", method = RequestMethod.GET)
    @SneakyThrows
    public String updateDeposit(Principal principal,
        @RequestParam(value = "pk") Long id,
        @RequestParam(value = "value") String value,
        @RequestParam(value = "name") String keyName
    ) {

        String name = null, currency = null;
        Date date_start = null, date_finish = null;
        Double sum = null, rate = null, tax_on_percent = null;

        switch(keyName) {
            case "name": {
                name = value;
                break;
            }
            case "currency.name": {
                currency = value;
                break;
            }
            case "startDate": {
                date_start = simpleDateFormat.parse(value);
                break;
            }
            case "endDate": {
                date_finish = simpleDateFormat.parse(value);
                break;
            }
            case "sum": {
                sum = Double.parseDouble(value);
                break;
            }
            case "depositeRate": {
                rate = Double.parseDouble(value);
                break;
            }
            case "taxOnPercents": {
                tax_on_percent = Double.parseDouble(value);
                break;
            }
            default: break;
        }

        depositeRepository.updateDeposite(id, principal.getName(),
                name, date_start, date_finish, sum, rate, currency, tax_on_percent);

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
