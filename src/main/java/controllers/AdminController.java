package controllers;

import dao.entities.Users;
import dao.repositories.RoleRepository;
import dao.repositories.UserRepository;
import factory.UserFactory;
import lombok.extern.log4j.Log4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import service.UserServiceImpl;
import utils.FormatUtils;
import validation.UserRolesValidator;
import validation.UserValidator;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by Admin on 02.05.2017.
 */
@Controller
@Log4j
public class AdminController {

    private static final String REDIRECT_ADMIN_USER_LIST = "redirect:/admin/user/list";
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private UserServiceImpl userService;
    private UserValidator userValidator;
    private UserRolesValidator userRolesValidator;
    private UserFactory userFactory;

    public AdminController(UserRepository userRepository, RoleRepository roleRepository, UserServiceImpl userService, UserValidator userValidator, UserRolesValidator userRolesValidator, UserFactory userFactory) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userService = userService;
        this.userValidator = userValidator;
        this.userRolesValidator = userRolesValidator;
        this.userFactory = userFactory;
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin/user/list")
    public String usersList(Model model) {
        List<Users> users = StreamSupport.stream(userRepository.findAll().spliterator(), false).collect(Collectors.toList());
        model.addAttribute("users", users.stream().sorted(Comparator.comparing(Users::getName)).collect(Collectors.toList()));
        model.addAttribute("roles", FormatUtils.formatRolesFromDBtoView(StreamSupport.stream(roleRepository.findAll().spliterator(), false).collect(Collectors.toList())).stream().sorted().collect(Collectors.toList()));
        model.addAttribute("usersroles", createUsersRolesMap(users));
        return "admin_user_list";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping("/admin/user/edit/{userName}")
    public String pageWithUserRoles(Model model, @PathVariable String userName) {
        model.addAttribute("userForm", userRepository.findByName(userName));
        model.addAttribute("allRoles", roleRepository.findAll());
        return "admin_user_edit";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/admin/user/update",method = RequestMethod.GET)
    public ModelAndView update(@RequestParam(value = "id") Long id
            , @RequestParam(value = "name") String name
            , @RequestParam(value = "email") String email
            , @RequestParam(value = "roles[]") String[] roles
        ){

        //check user name and email uniqueness
        userValidator.validateUserNameAndEmail(id, name, email);
        updateUser(id, name, email, FormatUtils.formatRolesFromViewToDB(Arrays.asList(roles)));

        return new ModelAndView(REDIRECT_ADMIN_USER_LIST);
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/admin/user/delete",method = RequestMethod.GET)
    public ModelAndView delete(@RequestParam(value = "id") Long id){
        userRepository.delete(id);
        return new ModelAndView(REDIRECT_ADMIN_USER_LIST);
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/admin/user/create",method = RequestMethod.GET)
    public ModelAndView create(@RequestParam(value = "name") String name
            , @RequestParam(value = "password") String password
            , @RequestParam(value = "email") String email
            , @RequestParam(value = "roles[]") String[] roles
        ){

        userValidator.validateUserNameAndEmail(name, email);
        userRolesValidator.validateRoles(roles);

        addUser(name, userService.encodePassword(password), email, FormatUtils.formatRolesFromViewToDB(Arrays.asList(roles)));
        return new ModelAndView(REDIRECT_ADMIN_USER_LIST);
    }

    private Map<String, List<String>> createUsersRolesMap(Collection<Users> users) {
        Function<Users, List<String>> valMapper = val->FormatUtils.formatRolesFromDBtoView(
                val.getRoles()).stream().sorted().collect(Collectors.toList());
        return users.stream().collect(Collectors.toMap(Users::getName, valMapper));
    }

    @Transactional
    private void updateUser(Long id, String name, String email, List<String> roles) {
        Users user = userRepository.findOne(id);
        user.setName(name);
        user.setEmail(email);
        user.setRoles(roleRepository.findByNameIn(roles));
        userRepository.save(user);
    }

    @Transactional
    private void  addUser(String name, String password, String email, List<String> roles) {
        Users user = userFactory.create();
        user.setName(name);
        user.setPassword(password);
        user.setEmail(email);
        user.setRoles(roleRepository.findByNameIn(roles));
        userRepository.save(user);
    }
}
