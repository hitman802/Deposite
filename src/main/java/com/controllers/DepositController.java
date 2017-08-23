package com.controllers;

import com.dao.entities.Deposite;
import com.dao.entities.Users;
import com.dao.repositories.ICurrencyRepository;
import com.dao.repositories.DepositeRepository;
import com.dao.repositories.UserRepository;
import com.deposit.DepositCalculator;
import com.exceptions.DepositNotFoundException;
import com.exceptions.OperationNotAllowedException;
import com.exceptions.UserNotFoundException;
import com.factory.DepositeFactory;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Admin on 29.04.2017.
 */
@Controller
@Secured({"ROLE_USER", "ROLE_ADMIN"})
public class DepositController {

    private UserRepository userRepository;
    private ICurrencyRepository currencyRepository;
    private DepositeRepository depositeRepository;
    private SimpleDateFormat simpleDateFormat;
    private DepositeFactory depositeFactory;
    private DepositCalculator depositCalculator;

    public DepositController(UserRepository userRepository, ICurrencyRepository currencyRepository,
                             DepositeRepository depositeRepository, @Qualifier("deposit") SimpleDateFormat simpleDateFormat, DepositeFactory depositeFactory, DepositCalculator depositCalculator) {
        this.userRepository = userRepository;
        this.currencyRepository = currencyRepository;
        this.depositeRepository = depositeRepository;
        this.simpleDateFormat = simpleDateFormat;
        this.depositeFactory = depositeFactory;
        this.depositCalculator = depositCalculator;
    }

    @RequestMapping(value = {"", "/index"}, method = RequestMethod.GET)
    public String registration(Model model, Principal principal) {
        Optional<String> userName = Optional.ofNullable(principal.getName());
        Optional<Users> user = Optional.ofNullable(
                userRepository.findByName(
                        userName.orElseThrow(UserNotFoundException::new)
                )
        );

        model.addAttribute("user", user.orElseThrow(UserNotFoundException::new));
        model.addAttribute("currencies", currencyRepository.findAll());

        return "index";
    }

    @RequestMapping(value = "/deposit/get", method = RequestMethod.GET)
    @ResponseBody
    @SneakyThrows
    public String getDepositsForUser(Principal principal) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setDateFormat(simpleDateFormat);

        List<Deposite> deposites = depositeRepository.findByUser_Name(principal.getName());
        deposites.forEach(depositCalculator::calculateDeposit);

        Map<String, Object> deps = new HashMap<>();
        deps.put("total", deposites.size());
        deps.put("rows", deposites);
        return mapper.writeValueAsString(deps);
    }

    @RequestMapping(value = "/deposit/deleteFew", method = RequestMethod.GET)
    public String deleteDeposit(Principal principal,
        @RequestParam(value = "id") Long[] ids) {

        String currentUser = principal.getName();
        List<Deposite> deposites = depositeRepository.findByIdIn(Arrays.asList(ids));
        if( !deposites
                .stream()
                .filter(deposit->!deposit.getUser().getName().equals(currentUser))
                .collect(Collectors.toList())
                .isEmpty()) {
            throw new OperationNotAllowedException();
        }
        depositeRepository.delete(deposites);
        return "index";
    }

    @RequestMapping(value = "/deposit/delete", method = RequestMethod.GET)
    public String deleteDeposit(Principal principal,
        @RequestParam(value = "id") Long id) {
        Optional<Deposite> deposit = Optional.ofNullable(depositeRepository.findOne(id));
         if ( !deposit.orElseThrow(DepositNotFoundException::new).getUser().getName().equals(principal.getName()) ) {
            throw new OperationNotAllowedException();
        }
        depositeRepository.delete(id);
        return "index";
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

        Deposite deposite = depositeRepository.findOne(id);
        setDepositeFields(deposite, principal.getName(),
                name, date_start, date_finish, sum, rate, currency, tax_on_percent);
        depositeRepository.save(deposite);

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
        Deposite deposite = depositeFactory.create();
        setDepositeFields(deposite, principal.getName(), name, date_start, date_finish, sum, rate, currency, tax_on_percent);
        depositeRepository.save(deposite);
        return "index";
    }

    private void setDepositeFields(Deposite deposite, String userName, String deposite_name, Date date_start, Date date_finish,
                                       Double sum, Double rate, String currencyName, Double tax_on_percent) {
        Optional.ofNullable(userName).ifPresent(usrName -> deposite.setUser(userRepository.findByName(userName)));
        Optional.ofNullable(deposite_name).ifPresent(deposite::setName);
        Optional.ofNullable(date_start).ifPresent(deposite::setStartDate);
        Optional.ofNullable(date_finish).ifPresent(deposite::setEndDate);
        Optional.ofNullable(sum).ifPresent(deposite::setSum);
        Optional.ofNullable(rate).ifPresent(deposite::setDepositeRate);
        Optional.ofNullable(tax_on_percent).ifPresent(deposite::setTaxOnPercents);
        Optional.ofNullable(currencyName).ifPresent(currName -> deposite.setCurrency(currencyRepository.findByName(currencyName)));
    }
}
