package controllers;

import com.sun.deploy.util.ArrayUtil;
import dao.entities.Role;
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

import java.util.*;
import java.util.stream.Collectors;

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
        Collection<Users> users = userRepository.loadAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("roles", formatRoles(roleRepository.loadAll()));
        model.addAttribute("usersroles", createUsersRolesMap(users));
        return "admin_user_list";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping("/admin/user/edit/{userName}")
    public String pageWithUserRoles(Model model, @PathVariable String userName) {
        model.addAttribute("userForm", userRepository.findUserByName(userName));
        model.addAttribute("allRoles", roleRepository.loadAll());
        return "admin_user_edit";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/admin/user/update",method = RequestMethod.GET)
    public ModelAndView update(Long id, String name, String password, String email, String roles ){
        //dao.update(emp);
        return new ModelAndView("redirect:/admin/user/list");
    }

    private Map<String, List<String>> createUsersRolesMap(Collection<Users> users) {
        Map<String, List<String>> userRoles = new HashMap<>();
        users.forEach( user -> {
            userRoles.put(user.getName(), formatRoles(user.getRoles()));
        });
        return userRoles;
    }

    private List<String> formatRoles(Collection<Role> rolesDB) {
        return
            rolesDB.stream()
                .map( role -> formatRole(role.getName()) )
                .collect(Collectors.toList())
            ;
    }

    private String formatRole(String role) {
        //from ROLE_ADMIN to Admin
        String roleNew = role.replace("ROLE_", "").toLowerCase();
        return Character.toUpperCase(roleNew.charAt(0)) + roleNew.substring(1);
    }
}
