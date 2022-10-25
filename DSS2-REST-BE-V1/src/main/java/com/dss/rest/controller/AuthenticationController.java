package com.dss.rest.controller;

import com.dss.rest.dto.UserForm;
import com.dss.rest.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/register")
    public boolean registerUser(@RequestBody UserForm userForm) {
        return authenticationService.registerUser(userForm);
    }

}
