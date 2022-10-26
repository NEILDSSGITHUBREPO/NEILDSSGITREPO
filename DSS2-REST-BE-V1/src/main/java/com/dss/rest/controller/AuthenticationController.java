package com.dss.rest.controller;

import com.dss.rest.dto.UserForm;
import com.dss.rest.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling authentication. Login and Registration
 */
@RestController
@RequestMapping("api/v1/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    /**
     * Controller method for registering user
     *
     * @Param Userform userForm
     * @Return boolean ? true if success else show message from RestExceptionHandler
     */
    @PostMapping("/register")
    public boolean registerUser(@RequestBody UserForm userForm) {
        return authenticationService.registerUser(userForm);
    }

    /**
     * Controller method for registering user
     *
     * @Param Userform userForm
     * @Return boolean ? true if success else false
     * catches errors through RestExceptionHandler
     */
    @PostMapping("/login")
    public boolean loginUser(@RequestBody UserForm userForm) {
        return authenticationService.loginUser(userForm);
    }
}
