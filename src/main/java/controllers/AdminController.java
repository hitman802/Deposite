package controllers;

import dao.entities.Role;
import dao.entities.Users;
import dao.repositories.RoleRepository;
import dao.repositories.UserRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import service.UserServiceImpl;
import utils.FormatUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Admin on 02.05.2017.
 */
@Controller
@Log4j
public class AdminController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserServiceImpl userService;

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin/user/list")
    public String usersList(Model model) {
        Collection<Users> users = userRepository.loadAllUsers();
        model.addAttribute("users", users.stream().sorted(Comparator.comparing(Users::getName)).collect(Collectors.toList()));
        model.addAttribute("roles", FormatUtils.formatRolesFromDBtoView(roleRepository.loadAll()).stream().sorted().collect(Collectors.toList()));
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
    public ModelAndView update(@RequestParam(value = "id") Long id
            , @RequestParam(value = "name") String name
            , @RequestParam(value = "password") String password
            , @RequestParam(value = "email") String email
            , @RequestParam(value = "roles[]") String[] roles
        ){

        //check user name and email uniqueness
        userRepository.validateUserNameAndEmail(id, name, email);
        userRepository.updateUser(id, name, email, FormatUtils.formatRolesFromViewToDB(Arrays.asList(roles)));

        return new ModelAndView("redirect:/admin/user/list");
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/admin/user/delete",method = RequestMethod.GET)
    public ModelAndView delete(@RequestParam(value = "id") Long id){
        userRepository.deleteUser(id);
        return new ModelAndView("redirect:/admin/user/list");
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/admin/user/create",method = RequestMethod.GET)
    public ModelAndView create(@RequestParam(value = "name") String name
            , @RequestParam(value = "password") String password
            , @RequestParam(value = "email") String email
        ){
        userRepository.validateUserNameAndEmail(name, email);
        userRepository.addUser(name, userService.encodePassword(password), email);
        return new ModelAndView("redirect:/admin/user/list");
    }

    private Map<String, List<String>> createUsersRolesMap(Collection<Users> users) {
        Map<String, List<String>> userRoles = new HashMap<>();
        users.forEach( user -> userRoles.put(user.getName(),
                FormatUtils.formatRolesFromDBtoView(user.getRoles()).stream().sorted().collect(Collectors.toList())));
        return userRoles;
    }

}
