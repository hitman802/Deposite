package controllers;

import dao.entities.Users;
import dao.repositories.RoleRepository;
import dao.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Admin on 02.05.2017.
 */
@Controller
public class AdminController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin/user/list")
    public String usersList(Model model) {
        model.addAttribute("users", userRepository.loadAllUsers());
        return "admin_user_list";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping("/admin/user/edit/{userName}")
    public String pageWithUserRoles(Model model, @PathVariable String userName) {
        model.addAttribute("userForm", userRepository.findUserByName(userName));
        model.addAttribute("allRoles", roleRepository.loadAll());
        return "admin_user_edit";
    }

    @RequestMapping(value="/admin/user/update",method = RequestMethod.POST)
    public ModelAndView editsave(@ModelAttribute("userForm") Users user){
        //dao.update(emp);
        return new ModelAndView("redirect:/admin/user/list");
    }
}
