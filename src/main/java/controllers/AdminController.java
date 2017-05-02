package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import service.interfaces.IUserService;

/**
 * Created by Admin on 02.05.2017.
 */
@Controller
public class AdminController {

    @Autowired
    private IUserService userService;

    @Secured("ROLE_ADMIN")
    @RequestMapping("/admin/user_roles")
    public String pageWithUserRoles(Model model) {
        return "admin_user_roles";
    }
}
