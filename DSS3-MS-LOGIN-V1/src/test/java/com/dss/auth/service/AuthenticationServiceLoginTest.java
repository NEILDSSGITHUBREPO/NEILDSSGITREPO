package com.dss.auth.service;

import com.dss.auth.dto.UserForm;
import com.dss.auth.entity.User;
import com.dss.auth.entity.UserInformation;
import com.dss.auth.exception.FieldValidationException;
import com.dss.auth.exception.UserNotFoundException;
import com.dss.auth.repository.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

/**
 * Written in JUnit-4
 * Running in Junit-5-vintage-engine
 */
@RunWith(SpringRunner.class)
@Import(AuthenticationServiceTestConfig.class)
public class AuthenticationServiceLoginTest {

    @MockBean
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationService authenticationService;

    @Test
    public void shouldThrowValidationExceptionLogin() {
        User user = new User("neil@gmail.com"
                , passwordEncoder.encode("P@ssw0rd")
                , new UserInformation("09168312431"
                , "Neil De Guzman"));

        Mockito.when(userRepository.findByEmailOrPhoneNumber(user.getEmail())).thenReturn(Optional.ofNullable(user));

        //fail because of null password
        UserForm userForm = new UserForm("newNeil@gmail.com"
                , null
                , null
                , null);

        Assertions.assertThrows(FieldValidationException.class, () -> authenticationService.loginUser(userForm));
    }

    @Test
    public void shouldSuccessFullyLogin() {
        User user = new User("neil@gmail.com"
                , passwordEncoder.encode("P@ssw0rd")
                , new UserInformation("09168312431"
                , "Neil De Guzman"));

        Mockito.when(userRepository.findByEmailOrPhoneNumber(user.getEmail()))
                .thenReturn(Optional.ofNullable(user));

        UserForm userForm = new UserForm("neil@gmail.com"
                , "P@ssw0rd"
                , null
                , null);

        Assertions.assertTrue(authenticationService.loginUser(userForm));
    }

    @Test
    public void shouldThrowUserNotFound() {
        User user = new User("neil@gmail.com"
                , passwordEncoder.encode("P@ssw0rd")
                , new UserInformation("09168312431"
                , "Neil De Guzman"));

        Mockito.when(userRepository.findByEmailOrPhoneNumber(user.getEmail()))
                .thenReturn(Optional.ofNullable(user));

        UserForm userForm = new UserForm("wrongneil@gmail.com"
                , "P@ssw0rd"
                , null
                , null);

        Assertions.assertThrows(UserNotFoundException.class
                , () -> authenticationService.loginUser(userForm));
    }
}
