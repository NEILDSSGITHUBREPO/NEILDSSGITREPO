package com.dss.client;

import com.dss.client.dto.UserForm;
import com.dss.client.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class DssClientV1Application {

    public static void main(String[] args) {
        SpringApplication.run(DssClientV1Application.class, args);
    }

}
