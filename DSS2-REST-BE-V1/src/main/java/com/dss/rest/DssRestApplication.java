package com.dss.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class DssRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(DssRestApplication.class, args);
	}

}
