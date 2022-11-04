package com.dss.client.proxy;

import com.dss.client.dto.UserForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="DSS-AUTHENTICATION-SERVICE")
public interface AuthenticationProxy {

    @GetMapping("/api/v1/auth/login")
    public Boolean loginUser(@RequestBody UserForm userForm);

    @GetMapping("/api/v1/auth/register")
    public Boolean registerUser(@RequestBody UserForm userForm);
}
