package controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Admin on 26.04.2017.
 */
@RestController
public class UserController {

    @RequestMapping(value = "/user/registration", method = RequestMethod.GET)
    public String showRegistationForm(HttpServletRequest req, )


}
