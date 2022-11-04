package com.dss.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
//@EnableEurekaClient
public class Dss8GatewayV1Application {

	public static void main(String[] args) {
		SpringApplication.run(Dss8GatewayV1Application.class, args);
	}

}
