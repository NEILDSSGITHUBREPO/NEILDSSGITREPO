package com.dss.auth.service;

import com.dss.auth.dto.UserForm;
import com.dss.auth.entity.User;
import com.dss.auth.entity.UserInformation;
import com.dss.auth.exception.FieldValidationException;
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

/**
 * Written in JUnit-4
 * Running in Junit-5-vintage-engine
 */
@RunWith(SpringRunner.class)
@Import(AuthenticationServiceTestConfig.class)
public class AuthenticationServiceRegistrationTest {

    @MockBean
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationService authenticationService;

    @Test
    public void shouldThrowValidationException() {
        User user = new User("neil@gmail.com"
                , passwordEncoder.encode("P@ssw0rd")
                , new UserInformation("09168312431"
                , "Neil De Guzman"));

        Mockito.when(userRepository.save(user)).thenReturn(user);

        //fail because of password format
        //expected format : 1 small character and capitalized character, at least 1 numeric. and at least 1 special character
        UserForm userForm = new UserForm("newNeil@gmail.com"
                , "Passw0rd"
                , "09168312431"
                , "Neil De Guzman");

        Assertions.assertThrows(FieldValidationException.class, () -> authenticationService.registerUser(userForm));
    }

    @Test
    public void shouldRegisterUser() {
        User user = new User("neil@gmail.com"
                , passwordEncoder.encode("P@ssw0rd")
                , new UserInformation("09168312431"
                , "Neil De Guzman"));

        Mockito.when(userRepository.save(user)).thenReturn(user);

        UserForm userForm = new UserForm("newNeil@gmail.com"
                , "P@ssw0rd"
                , "09168312431"
                , "Neil De Guzman");

        Assertions.assertTrue(authenticationService.registerUser(userForm));
    }
}
