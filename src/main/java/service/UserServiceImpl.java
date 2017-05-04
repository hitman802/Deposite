package service;

import dao.entities.Users;
import dao.repositories.RoleRepository;
import dao.repositories.UserRepository;
import org.springframework.stereotype.Service;
import service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

/**
 * Created by Admin on 30.04.2017.
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(Users user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>(roleRepository.loadAll()));
        userRepository.saveUser(user);
    }

    @Override
    public Users findByUsername(String username) {
        return userRepository.loadUserByName(username);
    }
}
