package controllers;

import dao.entities.Users;
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

/**
 * Created by Admin on 29.04.2017.
 */
@Controller
@Secured({"ROLE_USER", "ROLE_ADMIN"})
public class DepositController {
    @RequestMapping(value = {"", "/index"}, method = RequestMethod.GET)
    public String registration(Model model) {
        //add all deposites of current user
        return "index";
    }
}
