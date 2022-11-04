package com.dss.client.service;

import com.dss.client.dto.UserForm;
import com.dss.client.proxy.AuthenticationProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    AuthenticationProxy authenticationProxy;

    public boolean loginUser(UserForm userForm){
        return authenticationProxy.loginUser(userForm);
    }

    public boolean registerUser(UserForm userForm) { return authenticationProxy.registerUser(userForm);}
}
