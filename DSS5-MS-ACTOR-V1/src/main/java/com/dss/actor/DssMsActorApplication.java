package com.dss.actor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class DssMsActorApplication {

	public static void main(String[] args) {
		SpringApplication.run(DssMsActorApplication.class, args);
	}

}
