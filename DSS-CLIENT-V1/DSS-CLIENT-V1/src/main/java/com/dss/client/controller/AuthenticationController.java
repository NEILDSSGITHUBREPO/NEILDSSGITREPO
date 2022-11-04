package com.dss.client.controller;

import com.dss.client.dto.UserForm;
import com.dss.client.service.AuthenticationService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private Environment environment;
    /**
     * Controller method for registering user
     *
     * @Param Userform userForm
     * @Return boolean ? true if success else show message from RestExceptionHandler
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserForm userForm) {
        try {
            return ResponseEntity.ok(authenticationService.registerUser(userForm));
        }catch (FeignException fe){
            return ResponseEntity.status(fe.status())
                    .body(fe.contentUTF8());
        }
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
