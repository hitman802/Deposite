package controllers;

import dao.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Admin on 02.05.2017.
 */
@Controller
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Secured("ROLE_ADMIN")
    @RequestMapping("/admin/user_roles")
    public String pageWithUserRoles(Model model) {
        model.addAttribute("users", userRepository.loadAllUsers());
        return "admin_user_roles";
    }
}
