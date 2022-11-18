package com.dss.auth.controller;

import com.dss.auth.dto.TokenForm;
import com.dss.auth.dto.UserForm;
import com.dss.auth.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public TokenForm loginUser(@RequestBody UserForm userForm) {
        return authenticationService.loginUser(userForm);
    }
}
