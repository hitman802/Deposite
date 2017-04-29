package dao.services;

import dao.entities.UserRole;
import dao.entities.User;
import dao.repository.UserRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Admin on 26.04.2017.
 */
@Log4j
@Component("UserDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        User user = userRepository.loadUserByName(username);
        if( user == null ) {
            log.error("Cant load user by name " + username);
            throw new UsernameNotFoundException("Cant load user by name " + username);
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (UserRole userRole : user.getUserRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(userRole.getName()));
        }
        return new org.springframework.security.core.
                userdetails.User(user.getName(), user.getPassword(), grantedAuthorities);
    }
}
