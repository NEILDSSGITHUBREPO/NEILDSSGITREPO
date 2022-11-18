package com.dss.auth.controller;

import com.dss.auth.entity.Role;
import com.dss.auth.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/roles")
public class RoleController {

    @Autowired
    RoleService roleService;

    @GetMapping("/get/{roleId}")
    public Role getRole(@PathVariable("roleId") long roleId){
        return roleService.getRole(roleId);
    }
}
