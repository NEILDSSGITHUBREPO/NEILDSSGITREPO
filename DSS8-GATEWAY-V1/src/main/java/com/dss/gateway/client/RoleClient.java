package com.dss.gateway.client;

import com.dss.gateway.entity.Role;
import org.springframework.web.client.RestTemplate;

public class RoleClient {

    private RoleClient() {
    }

    public static Role getRoleInformation(int id) {
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForObject("http://localhost:9105/api/v1/roles/get/" + id, Role.class);
    }
}
