package com.controllers;

import com.dao.entities.Users;
import com.factory.UserFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.service.interfaces.ISecurityService;
import com.service.interfaces.IUserService;
import com.validation.UserValidator;

import java.util.Optional;

/**
 * Created by Admin on 29.04.2017.
 */
@Controller
public class LoginController {

    private IUserService userService;
    private ISecurityService securityService;
    private UserValidator userValidator;
    private UserFactory userFactory;

    public LoginController(IUserService userService, ISecurityService securityService, UserValidator userValidator, UserFactory userFactory) {
        this.userService = userService;
        this.securityService = securityService;
        this.userValidator = userValidator;
        this.userFactory = userFactory;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", userFactory.create());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") Users userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userForm);
        securityService.autologin(userForm.getName(), userForm.getPasswordConfirm());
        return "redirect:/index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        Optional.ofNullable(error).ifPresent(err -> model.addAttribute("error", "Your username and password is invalid."));
        Optional.ofNullable(logout).ifPresent(log-> model.addAttribute("message", "You have been logged out successfully."));
        return "login";
    }
}
