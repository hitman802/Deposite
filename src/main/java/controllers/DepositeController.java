package controllers;

import dao.dal.DepositeDal;
import dao.dal.UsersDal;
import dao.entities.Deposites;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Admin on 26.04.2017.
 */
@RestController
public class DepositeController {

    @Autowired
    DepositeDal depositeDal;
    @Autowired
    UsersDal usersDal;

    @RequestMapping(value = "/deposite/create", method = RequestMethod.GET)
    public Deposites createDeposite(
            @RequestParam(value = "u", required = true) String user
    ) {

        //check if user exist


        return deposite;
    }

}
