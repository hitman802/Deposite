package com.service;

import com.BaseUnitTest;
import com.dao.entities.Role;
import com.dao.entities.Users;
import com.dao.repositories.UserRepository;
import static org.mockito.Mockito.*;

import org.mockito.Mockito;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.testng.Assert.*;

public class UserDetailsServiceImplTest extends BaseUnitTest {

    private UserDetailsServiceImpl userDetailsService;
    private UserRepository userRepository;
    private final String userNameExisting = "userNameExisting";
    private final String userNameNotExisting = "userNameNotExisting";
    private Users user;
    private final Set<Role> roles =
            Stream.of("admin", "user")
                    .map(roleName-> {
                        Role role = new Role();
                        role.setName(roleName);
                        return role;
                    })
                    .collect(Collectors.toSet())
    ;

    @Test
    public void testLoadUserByUsername() throws Exception {
        Assert.expectThrows(UsernameNotFoundException.class, ()->userDetailsService.loadUserByUsername(userNameNotExisting));

        UserDetails userDetails = userDetailsService.loadUserByUsername(userNameExisting);
        Assert.assertNotNull(userDetails);
        Assert.assertEquals(userDetails.getUsername(), user.getName());
        Assert.assertEquals(userDetails.getPassword(), user.getPassword());

        Assert.assertEquals(
                userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()),
                roles.stream().map(Role::getName).collect(Collectors.toSet())
        );
    }

    @Override
    public void prepareMocks() {
        userRepository = Mockito.mock(UserRepository.class);
        userDetailsService = new UserDetailsServiceImpl(userRepository);

        user = new Users();
        user.setRoles(roles);
        user.setName(userNameExisting);
        user.setPassword("somePassword");

        when(userRepository.findByName(userNameNotExisting)).thenReturn(null);
        when(userRepository.findByName(userNameExisting)).thenReturn(user);
    }
}