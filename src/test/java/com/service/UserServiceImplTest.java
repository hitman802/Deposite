package com.service;

import com.BaseUnitTest;
import com.dao.entities.Users;
import com.dao.repositories.RoleRepository;
import com.dao.repositories.UserRepository;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

public class UserServiceImplTest extends BaseUnitTest {

    private UserServiceImpl userServiceImpl;
    private UserRepository userRepository = Mockito.mock(UserRepository.class);
    private BCryptPasswordEncoder encoder = Mockito.mock(BCryptPasswordEncoder.class);
    private final String pwdNotEncoded = "notEncodedPwd";
    private final String pwdEncoded = "encodedPwd";
    private final Set<String> roles = Stream.of("admin", "user").collect(Collectors.toSet());

    @Test
    public void testSave() throws Exception {
        Users user = new Users();
        user.setPassword(pwdNotEncoded);
        userServiceImpl.save(user);
        verify(userRepository, times(1)).save(user);
        Assert.assertEquals(user.getPassword(), pwdEncoded);
        Assert.assertTrue(roles.containsAll(user.getRoles()));
    }

    @Test
    public void testFindByUsername() throws Exception {
        String some_user_name = "some user name";
        userServiceImpl.findByUsername(some_user_name);
        verify(userRepository, times(1)).findByName(some_user_name);
    }

    @Test
    public void testEncodePassword() throws Exception {
        final String somepwd = "somepwd";
        userServiceImpl.encodePassword(somepwd);
        verify(encoder, times(1)).encode(somepwd);
    }

    @Override
    public void prepareMocks() {
        userRepository = Mockito.mock(UserRepository.class);
        RoleRepository roleRepository = Mockito.mock(RoleRepository.class);
        encoder = Mockito.mock(BCryptPasswordEncoder.class);
        userServiceImpl = new UserServiceImpl(userRepository, roleRepository, encoder);

        when(encoder.encode(pwdNotEncoded)).thenReturn(pwdEncoded);
        when(roleRepository.findAll()).thenReturn(((Iterable) roles));
    }
}