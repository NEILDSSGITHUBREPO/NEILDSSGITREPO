package com.dss.auth.service;

import com.dss.auth.entity.Role;
import com.dss.auth.exception.RoleNotFoundException;
import com.dss.auth.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;
    public Role getRole(long id){
        Optional<Role> roleOpt = roleRepository.findById(id);

        return roleOpt.orElseThrow(RoleNotFoundException::new);
    }
}
