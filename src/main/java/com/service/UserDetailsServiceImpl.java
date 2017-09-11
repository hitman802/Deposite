package com.service;

import com.dao.entities.Users;
import com.dao.repositories.UserRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Admin on 26.04.2017.
 */
@Log4j
@Component("UserDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String name) {
        Users user = userRepository.findByName(name);
        if( user == null ) {
            log.error("Cant load user by name " + name);
            throw new UsernameNotFoundException("Cant load user by name " + name);
        }

        Set<GrantedAuthority> grantedAuthorities =
            user.getRoles()
                .stream()
                .map(role->new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet())
        ;
        return new org.springframework.security.core.
                userdetails.User(user.getName(), user.getPassword(), grantedAuthorities);
    }
}
